package com.mh.JavaExample.sm;

import java.io.FileInputStream;

/**
 * 安全管理器测试类
 * ==========================
 * 1 首先创建test文件
 * 1.1 运行该测试，System.getSecurityManager()为空，所有操作可以正常进行
 * ==========================
 * 2 运行的配置安全管理器和安全策略文件，使用的是默认的安全管理器
 * （PS：需要注意的是-Djava.security.policy，一个等号=代表同时也默认策略文件生效：java.policy
 *  两个等号==代表只使用后面的策略文件，不是用默认的策略文件）
 *     VM options: -Djava.security.manager -Djava.security.policy="...\JavaExample\src\test\resources\conf\sjava.policy"
 * 2.1 可以看到输出结果是必一样的：
 *
 * 详情参考博客:http://47777205.com/view/24
 */
public class TestMain {

    public static void main(String[] args) {
        // 获取系统的SecurityManager
        System.out.println("系统当前的安全管理器："+System.getSecurityManager());

        String testFile = "C:\\Users\\Administrator\\Desktop\\fileTest.txt";
        try {
            FileInputStream fis = new FileInputStream(testFile);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try{
            // 正常读取（相关权限已经在java.policy中配置）
            // -Djava.security.policy= 这样配置的时候不抛异常
            // -Djava.security.policy==这样配置的时候会抛异常
            System.out.println(System.getProperty("java.version"));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        try{
            // 读取异常（相关权限没有进行配置，需要在sjava.policy中配置）
            System.out.println(System.getProperty("file.encoding"));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
