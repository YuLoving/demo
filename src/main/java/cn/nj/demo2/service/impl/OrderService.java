package cn.nj.demo2.service.impl;

import cn.nj.demo2.Thread.MyThreadPool;
import cn.nj.demo2.mapper.OrderMapper;
import cn.nj.demo2.pojo.MyOrderBO;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author ：zty
 * @date ：Created in 2020/12/24 18:12
 * @description ：
 */
@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;


    public void test() throws InterruptedException {
        long start = System.currentTimeMillis();
        List<MyOrderBO> list1 = Lists.newArrayList();
        for (int i = 0; i < 100; i++) {
            MyOrderBO orderPO = new MyOrderBO();
            orderPO.setStatus(i);
            orderPO.setName("第" + i + "号");
            orderPO.setType(i);
            orderPO.setGmtCreate(LocalDateTime.now());
            list1.add(orderPO);
        }
        List<MyOrderBO> list2 = Lists.newArrayList();
        for (int i = 100; i < 200; i++) {
            MyOrderBO orderPO = new MyOrderBO();
            orderPO.setStatus(i);
            orderPO.setName("第" + i + "号");
            orderPO.setType(i);
            orderPO.setGmtCreate(LocalDateTime.now());
            list2.add(orderPO);
        }
        //第一次  批量插入
        orderMapper.batchAdd(list1);
        Thread.sleep(1000);
        //第2次  批量插入
        orderMapper.batchAdd(list2);
        //两次同步操作 总共耗时
        long end = System.currentTimeMillis() - start;
        System.out.println(end);
    }


    public void Threadtest() throws InterruptedException {
        long start = System.currentTimeMillis();
        List<MyOrderBO> list1 = Lists.newArrayList();
        for (int i = 0; i < 100; i++) {
            MyOrderBO orderPO = new MyOrderBO();
            orderPO.setStatus(i);
            orderPO.setName("第" + i + "号");
            orderPO.setType(i);
            orderPO.setGmtCreate(LocalDateTime.now());
            list1.add(orderPO);
        }
        List<MyOrderBO> list2 = Lists.newArrayList();
        for (int i = 100; i < 200; i++) {
            MyOrderBO orderPO = new MyOrderBO();
            orderPO.setStatus(i);
            orderPO.setName("第" + i + "号");
            orderPO.setType(i);
            orderPO.setGmtCreate(LocalDateTime.now());
            list2.add(orderPO);
        }
        //使用多线程操作
        MyThreadPool.submit(() -> orderMapper.batchAdd(list1));
        MyThreadPool.submit(() -> orderMapper.batchAdd(list2));
        Thread.sleep(1000);
        //多线程操作操作 总共耗时
        long end = System.currentTimeMillis() - start;
        System.out.println(end);
    }

    public List<MyOrderBO> search(){
        MyOrderBO orderBO = new MyOrderBO();
        return orderMapper.search(orderBO);
    }
}
