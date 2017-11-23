package uteevbkru.cache_pac.caching;


import uteevbkru.cache_pac.api.Cache;
import uteevbkru.cache_pac.api.DoesNotExistException;
import uteevbkru.cache_pac.common.CallFrequencyCounter;
import uteevbkru.cache_pac.common.CallFrequencyCounterImpl;

public class CacheCounterInterceptor <K, V> implements Cache<K, V> {

    final private Cache<K, V> innerCache;
    private CallFrequencyCounter<K> callFrequencyCounter = new CallFrequencyCounterImpl<K>();

    public CacheCounterInterceptor(Cache<K, V> innerCache) {
        this.innerCache = innerCache;
    }

    @Override
    public void cache(K key, V value) {
        getCallFrequencyCounter().register(key);
        innerCache.cache(key, value);
    }

    @Override
    public V retrieve(K key) throws DoesNotExistException {
        // logging how often we retrieve an object
        getCallFrequencyCounter().call(key);
        return innerCache.retrieve(key);
    }

    @Override
    public void clear() {
        innerCache.clear();
        getCallFrequencyCounter().reset();
    }

    @Override
    public void remove(K key) {
        innerCache.remove(key);
    }

    public void setCallFrequencyCounter(CallFrequencyCounter<K> callFrequencyCounter) {
        this.callFrequencyCounter = callFrequencyCounter;
    }

    public CallFrequencyCounter<K> getCallFrequencyCounter() {
        return callFrequencyCounter;
    }

    @Override
    public int size() {
        return innerCache.size();
    }

}
