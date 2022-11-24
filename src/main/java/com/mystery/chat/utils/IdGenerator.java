package com.mystery.chat.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;

/**
 * @author shouchen
 * @date 2022/11/24
 */
public class IdGenerator {
    private static final String DIGEST_ALGORITHM = "MD5";

    public static String fromNameSpace(String nameSpace) {
        try {
            MessageDigest digest = MessageDigest.getInstance(DIGEST_ALGORITHM);
            byte[] bytes = digest.digest(nameSpace.getBytes(StandardCharsets.UTF_8));
            bytes = Arrays.copyOfRange(bytes, 10, 16);
            return Base64.getUrlEncoder().encodeToString(bytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}