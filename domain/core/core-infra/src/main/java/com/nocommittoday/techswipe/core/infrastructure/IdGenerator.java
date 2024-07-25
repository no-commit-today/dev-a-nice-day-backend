package com.nocommittoday.techswipe.core.infrastructure;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * 참고1: https://github.com/twitter-archive/snowflake/tree/snowflake-2010
 *   - https://github.com/twitter-archive/snowflake/blob/snowflake-2010/src/main/scala/com/twitter/service/snowflake/IdWorker.scala
 * 참고2: https://github.com/callicoder/java-snowflake/blob/master/src/main/java/com/callicoder/snowflake/Snowflake.java
 * 참고3: https://www.slideshare.net/slideshow/twitter-snowflake/80830757
 */
@Slf4j
@Scope("prototype")
@Component
public class IdGenerator {

    private static final int UNUSED_BITS = 1; // Sign bit, Unused (always set to 0)
    private static final int EPOCH_BITS = 41;
    private static final int NODE_ID_BITS = 10;
    private static final int SEQUENCE_BITS = 12;

    private static final long MAX_EPOCH = (1L << EPOCH_BITS) - 1;
    private static final long MAX_NODE_ID = (1L << NODE_ID_BITS) - 1;
    private static final long MAX_SEQUENCE = (1L << SEQUENCE_BITS) - 1;

    private static final long EPOCH_OFFSET = LocalDateTime.of(2024, 1, 1, 0, 0, 0)
            .atZone(ZoneOffset.UTC).toInstant().toEpochMilli();

    private final SystemClockHolder systemClockHolder;
    private final long nodeId;
    private volatile long lastTimestamp;
    private volatile long sequence = 0L;

    @Autowired
    public IdGenerator(final SystemClockHolder systemClockHolder) {
        this(systemClockHolder, createNodeId(), systemClockHolder.millis(), 0L);
    }

    public IdGenerator(
            final SystemClockHolder systemClockHolder,
            final long nodeId,
            final long lastTimestamp,
            final long sequence
    ) {
        if (systemClockHolder == null) {
            throw new IllegalArgumentException("systemClockHolder 는 null 이 될 수 없습니다.");
        }
        if (nodeId < 0 || nodeId > MAX_NODE_ID) {
            throw new IllegalArgumentException(String.format("nodeId 는 %d 이상 %d 이하의 값이어야 합니다.", 0, MAX_NODE_ID));
        }
        if (lastTimestamp < EPOCH_OFFSET) {
            throw new IllegalArgumentException(String.format("lastTimestamp 는 %d 이상의 값이어야 합니다.", EPOCH_OFFSET));
        }
        if (sequence < 0 || sequence > MAX_SEQUENCE) {
            throw new IllegalArgumentException(String.format("sequence 는 %d 이상 %d 이하의 값이어야 합니다.", 0, MAX_SEQUENCE));
        }
        this.systemClockHolder = systemClockHolder;
        this.nodeId = nodeId;
        this.lastTimestamp = lastTimestamp;
        this.sequence = sequence;
    }

    private static long createNodeId() {
        long nodeId;
        try {
            final InetAddress localHost = InetAddress.getLocalHost();
            final String hostName = localHost.getHostName();
            log.info("hostName[{}] 을 통해서 nodeId 를 생성합니다.", hostName);
            nodeId = hostName.hashCode();
        } catch (final Exception ex) {
            log.warn("NodeId 생성 중 에러가 발생했습니다. 랜덤 값으로 대체합니다.", ex);
            nodeId = (new SecureRandom().nextInt());
        }
        nodeId = nodeId & MAX_NODE_ID;
        return nodeId;
    }

    public synchronized long nextId() {
        long currentTimestamp = systemClockHolder.millis();

        if (currentTimestamp < lastTimestamp) {
            throw new IllegalStateException(
                    String.format("시간 값이 잘못되었습니다. 이전 시간: %d, 현재 시간: %d", lastTimestamp, currentTimestamp));
        }


        if (currentTimestamp == lastTimestamp) {
            sequence = (sequence + 1) & MAX_SEQUENCE;
            if (sequence == 0) {
                currentTimestamp = waitNextMillis(currentTimestamp);
            }
        } else {
            sequence = 0;
        }

        lastTimestamp = currentTimestamp;

        final long epoch = currentTimestamp - EPOCH_OFFSET;
        if (epoch > MAX_EPOCH) {
            throw new IllegalStateException("시간 값이 범위를 벗어났습니다.");
        }

        return (epoch << (NODE_ID_BITS + SEQUENCE_BITS))
                | (nodeId << SEQUENCE_BITS)
                | sequence;
    }

    private long waitNextMillis(long currentTimestamp) {
        while (currentTimestamp == lastTimestamp) {
            currentTimestamp = systemClockHolder.millis();
        }
        return currentTimestamp;
    }

    public long[] parse(final long id) {
        final long maskNodeId = ((1L << NODE_ID_BITS) - 1) << SEQUENCE_BITS;
        final long maskSequence = (1L << SEQUENCE_BITS) - 1;

        final long timestamp = (id >> (NODE_ID_BITS + SEQUENCE_BITS)) + EPOCH_OFFSET;
        final long nodeId = (id & maskNodeId) >> SEQUENCE_BITS;
        final long sequence = id & maskSequence;

        return new long[]{timestamp, nodeId, sequence};
    }

    public long firstId(final long millis) {
        return (millis - EPOCH_OFFSET) << (NODE_ID_BITS + SEQUENCE_BITS);
    }

    public long firstId(final LocalDateTime dateTime) {
        return firstId(dateTime.atZone(ZoneOffset.UTC).toInstant().toEpochMilli());
    }

    public long firstId(final LocalDate date) {
        return firstId(date.atStartOfDay());
    }
}
