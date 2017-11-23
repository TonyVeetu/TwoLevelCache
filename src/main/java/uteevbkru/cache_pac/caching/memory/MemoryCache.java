package uteevbkru.cache_pac.caching.memory;

import java.util.HashMap;
import java.util.Map;

import uteevbkru.cache_pac.api.Cache;
import uteevbkru.cache_pac.api.DoesNotExistException;

public class MemoryCache<K, V> implements Cache<K, V> {

    private final Map<K, V> map = new HashMap<K, V>();

    @Override
    public void cache(K key, V value) {
        map.put(key, value);
    }

    @Override
    public V retrieve(K key) throws DoesNotExistException {
        if (!map.containsKey(key)) {
            throw new DoesNotExistException();
        }

        return map.get(key);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public void remove(K key) {
        map.remove(key);
    }

    @Override
    public int size() {
        return map.size();
    }
}
