package com.ozaytunctan.security.manager;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.util.StringUtils;

public final class EncryptionManager {

    private final String SALT_KEY = "SECRETKEY+ENCRYPT123";

    public String encrypt(String encryptText) {
        if (!StringUtils.hasText(encryptText))
            return "";

        return DigestUtils.sha256Hex(encryptText + SALT_KEY);
    }
}