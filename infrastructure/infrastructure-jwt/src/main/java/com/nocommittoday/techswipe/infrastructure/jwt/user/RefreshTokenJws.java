package com.nocommittoday.techswipe.infrastructure.jwt.user;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.gen.OctetSequenceKeyGenerator;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class RefreshTokenJws {

    private static final MacAlgorithm algorithm = MacAlgorithm.HS512;

    private final SecretKey secretKey;


    private RefreshTokenJws(SecretKey secretKey) {
        this.secretKey = secretKey;
    }

    public static RefreshTokenJws from(String secret) {
        return new RefreshTokenJws(
                new SecretKeySpec(secret.getBytes(), "HmacSHA512")
        );
    }

    public static RefreshTokenJws generate() {
        try {
            return new RefreshTokenJws(
                    new OctetSequenceKeyGenerator(512)
                            .algorithm(JWSAlgorithm.HS512)
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
