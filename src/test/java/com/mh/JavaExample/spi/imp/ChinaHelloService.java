package com.mh.JavaExample.spi.imp;

/**
 * 中国式的招呼
 * Start at: 2018/5/1 16:17
 *
 * @author muhong
 */
public class ChinaHelloService implements HelloService {
    @Override
    public void sayHello() {
        System.out.println("你好，世界！");
    }
}
