package com.dgsoft.common;

import java.util.Map;

/**
 * Created by cooper on 7/18/15.
 */
public class Entry<K,V> implements Map.Entry<K,V> {

    private K key;

    private V value;

    public Entry(K key,V value) {
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
        this.value = value;
        return value;
    }
}
