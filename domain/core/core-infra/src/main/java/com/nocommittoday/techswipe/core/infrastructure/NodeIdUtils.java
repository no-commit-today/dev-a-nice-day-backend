package com.nocommittoday.techswipe.core.infrastructure;

import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.security.SecureRandom;

@Slf4j
final class NodeIdUtils {
    
    static long create() {
        long nodeId;
        try {
            final InetAddress localHost = InetAddress.getLocalHost();
            final String hostName = localHost.getHostName();
            log.info("hostName[{}] 을 통해서 nodeId 를 생성합니다.", hostName);
            nodeId = hostName.hashCode();
        } catch (Exception ex) {
            log.warn("NodeId 생성 중 에러가 발생했습니다. 랜덤 값으로 대체합니다.", ex);
            nodeId = (new SecureRandom().nextInt());
        }
        return nodeId;
    }

    private NodeIdUtils() {
        throw new UnsupportedOperationException();
    }
}
