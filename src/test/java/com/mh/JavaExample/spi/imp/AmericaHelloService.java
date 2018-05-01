package com.mh.JavaExample.spi.imp;

/**
 * 美国式的招呼
 * Start at: 2018/5/1 16:18
 *
 * @author muhong
 */
public class AmericaHelloService implements HelloService {
    @Override
    public void sayHello() {
        System.out.println("Hello world!");
    }
}
