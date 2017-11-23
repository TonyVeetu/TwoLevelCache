package uteevbkru.cache_pac.strategy;

import java.io.Serializable;

import uteevbkru.cache_pac.api.Cache;
import uteevbkru.cache_pac.builder.MultiLevelCache;
import uteevbkru.cache_pac.builder.OneLevelCache;
import uteevbkru.cache_pac.caching.disk.FileCache;
import uteevbkru.cache_pac.caching.disk.strategy.StandartMarshaller;
import uteevbkru.cache_pac.caching.memory.MemoryCache;

public class CacheStrategies <K extends Serializable, V extends Serializable> {
    /*
     * May be moved in several classes
     */
    public final CacheHolder<K, V> ONE_LEVEL_MEMORY = new CacheHolder<K, V>() {
        private OneLevelCache<K, V> cacheHolder;
        {
            this.cacheHolder = new OneLevelCache<K, V>(new MemoryCache<K, V>());
        }

        @Override
        public Cache<K, V> getCache() {
            return cacheHolder.asCache();
        }

        @Override
        public void recache() {
            this.cacheHolder.recache();
        }
    };

    public final CacheHolder<K, V> ONE_LEVEL_HARDDISK = new CacheHolder<K, V>() {
        private OneLevelCache<K, V> cacheHolder;

        {
            FileCache<K, V> cache = new FileCache<K, V>();
            cache.setMarshaller(new StandartMarshaller<K, V>());
            this.cacheHolder = new OneLevelCache<K, V>(cache);
        }

        @Override
        public Cache<K, V> getCache() {
            return cacheHolder.asCache();
        }

        @Override
        public void recache() {
            this.cacheHolder.recache();
        }
    };

    public final CacheHolder<K, V> TWO_LEVELS_MEMORY_HARDDISK = new CacheHolder<K, V>() {
        private MultiLevelCache<K, V> cacheHolder;

        {
            // using default constructor of MultipleLevelCache which provides
            // needed two levels caching
            this.cacheHolder = new MultiLevelCache<K, V>();
        }

        @Override
        public Cache<K, V> getCache() {
            return cacheHolder.asCache();
        }

        @Override
        public void recache() {
            this.cacheHolder.recache();
        }
    };

}
