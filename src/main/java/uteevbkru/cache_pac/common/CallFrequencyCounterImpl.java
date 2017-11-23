package uteevbkru.cache_pac.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import uteevbkru.cache_pac.api.DoesNotExistException;


public class CallFrequencyCounterImpl <K> implements CallFrequencyCounter<K> {

    private Map<K, Integer> frequencyMap = new HashMap<K, Integer>();

    @Override
    public void register(K key) {
        frequencyMap.put(key, Integer.valueOf(0));
    }

    @Override
    public void call(K key) throws DoesNotExistException {
        if (frequencyMap.containsKey(key)) {
            Integer frequency = frequencyMap.get(key);
            frequency++;
            frequencyMap.put(key, frequency);
        } else {
            throw new DoesNotExistException();
        }
    }

    @Override
    public int getFrequency(K key) throws DoesNotExistException {
        if (frequencyMap.containsKey(key)) {
            return frequencyMap.get(key);
        } else {
            throw new DoesNotExistException();
        }
    }

    @Override
    public List<K> getMostFrequent(int amount) {
        /*
         * First of all, creating a list of all entries in the map. Then sort it by the integer value, not by the key;
         * and then return only {amount} of them
         */
        List<Entry<K, Integer>> freqs = new ArrayList<Map.Entry<K, Integer>>(frequencyMap.entrySet());

        Collections.sort(freqs, new Comparator<Entry<K, Integer>>() {
            @Override
            public int compare(Entry<K, Integer> o1, Entry<K, Integer> o2) {
                return -o1.getValue().compareTo(o2.getValue());
            }
        });

        List<K> result = new ArrayList<K>();

        for (int i = 0; i < amount; ++i) {
            result.add(freqs.get(i).getKey());
        }

        return result;
    }

    @Override
    public List<K> getMostFrequent() {
        return getMostFrequent(frequencyMap.size());
    }

    @Override
    public void reset() {
        frequencyMap.clear();
    }

    @Override
    public void removeElement(K key) {
        frequencyMap.remove(key);
    }

}
