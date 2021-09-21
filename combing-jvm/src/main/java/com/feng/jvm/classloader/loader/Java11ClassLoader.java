package com.feng.jvm.classloader.loader;


import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * java9以后 扩展类加载器，应用类加载器，URL类加载器没有了继承关系
 * 无法将前两个转换成URLClassLoader
 */
public class Java11ClassLoader{
    public static void main(String[] args) {
        String path = Java11ClassLoader.class.getResource("/loadclass/").toString();
        System.out.println(path);

        URLClassLoader urlClassLoader = (URLClassLoader) Java11ClassLoader.class.getClassLoader();
        try {
            Method addURL = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
            addURL.setAccessible(true);
            addURL.invoke(urlClassLoader, new URL(path));

            Class.forName("com.feng.jvm.classs.JvmClass", true, urlClassLoader);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
