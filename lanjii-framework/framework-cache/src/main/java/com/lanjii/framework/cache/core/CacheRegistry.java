package com.lanjii.framework.cache.core;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 缓存注册表
 *
 * @author lanjii
 */
public class CacheRegistry {

    private final Map<String, CacheDef> cacheDefMap = new ConcurrentHashMap<>();

    /**
     * 注册缓存定义
     *
     * @param cacheDef 缓存定义
     */
    public void register(CacheDef cacheDef) {
        cacheDefMap.put(cacheDef.getName(), cacheDef);
    }

    /**
     * 批量注册缓存定义
     *
     * @param cacheDefs 缓存定义列表
     */
    public void registerAll(CacheDef... cacheDefs) {
        for (CacheDef cacheDef : cacheDefs) {
            register(cacheDef);
        }
    }

    /**
     * 获取缓存定义
     *
     * @param name 缓存名称
     * @return Optional<CacheDef>
     */
    public Optional<CacheDef> get(String name) {
        return Optional.ofNullable(cacheDefMap.get(name));
    }

    /**
     * 获取所有已注册的缓存定义
     *
     * @return 缓存定义集合
     */
    public Collection<CacheDef> getAll() {
        return cacheDefMap.values();
    }

    /**
     * 判断是否已注册指定缓存
     *
     * @param name 缓存名称
     * @return 是否已注册
     */
    public boolean contains(String name) {
        return cacheDefMap.containsKey(name);
    }
}
