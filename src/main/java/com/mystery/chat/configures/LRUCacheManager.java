package com.mystery.chat.configures;

import com.mystery.chat.utils.LRUCache;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * LRU缓存管理器
 *
 * @author shouchen
 * @date 2023/1/3
 */
@Component
public class LRUCacheManager implements CacheManager {
    public static final String USER_CACHE = "USER_CACHE";
    public static final String MEMBER_CACHE = "MEMBER_CACHE";
    public static final String DEFAULT_CACHE = "DEFAULT_CACHE";
    private static final List<String> CACHE_NAMES = Arrays.asList(USER_CACHE, MEMBER_CACHE, DEFAULT_CACHE);
    public final Map<String, Cache> cacheMap;
    public final LRUCacheWrapper defaultCache;

    public LRUCacheManager(AppConfig appConfig) {
        cacheMap = new HashMap<>();
        cacheMap.put(USER_CACHE, new LRUCacheWrapper(USER_CACHE, appConfig.userCacheSize));
        cacheMap.put(MEMBER_CACHE, new LRUCacheWrapper(MEMBER_CACHE, appConfig.userCacheSize * 2));
        defaultCache = new LRUCacheWrapper(DEFAULT_CACHE, 1023);
    }

    @Override
    public Cache getCache(String name) {
        return cacheMap.getOrDefault(name, defaultCache);
    }

    @Override
    public Collection<String> getCacheNames() {
        return CACHE_NAMES;
    }

    private static class LRUCacheWrapper implements Cache {
        private final String name;
        private final LRUCache<Object, ValueWrapper> cache;

        public LRUCacheWrapper(String name, int size) {
            this.name = name;
            cache = new LRUCache<>(size);
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public Object getNativeCache() {
            return null;
        }

        @Override
        public ValueWrapper get(Object key) {
            return cache.get(key);
        }

        @Override
        public <T> T get(Object key, Class<T> type) {
            return null;
        }

        @Override
        public <T> T get(Object key, Callable<T> valueLoader) {
            return null;
        }

        @Override
        public void put(Object key, Object value) {
            cache.put(key, () -> value);
        }

        @Override
        public void evict(Object key) {
            cache.remove(key);
        }

        @Override
        public void clear() {
            cache.clear();
        }
    }
}
