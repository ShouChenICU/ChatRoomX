package com.mystery.chat.utils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 线程安全的LRU算法的缓存
 *
 * @author shouchen
 * @date 2022/12/2
 */
public class LRUCache<K, V> {
    private final LinkedHashMap<K, V> cache;
    private final ReadWriteLock readWriteLock;

    public LRUCache(int cacheSize) {
        this.readWriteLock = new ReentrantReadWriteLock();
        cache = new LinkedHashMap<>(cacheSize, 1, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                return size() > cacheSize;
            }
        };
    }

    public LRUCache<K, V> put(K key, V value) {
        readWriteLock.writeLock().lock();
        try {
            cache.put(key, value);
            return this;
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    public LRUCache<K, V> remove(K key) {
        readWriteLock.writeLock().lock();
        try {
            cache.remove(key);
            return this;
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    public LRUCache<K, V> clear() {
        readWriteLock.writeLock().lock();
        try {
            cache.clear();
            return this;
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    public V get(K key) {
        readWriteLock.readLock().lock();
        try {
            return cache.get(key);
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    @Override
    public String toString() {
        return cache.toString();
    }
}
