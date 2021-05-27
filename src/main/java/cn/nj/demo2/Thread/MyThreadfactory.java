package cn.nj.demo2.Thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;


public class MyThreadfactory implements ThreadFactory {
    private final AtomicInteger sequence =new AtomicInteger(0);

    private final String prefix;

    public MyThreadfactory(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public Thread newThread(Runnable r) {
        assert r!=null;
        Thread thread = new Thread(r);
        int seq = sequence.getAndIncrement();
        thread.setName(prefix+seq);
        if(!thread.isDaemon()){
            thread.setDaemon(true);
        }
        return thread;
    }
}
