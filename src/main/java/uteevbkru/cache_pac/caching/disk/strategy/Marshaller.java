package uteevbkru.cache_pac.caching.disk.strategy;

import java.io.File;
import java.io.Serializable;
import java.util.Map;

public interface Marshaller<K extends Serializable, V extends Serializable> {

    Map.Entry<K, V> unmarshall(File file);

    void marshal(K key, V value, File file);
}
