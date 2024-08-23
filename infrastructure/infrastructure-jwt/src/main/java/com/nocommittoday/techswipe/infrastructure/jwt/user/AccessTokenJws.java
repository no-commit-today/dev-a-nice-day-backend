package com.nocommittoday.techswipe.infrastructure.jwt.user;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.gen.OctetSequenceKeyGenerator;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AccessTokenJws {

    private static final MacAlgorithm algorithm = MacAlgorithm.HS256;

    private final SecretKey secretKey;


    private AccessTokenJws(SecretKey secretKey) {
        this.secretKey = secretKey;
    }

    public static AccessTokenJws from(String secret) {
        return new AccessTokenJws(
                new SecretKeySpec(secret.getBytes(), "HmacSHA256")
        );
    }

    public static AccessTokenJws generate() {
        try {
            return new AccessTokenJws(
                    new OctetSequenceKeyGenerator(256)
                            .algorithm(JWSAlgorithm.HS256)
                            .generate()
                            .toSecretKey()
            );
        } catch (JOSEException e) {
            throw new IllegalStateException("AccessTokenJws 생성 중 오류가 발생했습니다.", e);
        }
    }

    public MacAlgorithm getAlgorithm() {
        return algorithm;
    }

    public SecretKey getSecretKey() {
        return secretKey;
    }
}
