package com.live.activity;

import com.live.utils.MyHashMap;

public class TestMap {


    public static void main(String[] args) {
        MyHashMap<String, String> mUserWindowMap = new MyHashMap<>();
        mUserWindowMap.put("token1", "老师1");
        mUserWindowMap.put("token2", "学生2");
        mUserWindowMap.replacePositonByValue("老师1", "学生2");


        System.out.println(mUserWindowMap.toString());
        System.out.println(mUserWindowMap.getOrderedValues().toString());
    }
}
