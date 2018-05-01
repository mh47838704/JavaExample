package com.mh.JavaExample.spi;

import org.junit.Test;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * 测试SPI
 * 关于SPI的jar包的打包方式，可以参考oracle的官网说明：https://docs.oracle.com/javase/tutorial/sound/SPI-intro.html
 * 本次SPI的测试jar包也是参考该说明打包的：
 * 1、和spi相关的class文件拷贝到一个临时目录下/tmp(用户自定创建，和打包无关)
 * 2、在该目录下创建META-INF目录，在META-INF目录下创建services目录，在目录下创建com.mh.JavaExample.spi.HelloService文件
 * 3、在该文件中输入SPI接口的名字，如com.mh.JavaExample.spi.AmericaHelloService
 * 4、进入到临时根目录/tmp中，执行命令jar cvf AmericaHelloService.jar -C . .
 * 5、在该根目录下就可以看到AmericaHelloService.jar了
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
