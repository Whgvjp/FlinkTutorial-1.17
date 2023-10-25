package com.atguigu.partition;

import org.apache.flink.api.java.tuple.Tuple2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapExample {
    public static void main(String[] args) {
        // 创建一个 Map，键是整数，值是元组列表
        Map<Integer, List<Tuple2<Integer, String>>> s1Cache = new HashMap<>();
        // 插入测试数据
        int key1 = 1;
        Tuple2<Integer, String> entry1 = Tuple2.of(1, "Value1");
        Tuple2<Integer, String> entry2 = Tuple2.of(2, "Value2");

        // 创建键1对应的列表并插入数据
        List<Tuple2<Integer, String>> list1 = new ArrayList<>();
        list1.add(entry1);
        list1.add(entry2);

        // 将列表放入映射
        s1Cache.put(key1, list1);

        // 插入更多数据
        int key2 = 2;
        Tuple2<Integer, String> entry3 = Tuple2.of(3, "Value3");
        Tuple2<Integer, String> entry4 = Tuple2.of(4, "Value4");

        List<Tuple2<Integer, String>> list2 = new ArrayList<>();
        list2.add(entry3);
        list2.add(entry4);

        s1Cache.put(key2, list2);

        List<Tuple2<Integer, String>> tuple2s = s1Cache.get(1);
        System.out.println(tuple2s);

    }
}
