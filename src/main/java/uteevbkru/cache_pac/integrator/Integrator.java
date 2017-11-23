package uteevbkru.cache_pac.integrator;

import java.util.List;

import uteevbkru.cache_pac.api.Cache;
import uteevbkru.cache_pac.api.DoesNotExistException;

public class Integrator <K, V> implements Cache<K, V> {

    private List<Cache<K, V>> caches;

    @Override
    public void cache(K key, V value) {
        // caching to the first cache_pac
        caches.get(0).cache(key, value);
    }

    @Override
    public V retrieve(K key) throws DoesNotExistException {

        V value = null;

        for (Cache<K, V> cache : caches) {
            try {
                value = cache.retrieve(key);
                return value;
            } catch (DoesNotExistException dnex) {
                continue;
            }

        }

        throw new DoesNotExistException();
    }

    public void setCaches(List<Cache<K, V>> caches) {
        this.caches = caches;
    }

    public List<Cache<K, V>> getCaches() {
        return caches;
    }

    @Override
    public void clear() {
        for (Cache<K, V> cache : caches) {
            cache.clear();
        }
    }

    @Override
    public void remove(K key) {
        for (Cache<K, V> cache : caches) {
            cache.remove(key);
        }
    }

    @Override
    public int size() {
        int size = 0;

        for (Cache<K, V> cache : caches) {
            size = size + cache.size();
        }

        return size;
    }
}
