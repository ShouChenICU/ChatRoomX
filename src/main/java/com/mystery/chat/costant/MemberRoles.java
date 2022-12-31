package com.mystery.chat.costant;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * 成员角色
 *
 * @author shouchen
 * @date 2022/12/6
 */
@Lazy
@Component
public final class MemberRoles {
    /**
     * 房主
     */
    public static String OWNER = "OWNER";
    /**
     * 管理员
     */
    public static String ADMIN = "ADMIN";
    /**
     * 群员
     */
    public static String MEMBER = "MEMBER";

    public static int calcWeight(String role) {
        if (OWNER.equalsIgnoreCase(role)) {
            return 30;
        } else if (ADMIN.equalsIgnoreCase(role)) {
            return 20;
        } else if (MEMBER.equalsIgnoreCase(role)) {
            return 10;
        }
        return 0;
    }
}
