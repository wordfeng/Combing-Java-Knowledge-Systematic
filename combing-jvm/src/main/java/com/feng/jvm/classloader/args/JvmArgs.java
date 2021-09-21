package com.feng.jvm.classloader.args;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.Map;

public class JvmArgs {
    public static void main(String[] args) {

        //启动参数  -Dfeng=wqe
        System.out.println(System.getProperty("feng"));

        //系统变量
        System.out.println(System.getProperty("user.dir"));
        Map<String, String> systemEnv = System.getenv();
        systemEnv.values().forEach(System.out::println);
        systemEnv.forEach((key, value) -> System.out.println(key + ":" + value));

        //jvm信息
        MemoryMXBean memorymbean = ManagementFactory.getMemoryMXBean();
        MemoryUsage usage = memorymbean.getHeapMemoryUsage();
        System.out.println("INIT HEAP: " + usage.getInit());
        System.out.println("MAX HEAP: " + usage.getMax());
        System.out.println("USE HEAP: " + usage.getUsed());

    }
}
