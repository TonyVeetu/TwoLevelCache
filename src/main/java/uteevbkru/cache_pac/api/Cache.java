package uteevbkru.cache_pac.api;

public interface Cache<K, V> {
    void cache(K key, V value);
    V retrieve(K key) throws DoesNotExistException;
    void remove(K key);
    void clear();
    int size();
}
