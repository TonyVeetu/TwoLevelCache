package uteevbkru.cache_pac.caching.disk;

import java.io.File;
import java.io.Serializable;
import java.util.Map.Entry;
import java.util.UUID;

import uteevbkru.cache_pac.api.Cache;
import uteevbkru.cache_pac.api.DoesNotExistException;
import uteevbkru.cache_pac.caching.disk.strategy.Marshaller;


public class FileCache <K extends Serializable, V extends Serializable> implements Cache<K, V> {

    private String DIR = "cache_pac/";

    private int elementsAdded;

    private Marshaller<K, V> marshaller;

    /**
     * Default constructor. If used, marshaller has to be set manually via
     * {@link FileCache#setMarshaller(Marshaller)}.
     */
    public FileCache() {
    }

    public FileCache(Marshaller<K, V> marshaller) {
        this.marshaller = marshaller;
    }

    /**
     * Looks up the value in the given directory
     *
     * @param dir
     * @return File with value or null if none found
     */
    private File lookThroughDir(File dir, K key) {
        File[] files = dir.listFiles();
        for (File f : files) {
            Entry<K, V> entry = marshaller.unmarshall(f);

            if (entry.getKey().equals(key)) {
                return f;
            }
        }

        return null;
    }

    @Override
    public void cache(K key, V value) {
        String hash = String.valueOf(key.hashCode());

        String dirName = getDIR() + hash + "/";
        File dir = new File(dirName);

        File toUse = null;

        if (!dir.exists()) {

            dir.mkdirs();
            toUse = new File(dirName + randomString());
            elementsAdded++;

        } else {

            toUse = lookThroughDir(dir, key);

            if (toUse == null) {
                toUse = new File(dirName + randomString());
                elementsAdded++;
            }

        }

        marshaller.marshal(key, value, toUse);
    }

    private String randomString() {
        return UUID.randomUUID().toString();
    }

    @Override
    public V retrieve(K key) throws DoesNotExistException {
        String hash = String.valueOf(key.hashCode());

        String dirName = getDIR() + hash + "/";
        File dir = new File(dirName);

        if (!dir.exists()) {
            throw new DoesNotExistException();
        }

        File toUse = lookThroughDir(dir, key);

        if (toUse == null) {
            throw new DoesNotExistException();
        }

        V result = marshaller.unmarshall(toUse).getValue();

        return result;
    }

    @Override
    public void remove(K key) {
        String hash = String.valueOf(key.hashCode());

        String dirName = getDIR() + hash + "/";
        File dir = new File(dirName);

        if (dir.exists()) {
            File toUse = lookThroughDir(dir, key);

            if (toUse != null) {
                toUse.delete();
                elementsAdded--;
            }
        }
    }

    public void setMarshaller(Marshaller<K, V> marshaller) {
        this.marshaller = marshaller;
    }

    public Marshaller<K, V> getMarshaller() {
        return marshaller;
    }

    public void setDIR(String dIR) {
        DIR = dIR;
    }

    public String getDIR() {
        return DIR;
    }

    @Override
    public void clear() {
        recurseDelete(new File(getDIR()));
    }

    /**
     * Recursive deletes all the files
     *
     * @param file
     */
    private void recurseDelete(File file) {
        for (File inner : file.listFiles()) {
            if (inner.isDirectory()) {
                recurseDelete(inner);
            } else {
                boolean deleted = inner.delete();
                assert deleted;
            }
        }
    }

    @Override
    public int size() {
        return elementsAdded;
    }
}
