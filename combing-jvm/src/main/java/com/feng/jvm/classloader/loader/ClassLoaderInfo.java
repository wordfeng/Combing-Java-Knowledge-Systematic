package com.feng.jvm.classloader.loader;

import sun.misc.Launcher;

import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.List;

public class ClassLoaderInfo {
    public static void main(String[] args) {

        URL[] urLs = Launcher.getBootstrapClassPath().getURLs();
        System.out.println("启动类加载器: ");
        Arrays.stream(urLs).forEach(System.out::println);

        printClassLoader("应用类加载器", ClassLoaderInfo.class.getClassLoader());

        printClassLoader("扩展类加载器", ClassLoaderInfo.class.getClassLoader().getParent());
    }


    private static void printClassLoader(String name, ClassLoader classLoader) {
        if (classLoader != null) {
            System.out.println(name + " ClassLoader: " + classLoader.toString());
            printUrlForClassLoader(classLoader);
        } else {
            System.out.println(name + " ClassLoader: " + null);
        }

    }

    private static void printUrlForClassLoader(ClassLoader classLoader) {
        Object ucp = insightField(classLoader, "ucp");
        Object path = insightField(ucp, "path");

        List ps = (List) path;
        ps.forEach(System.out::println);

    }

    private static Object insightField(Object object, String fieldName) {

        try {
            Field f;
            if (object instanceof URLClassLoader) {
                f = URLClassLoader.class.getDeclaredField(fieldName);
            } else {
                f = object.getClass().getDeclaredField(fieldName);
            }
            f.setAccessible(true);
            return f.get(object);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
