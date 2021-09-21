package com.feng.jvm.classloader.loader;


import java.lang.reflect.Method;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * load Hello.xlass with decrypt inverse code
 */
public class TestClassLoader extends ClassLoader {

    //key:full name, value: uri
    private final Map<String, String> urlMap = new HashMap<>();


    public static void main(String[] args) throws Exception {
        URL resource = TestClassLoader.class.getResource("/xclasses/Hello.xlass");
        String path = resource.getPath();

        TestClassLoader loader = new TestClassLoader();
        loader.urlMap.put("Hello", path);

        Class<?> helloClass = loader.loadClass("Hello");
        Object instance = helloClass.newInstance();
        Method[] declaredMethods = helloClass.getDeclaredMethods();

        for (Method declaredMethod : declaredMethods) {
            System.out.println(declaredMethod.getName());
            declaredMethod.invoke(instance);
        }


    }

    @Override
    protected Class<?> findClass(String name) {
        byte[] bytes = loadClassData(this.urlMap.get(name));
        return defineClass(name, bytes, 0, bytes.length);
    }

    private static byte[] loadClassData(String url) {

        try {
            Path path = Paths.get(url);
            byte[] clsBytes = Files.readAllBytes(path);
            for (int i = 0; i < clsBytes.length; i++) {
                clsBytes[i] = (byte) (255 - clsBytes[i]);
            }
            {
                Path out = Paths.get(url.replace("xlass", "class"));
                if (!Files.exists(out)) {
                    Files.createFile(out);
                }
                Files.write(out, clsBytes);
            }
            return clsBytes;
        } catch (Exception e) {
            e.printStackTrace();
            return new byte[0];
        }
    }

}
