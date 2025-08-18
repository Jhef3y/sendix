package br.com.sendix.api.infra.security;

import java.security.SecureRandom;
import java.util.Base64;

public final class SecureTokenGenerator {

    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();

    private SecureTokenGenerator() {}

    public static String generateToken(int byteLength) {
        byte[] randomBytes = new byte[byteLength];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }
}