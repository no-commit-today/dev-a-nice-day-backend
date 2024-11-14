package com.devniceday.module.idgenerator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.security.SecureRandom;
import java.time.Clock;
import java.time.LocalDateTime;

/**
 * 참고1: https://github.com/twitter-archive/snowflake/tree/snowflake-2010
 * - https://github.com/twitter-archive/snowflake/blob/snowflake-2010/src/main/scala/com/twitter/service/snowflake/IdWorker.scala
 * 참고2: https://github.com/callicoder/java-snowflake/blob/master/src/main/java/com/callicoder/snowflake/Snowflake.java
 * 참고3: https://www.slideshare.net/slideshow/twitter-snowflake/80830757
 */
public class IdGenerator {

    private static final Logger log = LoggerFactory.getLogger(IdGenerator.class);

    private static final int UNUSED_BITS = 1; // Sign bit, Unused (always set to 0)
    private static final int EPOCH_BITS = 41;
    private static final int NODE_ID_BITS = 10;
    private static final int SEQUENCE_BITS = 12;

    private static final long MAX_EPOCH = (1L << EPOCH_BITS) - 1;
    private static final long MAX_NODE_ID = (1L << NODE_ID_BITS) - 1;
    private static final long MAX_SEQUENCE = (1L << SEQUENCE_BITS) - 1;

    private static final LocalDateTime EPOCH_OFFSET_DT = LocalDateTime.of(2024, 1, 1, 0, 0, 0);

    private final Clock clock;
    private final long nodeId;
    private final long epochOffset;
    private volatile long lastTimestamp;
    private volatile long sequence = 0L;

    public IdGenerator(Clock clock) {
        this(clock, createNodeId(), clock.millis(), 0L);
    }

    public IdGenerator(Clock clock, long nodeId) {
        this(clock, nodeId, clock.millis(), 0L);
    }

    public IdGenerator(
            Clock clock,
            long nodeId,
            long lastTimestamp,
            long sequence
    ) {
        if (clock == null) {
            throw new IllegalArgumentException("clock 은 null 이 될 수 없습니다.");
        }
        this.clock = clock;
        this.epochOffset = EPOCH_OFFSET_DT.atZone(clock.getZone()).toInstant().toEpochMilli();

        if (lastTimestamp < epochOffset) {
            throw new IllegalArgumentException(String.format("lastTimestamp 는 %d 이상의 값이어야 합니다.", epochOffset));
        }
        if (sequence < 0 || sequence > MAX_SEQUENCE) {
            throw new IllegalArgumentException(String.format("sequence 는 %d 이상 %d 이하의 값이어야 합니다.", 0, MAX_SEQUENCE));
        }
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

    private static long createNodeId() {
        long nodeId;
        try {
            final InetAddress localHost = InetAddress.getLocalHost();
            final String hostName = localHost.getHostName();
            nodeId = hostName.hashCode();
            log.debug("hostName[{}] 을 통해서 nodeId 를 생성합니다. nodeId={}", hostName, nodeId);
        } catch (Exception ex) {
            log.warn("NodeId 생성 중 에러가 발생했습니다. 랜덤 값으로 대체합니다.", ex);
            nodeId = (new SecureRandom().nextInt());
        }
        return nodeId;
    }

    public synchronized long nextId() {
        long currentTimestamp = clock.millis();

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

        long epoch = currentTimestamp - epochOffset;
        if (epoch > MAX_EPOCH) {
            throw new IllegalStateException("시간 값이 범위를 벗어났습니다.");
        }

        return (epoch << (NODE_ID_BITS + SEQUENCE_BITS))
                | (nodeId << SEQUENCE_BITS)
                | sequence;
    }

    private long waitNextMillis(long currentTimestamp) {
        while (currentTimestamp == lastTimestamp) {
            currentTimestamp = clock.millis();
        }
        return currentTimestamp;
    }

    public long[] parse(long id) {
        long maskNodeId = ((1L << NODE_ID_BITS) - 1) << SEQUENCE_BITS;
        long maskSequence = (1L << SEQUENCE_BITS) - 1;

        long timestamp = (id >> (NODE_ID_BITS + SEQUENCE_BITS)) + epochOffset;
        long nodeId = (id & maskNodeId) >> SEQUENCE_BITS;
        long sequence = id & maskSequence;

        return new long[]{timestamp, nodeId, sequence};
    }
}
