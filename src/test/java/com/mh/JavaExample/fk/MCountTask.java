package com.mh.JavaExample.fk;

import java.util.concurrent.RecursiveTask;

/**
 * 求和递归任务
 * Start at: 2018/5/27 13:11
 *
 * @author muhong
 */
public class MCountTask extends RecursiveTask {

    private static int FACTOR = 2;

    private long[] dataArray;
    private int        from;
    private int         end;

    public MCountTask(long[] dataArray, int from, int end){
        super();
        this.dataArray = dataArray;
        this.from = from;
        this.end = end;
    }
    @Override
    protected Long compute() {
        long result = 0;
        // 参数校验
        if (!validate())
            return result;
        // 业务逻辑
        System.out.println(Thread.currentThread().getId()+"：开始计算");
        if((end - from) < FACTOR){
            for (int i = from; i < end; i++){
                result += dataArray[i];
            }
            return result;
        }
        // 任务划分逻辑
        int mid = (from + end) / 2;
        MCountTask left = new MCountTask(dataArray, from, mid);
        MCountTask right = new MCountTask(dataArray, mid, end);
        left.fork();
        right.fork();
        left.join();
        right.join();
        long leftResult = (long)left.getRawResult();
        long rightResult = (long)right.getRawResult();
        result += leftResult + rightResult;
        System.out.println(Thread.currentThread().getId()+":"+leftResult+","
                +rightResult+","+result);
        return result;
    }

    private boolean validate(){
        if(this.dataArray == null || from < 0 || end < 0
                || from > end)
            return false;
        return true;
    }
}
