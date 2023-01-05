package com.mystery.chat.managers;

import com.mystery.chat.configures.AppConfig;
import com.mystery.chat.utils.DateTimeFormatUtils;
import com.mystery.chat.vos.MessageVO;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 全局房间
 *
 * @author shouchen
 * @date 2022/12/3
 */
public class GlobalRoom {
    public static final String GLOBAL_ROOM_ID = "GLOBAL";
    private final ReadWriteLock readWriteLock;
    private final List<MessageVO> messages;
    private final AtomicLong priKey;
    private final int maxSize;

    public GlobalRoom(AppConfig appConfig) {
        readWriteLock = new ReentrantReadWriteLock();
        messages = new LinkedList<>();
        priKey = new AtomicLong(0);
        maxSize = appConfig.globalRoomMaxMsgCount;
    }

    public void putMsg(MessageVO messageVO) {
        readWriteLock.writeLock().lock();
        try {
            long instant = System.currentTimeMillis();
            // TODO: 2023/1/5  
            messageVO.setId(priKey.incrementAndGet())
                    .setInstant(instant)
                    .setDateTime(DateTimeFormatUtils.formatMsg(instant));
            messages.add(messageVO);
            messages.sort(Comparator.comparingLong(MessageVO::getInstant));
            if (messages.size() > maxSize) {
                messages.remove(0);
            }
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }
}
