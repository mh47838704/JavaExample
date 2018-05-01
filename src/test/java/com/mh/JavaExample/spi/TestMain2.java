package com.mh.JavaExample.spi;

import org.junit.Test;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * 测试SPI
 * Start at: 2018/5/1 17:25
 *
 * @author muhong
 */
public class TestMain2 {


    @Test
    public void testSpi(){
        ServiceLoader<HelloService> services = ServiceLoader.load(HelloService.class);
        for (Iterator<HelloService> iterator = services.iterator(); iterator.hasNext(); ) {
            HelloService helloService = iterator.next();
            helloService.sayHello();
        }
    }
}
