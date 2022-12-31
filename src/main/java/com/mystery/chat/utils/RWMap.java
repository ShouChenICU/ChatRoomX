package com.mystery.chat.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * 用读写锁修饰的Map
 *
 * @author shouchen
 * @date 2022/12/31
 */
public class RWMap<K, V> extends HashMap<K, V> {
    private final ReadWriteLock readWriteLock;

    public RWMap() {
        this.readWriteLock = new ReentrantReadWriteLock();
    }

    public RWMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
        this.readWriteLock = new ReentrantReadWriteLock();
    }

    public RWMap(int initialCapacity) {
        super(initialCapacity);
        this.readWriteLock = new ReentrantReadWriteLock();
    }

    @Override
    public V get(Object key) {
        readWriteLock.readLock().lock();
        try {
            return super.get(key);
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    @Override
    public boolean containsKey(Object key) {
        readWriteLock.readLock().lock();
        try {
            return super.containsKey(key);
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    @Override
    public V put(K key, V value) {
        readWriteLock.writeLock().lock();
        try {
            return super.put(key, value);
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        readWriteLock.writeLock().lock();
        try {
            super.putAll(m);
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    @Override
    public V remove(Object key) {
        readWriteLock.writeLock().lock();
        try {
            return super.remove(key);
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    @Override
    public void clear() {
        readWriteLock.writeLock().lock();
        try {
            super.clear();
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    @Override
    public boolean containsValue(Object value) {
        readWriteLock.readLock().lock();
        try {
            return super.containsValue(value);
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    @Override
    public V putIfAbsent(K key, V value) {
        readWriteLock.writeLock().lock();
        try {
            return super.putIfAbsent(key, value);
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    @Override
    public boolean remove(Object key, Object value) {
        readWriteLock.writeLock().lock();
        try {
            return super.remove(key, value);
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    @Override
    public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
        readWriteLock.writeLock().lock();
        try {
            return super.computeIfAbsent(key, mappingFunction);
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    @Override
    public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        readWriteLock.writeLock().lock();
        try {
            return super.computeIfPresent(key, remappingFunction);
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    @Override
    public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        readWriteLock.readLock().lock();
        try {
            return super.compute(key, remappingFunction);
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    @Override
    public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        readWriteLock.writeLock().lock();
        try {
            return super.merge(key, value, remappingFunction);
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    @Override
    public void forEach(BiConsumer<? super K, ? super V> action) {
        readWriteLock.readLock().lock();
        try {
            super.forEach(action);
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    @Override
    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
        readWriteLock.writeLock().lock();
        try {
            super.replaceAll(function);
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    @Override
    public V getOrDefault(Object key, V defaultValue) {
        readWriteLock.readLock().lock();
        try {
            return super.getOrDefault(key, defaultValue);
        } finally {
            readWriteLock.readLock().unlock();
        }
    }
}
