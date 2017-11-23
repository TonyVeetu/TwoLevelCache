package uteevbkru.cache_pac.builder;

import uteevbkru.cache_pac.api.Cache;

public interface LeveledCache<K, V> {
    /**
     * @return Returns all levels of cache_pac wrapped in one {@link Cache} instance.
     */
    Cache<K, V> asCache();

    /**
     * Redistibutes cached objects within levels. Strategy of redistibuting depends on implementation.
     */
    void recache();
}
