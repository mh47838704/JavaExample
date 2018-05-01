package com.mh.JavaExample.spi;

import com.mh.JavaExample.spi.imp.AmericaHelloService;
import com.mh.JavaExample.spi.imp.ChinaHelloService;
import com.mh.JavaExample.spi.imp.HelloService;
import org.junit.Test;
/**
 * 测试接口
 * Start at: 2018/5/1 16:22
 *
 * @author muhong
 */
public class TestMain {

    @Test
    public void testInterface(){
        HelloService ch = new ChinaHelloService();
        ch.sayHello();
        HelloService en = new AmericaHelloService();
        en.sayHello();
    }
}
