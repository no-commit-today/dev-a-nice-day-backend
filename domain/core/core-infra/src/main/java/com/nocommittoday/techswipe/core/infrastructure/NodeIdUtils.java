package com.nocommittoday.techswipe.core.infrastructure;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.security.SecureRandom;

final class NodeIdUtils {

    private static final Logger log = LoggerFactory.getLogger(NodeIdUtils.class);
    
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
