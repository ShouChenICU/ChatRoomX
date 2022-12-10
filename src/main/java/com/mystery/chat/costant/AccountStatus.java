package com.mystery.chat.costant;

/**
 * 账户状态
 *
 * @author shouchen
 * @date 2022/12/9
 */
public interface AccountStatus {
    /**
     * 正常
     */
    String NORMAL = "NORMAL";
    /**
     * 已锁定
     */
    String LOCKED = "LOCKED";
    /**
     * 未激活
     */
    String INACTIVE = "INACTIVE";
    /**
     * 已删除
     */
    String DELETED = "DELETED";
}
