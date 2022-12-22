package com.mystery.chat.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

/**
 * @author shouchen
 * @date 2022/11/24
 */
public final class UIDGenerator {
    private static final String DIGEST_ALGORITHM = "MD5";

    /**
     * 随机生成UID
     *
     * @return 随机UID
     */
    public static String randomUID() {
        SecureRandom random = new SecureRandom();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            int code = random.nextInt(62);
            if (code < 10) {
                builder.append(code);
            } else if (code < 36) {
                builder.append((char) ('A' + code - 10));
            } else {
                builder.append((char) ('a' + code - 36));
            }
        }
        return builder.toString();
    }

    /**
     * 效果同以UUID第三版生成的16字节数据最后6字节转URL版Base64相同
     *
     * @param nameSpace 命名空间
     * @return UID
     */
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
