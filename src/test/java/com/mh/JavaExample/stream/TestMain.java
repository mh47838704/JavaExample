package com.mh.JavaExample.stream;

import org.junit.Before;
import org.junit.Test;

public class TestMain {

    ImmutableArrayList immutableArrayList;

    @Before
    public void init(){
        int dataSize = 4;
        String[] data = new String[dataSize];
        String[] tags = new String[dataSize];
        for (int i = 0; i < dataSize; i++){
            data[i] = "Data:" + i;
            tags[i] = "Tags" + i;
        }
        immutableArrayList = new ImmutableArrayList(data, tags);
    }

    @Test
    public void testSteam(){
        System.out.println("普通流测试");
        System.out.println("打印所有信息：");
        immutableArrayList.stream().forEach(System.out::println);
        System.out.println("过滤并打印Data信息：");
        immutableArrayList.stream().filter(
                param -> {
                    String str = (String)param;
                    return str.startsWith("Data");
                }
        ).forEach(System.out::println);
    }

    @Test
    public void testParallelStream(){
        System.out.println("并发流测试");
        System.out.println("打印所有信息：");
        immutableArrayList.stream().forEach(System.out::println);
        System.out.println("过滤并打印Data信息：");
        immutableArrayList.parallelStream().filter(
                param -> {
                    String str = (String)param;
                    return str.startsWith("Data");
                }
        ).forEach(System.out::println);
    }
}
