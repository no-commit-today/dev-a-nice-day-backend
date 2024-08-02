package com.nocommittoday.techswipe.core.infrastructure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * 참고1: https://github.com/twitter-archive/snowflake/tree/snowflake-2010
 *   - https://github.com/twitter-archive/snowflake/blob/snowflake-2010/src/main/scala/com/twitter/service/snowflake/IdWorker.scala
 * 참고2: https://github.com/callicoder/java-snowflake/blob/master/src/main/java/com/callicoder/snowflake/Snowflake.java
 * 참고3: https://www.slideshare.net/slideshow/twitter-snowflake/80830757
 */
@Scope("prototype")
@Component
public class IdGenerator {

    private static final Logger log = LoggerFactory.getLogger(IdGenerator.class);

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
    public IdGenerator(SystemClockHolder systemClockHolder) {
        this(systemClockHolder, NodeIdUtils.create(), systemClockHolder.millis(), 0L);
    }

    public IdGenerator(SystemClockHolder systemClockHolder, long nodeId) {
        this(systemClockHolder, nodeId, systemClockHolder.millis(), 0L);
    }

    public IdGenerator(
            SystemClockHolder systemClockHolder,
            long nodeId,
            long lastTimestamp,
            long sequence
    ) {
        if (systemClockHolder == null) {
            throw new IllegalArgumentException("systemClockHolder 는 null 이 될 수 없습니다.");
        }
        if (lastTimestamp < EPOCH_OFFSET) {
            throw new IllegalArgumentException(String.format("lastTimestamp 는 %d 이상의 값이어야 합니다.", EPOCH_OFFSET));
        }
        if (sequence < 0 || sequence > MAX_SEQUENCE) {
            throw new IllegalArgumentException(String.format("sequence 는 %d 이상 %d 이하의 값이어야 합니다.", 0, MAX_SEQUENCE));
        }
        this.systemClockHolder = systemClockHolder;
        this.lastTimestamp = lastTimestamp;
        this.sequence = sequence;

        if (nodeId < 0 || nodeId > MAX_NODE_ID) {
            long newNodeId = nodeId & MAX_NODE_ID;
            log.debug("nodeId[{}] 가 범위를 벗어났습니다. 새로운 nodeId[{}] 로 대체합니다.", nodeId, newNodeId);
            this.nodeId = newNodeId;
        } else {
            this.nodeId = nodeId;
        }
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

        long epoch = currentTimestamp - EPOCH_OFFSET;
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

    public long[] parse(long id) {
        long maskNodeId = ((1L << NODE_ID_BITS) - 1) << SEQUENCE_BITS;
        long maskSequence = (1L << SEQUENCE_BITS) - 1;

        long timestamp = (id >> (NODE_ID_BITS + SEQUENCE_BITS)) + EPOCH_OFFSET;
        long nodeId = (id & maskNodeId) >> SEQUENCE_BITS;
        long sequence = id & maskSequence;

        return new long[]{timestamp, nodeId, sequence};
    }

    public long firstId(long millis) {
        return (millis - EPOCH_OFFSET) << (NODE_ID_BITS + SEQUENCE_BITS);
    }

    public long firstId(LocalDateTime dateTime) {
        return firstId(dateTime.atZone(ZoneOffset.UTC).toInstant().toEpochMilli());
    }

    public long firstId(LocalDate date) {
        return firstId(date.atStartOfDay());
    }
}
