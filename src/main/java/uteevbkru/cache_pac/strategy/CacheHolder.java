package uteevbkru.cache_pac.strategy;

import uteevbkru.cache_pac.api.Cache;

public interface CacheHolder<K, V> {

    /**
     * @return Cache to use
     */
    Cache<K, V> getCache();

    /**
     * If the cache_pac is leveled, redistributes values between levels
     */
    void recache();

}