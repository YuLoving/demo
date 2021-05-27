package cn.nj.demo2.Thread;

import com.alibaba.ttl.threadpool.TtlExecutors;

import java.util.concurrent.*;

public class MyThreadPool {

    private static volatile ExecutorService executors;

    private static final int corePoolSize = 60;

    private static final int maxPoolSize = 200;

    private static final int queueCapacity = 10000;

    private static final Long keepAliveSeconds = 60L;

    static {
        if (null == executors) {
            synchronized (MyThreadPool.class) {
                //默认使用拒绝策略直接抛出异常
                executors = TtlExecutors.getTtlExecutorService(new ThreadPoolExecutor(
                        corePoolSize,
                        maxPoolSize,
                        keepAliveSeconds,
                        TimeUnit.SECONDS,
                        new LinkedBlockingQueue<>(queueCapacity),
                        new MyThreadfactory("my-thread-task-")
                ));
            }
        }
    }

    private MyThreadPool() {

    }

    /**
     * 获取当前ExecutorService信息
     * @return executors
     */
    public static ExecutorService getExecutors() {
        return executors;
    }

    /**
     *多线程并发操作
     * @param task  Callable
     * @param <T>  T
     * @return  Future<T>
     */
    public static <T> Future<T> submit(Callable<T> task) {
      return   executors.submit(task);
    }

    /**
     * 线程池实现 异步操作
     * @param command
     */
    public  static void execute(Runnable command){
        executors.execute(command);
    }

    /**
     *多线程 加 计数器控制
     * @param command
     * @param countDownLatch
     */
    public  static void execute(Runnable command,CountDownLatch countDownLatch){
        executors.execute(()->{
            try {
                command.run();
            }catch (Throwable e){
                throw new RuntimeException(e);
            }finally {
                countDownLatch.countDown();
            }
        });
    }





}






