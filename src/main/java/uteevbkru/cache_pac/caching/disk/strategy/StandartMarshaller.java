package uteevbkru.cache_pac.caching.disk.strategy;

import java.io.Serializable;
import java.io.*;
import java.util.Map.Entry;

public class StandartMarshaller<K extends Serializable, V extends Serializable> implements
        Marshaller<K, V> {

    @Override
    public void marshal(K key, V value, File file) {
        OutputStream os = null;
        ObjectOutputStream oos = null;
        try {
            os = new BufferedOutputStream(new FileOutputStream(file));
            oos = new ObjectOutputStream(os);

            oos.writeObject(key);
            oos.writeObject(value);
            oos.flush();
        } catch (IOException e) {
            throw new RuntimeException("IO exception during unmarchalling", e);
        } finally {
            closeQuietly(oos);
            closeQuietly(os);
        }

    }

    private static void closeQuietly(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (IOException e) {
        }
    }

    @Override
    public Entry<K, V> unmarshall(File file) {
        Entry<K, V> result = null;
        InputStream is = null;
        ObjectInputStream ois = null;

        try {

            is = new BufferedInputStream(new FileInputStream(file));
            ois = new ObjectInputStream(is);

            /*
             * Supposing the type will be always the same
             */
            @SuppressWarnings("unchecked")
            K key = (K) ois.readObject();
            @SuppressWarnings("unchecked")
            V value = (V) ois.readObject();

            result = new SerializableEntry<K, V>(key, value);
        } catch (IOException e) {
            throw new RuntimeException("IO exception during unmarchalling", e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Class not found during unmarchalling", e);
        } finally {
            closeQuietly(ois);
            closeQuietly(is);
        }

        return result;
    }

    /**
     *
     * Inner class to hold entries (pair key-value)
     *
     * @author agrigor
     */
    private static class SerializableEntry<K, V> implements Entry<K, V> {
        private final K key;
        private final V value;

        private SerializableEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            throw new RuntimeException("Operaion is not supported");
        }
    }
}
