package com.mh.JavaExample.fk;

import org.junit.Test;

import java.util.concurrent.ForkJoinPool;

/**
 * 测试fk
 * Start at: 2018/5/27 13:31
 *
 * @author muhong
 */
public class TestMain {

    @Test
    public void test(){
        long[] dataArray = {1, 2, 3, 4, 5, 6, 7};
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        MCountTask countTask = new MCountTask(dataArray, 0, dataArray.length);
        forkJoinPool.submit(countTask);

    }
}
