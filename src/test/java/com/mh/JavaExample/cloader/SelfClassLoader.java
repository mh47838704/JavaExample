package com.mh.JavaExample.cloader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 自定义的类加载器
 * Start at: 2018/3/27 23:53
 *
 * @author xx
 */
public class SelfClassLoader extends ClassLoader {

    private final String CLASS_SUFFIX = ".class";

    // 该加载器默认加载class的路径
    private String classPath;

    public SelfClassLoader(String classPath){
        this.classPath = classPath;
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        /*
         * name是全限定名，需要确保那么classPath下面有全限定名对应的文件夹
         * 如果name：com.mh.RemoteHello，那么classPath下面必须有对应的文件路径
         * classPath+com/mh/RemoteHello.class
         */
        System.out.println("==============Start to load class("+name+")=============");
        if (null == classPath || null == name){
            throw new ClassNotFoundException("Classpath or name is null:"+classPath
                    +",name:"+name);
        }
        // getParent():Launcher的AppClassLoader（应用加载器）
        // getParent().getParent():Launcher的ExtClassLoader（系统扩展加载器）
        ClassLoader extClassLoader = getParent().getParent();
        Class<?> cls = null;
        try{
            // 首先交由系统加载器加载，如果系统加载器抛出了class not found
            // 那么由自定义加载器进行加载
            cls = extClassLoader.loadClass(name);
        }catch (ClassNotFoundException e){
            System.out.println("ExtClassLoader can not load class");
        }
        /*
         * 如果jvm系统的加载器不能加载，那么子类继续加载
         * 因为如果通过该类加载器加载应用类，那么应用类所引用的所有的类（包括系统类）
         * 都会通过该类加载，对于系统类来说首先交由系统加载器加载，但是系统加载器无法
         * 加载用户自己写的类，所以这样的类需要用户自己加载
         */
        if(cls != null){
            System.out.println("Parent load class success:"+name);
            return cls;
        }
        System.out.println("Self classloader load class success:"+name);
        cls = findClass(name);
        System.out.println("==============End to load class("+name+")=============");
        return cls;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String pathName = name.replace("." ,"\\");
        String classFilePath = classPath + pathName + CLASS_SUFFIX;
        Class<?> cls = null;
        try {
            byte[] classBytes = getClassFileBytes(classFilePath);
            // 从字节数组生成class对象
            cls = defineClass(name, classBytes, 0, classBytes.length);
        } catch (Exception e) {
            throw new ClassNotFoundException(e.getMessage());
        }
        return cls;
    }

    /**
     * 读取class文件的字节数组
     * @param classFilePath class文件的绝对路径
     * @return 字节数组
     * @throws Exception 文件不存在或者是读取字节数组失败
     */
    private byte[] getClassFileBytes(String classFilePath) throws Exception{
        File f = new File(classFilePath);
        if (!f.exists()) {
            throw new FileNotFoundException("Can not find file");
        }
        FileChannel channel = null;
        FileInputStream fs = null;
        try {
            fs = new FileInputStream(f);
            channel = fs.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate((int) channel.size());
            while ((channel.read(byteBuffer)) > 0) {
            }
            return byteBuffer.array();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                if(channel != null){
                    channel.close();
                }
                if(fs != null){
                    fs.close();
                }
            } catch (IOException e) {
                throw e;
            }
        }
    }
}