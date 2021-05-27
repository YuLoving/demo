package cn.nj.demo2.thread;

import cn.hutool.core.date.DateUtil;
import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.threadpool.TtlExecutors;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author ZTY
 * @classname TestThreadLocal
 * @description TODO
 * @date 2020/12/1715:37
 */
public class TestThreadLocal {

    public static void main(String[] args) {
        //threadlocaltest1();
        //testThreadLocal2();
        //testThreadLocal3();
        //testThreadLocal4();
        testThreadLocal5();
    }

    /**
     * ThreadLocal在开启子线程时，父线程向子线程值传递问题
     */
    private static void testThreadLocal2() {
        ThreadLocal<Object> threadLocal = new ThreadLocal<>();
        threadLocal.set("初始化的值能继承吗？");
        System.out.println(Thread.currentThread().getName()+":父线程的ThreadLocal值：" +threadLocal.get());
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("子线程到了");
                System.out.println(Thread.currentThread().getName()+":子线程的ThreadLocal值：" + threadLocal.get());
            }
        }).start();
    }
    /**
     * InheritableThreadLocal 在开启子线程时，父线程向子线程值传递
     */
    private static void testThreadLocal3(){
        ThreadLocal<Object> threadLocal = new InheritableThreadLocal<>();
        threadLocal.set("初始化的值能继承吗？");
        System.out.println(Thread.currentThread().getName()+":父线程的ThreadLocal值：" +threadLocal.get());
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("子线程到了");
                System.out.println(Thread.currentThread().getName()+":子线程的ThreadLocal值：" + threadLocal.get());
            }
        }).start();
    }


    /**
     * 创建一个固定大小为2的线程池，进行五次子线程执行，每次给父线程的threadLocal重新赋值，由于线程池大小为2，因此只有前两次触发初始化线程池，后面三次均为线程复用，理论上来说，子线程可继承的父线程变量到i=1截止。
     * InheritableThreadLocal 在开启子线程时，父线程向子线程值传递
     */
    private static void testThreadLocal4(){
        ThreadLocal<Object> threadLocal = new InheritableThreadLocal<>();
        //创建一个固定大小为2的线程池
        ExecutorService threadPool = Executors.newFixedThreadPool(2);
        for (int i = 0; i <5 ; i++) {
            threadLocal.set("初始化的值能继承吗？"+i);
            System.out.println(Thread.currentThread().getName()+":父线程的ThreadLocal值：" +threadLocal.get());
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("子线程到了");
                    System.out.println(Thread.currentThread().getName()+":子线程的ThreadLocal值：" + threadLocal.get());
                }
            });
        }

    }

    /**
     * testThreadLocal4 问题在于线程池中线程复用没有触发条件，而只要在子线程执行时加入复制父线程的threadLocal即可。
     * 而原生的线程池创建方法不提供此功能，因此需要单独的线程池创建类支持，TtlExecutors，同样也是阿里开源的。
     * TransmittableThreadLocal
     */
    private static void testThreadLocal5(){
        TransmittableThreadLocal<Object> threadLocal = new TransmittableThreadLocal<>();
        //创建一个固定大小为2的线程池
        ExecutorService ttlExecutorService = TtlExecutors.getTtlExecutorService(Executors.newFixedThreadPool(2));
        for (int i = 0; i <5 ; i++) {
            threadLocal.set("初始化的值能继承吗？"+i);
            System.out.println(Thread.currentThread().getName()+":父线程的ThreadLocal值：" +threadLocal.get());
            ttlExecutorService.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("子线程到了");
                    System.out.println(Thread.currentThread().getName()+":子线程的ThreadLocal值：" + threadLocal.get());
                }
            });
        }

    }









    private static void threadlocaltest1() {
        //新建一个threadLocal
        ThreadLocal<Object> threadLocal = new ThreadLocal<>();
        //生成一个随机值
        Random random = new Random();
        //使用java8的Stream 新建5个线程
        IntStream.range(0,5).forEach(a-> new Thread(()->{
                //为每个线程设置一个threadLocal值
                threadLocal.set(a+"  "+random.nextInt(10));
                System.out.println(DateUtil.now() +"线程和local值分别是 "+threadLocal.get());
                try {
                    TimeUnit.SECONDS.sleep(1);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
        }).start()
        );
    }
}
