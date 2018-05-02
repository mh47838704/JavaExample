package com.mh.JavaExample.cloader;

import org.junit.Test;

import java.lang.reflect.Method;

/**
 * 测试类加载机制
 * Start at: 2018/3/27 23:53
 *
 * @author xx
 */
public class TestMain {


    @Test
    public void test(){
        // 需要加载的class文件所在的路径
        String classPath = getResourcePath();
        if(classPath == null){
            return;
        }
        ClassLoader classLoader = new SelfClassLoader(classPath);
        // 需要加载的class文件的名称
        // 如果包含了package信息，那么需要把整个目录拷贝到上面的路径下
        String className = "com.mh.JavaExample.cloader.CLoaderHello";
        try {
            Class cls = classLoader.loadClass(className);
            Object target = cls.newInstance();
            Method method = cls.getMethod("sayHello");
            method.invoke(target);
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testPath(){
        System.out.println(getResourcePath());
    }

    private String getResourcePath(){
        String rootPath = this.getClass().getResource("/").getPath();
        if(rootPath.length() > 1){
            int index = rootPath.indexOf("classes");
            rootPath = rootPath.substring(1, index);
            rootPath = rootPath.replace("/", "\\");
            rootPath += "resources\\sclass\\";
            System.out.println(rootPath);
            return rootPath;
        }
        System.out.println("Path not correct");
        return null;
    }
}
