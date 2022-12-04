package com.mystery.chat.costant;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author shouchen
 * @date 2022/11/28
 */
@Component("roles")
public final class Roles {
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_ADMIN_VALUE = "管理员";
    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_USER_VALUE = "用户";
    public static final String ADMIN = "ADMIN";
    public static final String USER = "USER";
    private static final Map<String, String> ROLE_MAP = new HashMap<>() {
        {
            put(ROLE_ADMIN, ROLE_ADMIN_VALUE);
            put(ROLE_USER, ROLE_USER_VALUE);
        }
    };

    public static String parseRole(String role) {
        return ROLE_MAP.getOrDefault(role, "");
    }
}
