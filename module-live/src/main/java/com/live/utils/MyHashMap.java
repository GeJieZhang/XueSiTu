package com.live.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class MyHashMap<K, V> extends HashMap<K, V> {

    private List<V> mOrderedValues = new LinkedList<>();


    /**
     * 获取列表
     *
     * @return
     */
    public List<V> getOrderedValues() {
        return new ArrayList<>(mOrderedValues);
    }


    /**
     * 获取到移除当前Value之后的列表
     *
     * @param exclude
     * @return
     */
    public List<V> getOrderedValues(V exclude) {
        LinkedList<V> result = new LinkedList<>(mOrderedValues);
        result.remove(exclude);
        return result;
    }


    /**
     * 将数据添加到第一个位置
     *
     * @param key
     * @param value
     * @param insertToFirst
     * @return
     */
    public V put(K key, V value, boolean insertToFirst) {
        // in case replace key
        mOrderedValues.remove(get(key));
        // in case replace value
        mOrderedValues.remove(value);

        if (insertToFirst) {
            mOrderedValues.add(0, value);
        } else {
            mOrderedValues.add(value);
        }
        return super.put(key, value);
    }

    /**
     * 将数据添加到指定位置，不超过当前索引
     *
     * @param key
     * @param value
     * @param position
     * @return
     */
    public V putPosition(K key, V value, int position) {
        // in case replace key
        mOrderedValues.remove(get(key));
        // in case replace value
        mOrderedValues.remove(value);
        mOrderedValues.add(position, value);

        return super.put(key, value);
    }

    @Override
    public V put(K key, V value) {


        return put(key, value, false);
    }

    @Override
    public V remove(Object key) {
        V result = super.remove(key);

        mOrderedValues.remove(result);
        return result;
    }


    public void replacePositonByKey(K key1, K key2) {


        V value1 = get(key1);
        V value2 = get(key2);
        int position1 = mOrderedValues.indexOf(value1);
        int position2 = mOrderedValues.indexOf(value2);
        mOrderedValues.remove(value2);
        mOrderedValues.add(position1, value2);


        mOrderedValues.remove(value1);
        mOrderedValues.add(position2, value1);
        super.put(key1, value1);
        super.put(key2, value2);
    }


    public void replacePositonByValue(V value1, V value2) {


        int position1 = mOrderedValues.indexOf(value1);
        int position2 = mOrderedValues.indexOf(value2);
        mOrderedValues.remove(value2);
        mOrderedValues.add(position1, value2);
        mOrderedValues.remove(value1);
        mOrderedValues.add(position2, value1);

    }

    @Override
    public void clear() {
        mOrderedValues.clear();
        super.clear();
    }
}
