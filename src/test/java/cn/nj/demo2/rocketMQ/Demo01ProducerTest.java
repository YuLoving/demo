package cn.nj.demo2.rocketMQ;

import cn.hutool.core.util.IdUtil;
import cn.nj.demo2.Demo2Application;
import cn.nj.demo2.pojo;
import cn.nj.demo2.rocketMq.Contant.RocketMqBasicConstant;
import cn.nj.demo2.rocketMq.DelayEnum;
import cn.nj.demo2.rocketMq.Message.DemoMessage;
import cn.nj.demo2.rocketMq.RocketMqManager;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ZTY
 * @classname Demo01ProducerTest
 * @description TODO
 * @date 2020/12/115:42
 */
@SpringBootTest(classes = Demo2Application.class)
@RunWith(SpringRunner.class)
public class Demo01ProducerTest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RocketMqManager rocketMqManager;


    //同步发送
    @Test
    public void testSyncSend() throws InterruptedException {
        int id = (int) (System.currentTimeMillis() / 1000);
        DemoMessage message = new DemoMessage();
        message.setId(id);
        message.setTopic("MyOrderTopic");
        //rocketMqManager.syncSend(message, RocketMqBasicConstant.MY_TAG,RocketMqBasicConstant.MY_KEY);

        // 阻塞等待，保证消费
       // new CountDownLatch(1).await();
        List<pojo> list = new ArrayList<>();
        pojo pojo0 = new pojo();
        pojo0.setImei("");
        pojo0.setSn("SD12DS");
        pojo pojo1 = new pojo();
        pojo1.setImei("3123213");
        pojo1.setSn("");
        pojo pojo2 = new pojo();
        pojo2.setImei("");
        pojo2.setSn("");
        pojo pojo3 = new pojo();
        pojo3.setImei("45345345345");
        pojo3.setSn("SD12DSSDSD");
        pojo pojo4 = new pojo();
        pojo4.setImei("343434");
        pojo4.setSn("KK2212");
        list.add(pojo0);
        list.add(pojo1);
        list.add(pojo2);
        list.add(pojo3);
        list.add(pojo4);
        message.setBody(JSON.toJSONString(list));
        rocketMqManager.syncSend(message,"MyOrderTag",null);
    }


    //同步发送 批量消息
   @Test
    public void testSyncBatchSend() throws InterruptedException {
        List<DemoMessage> list = Lists.newArrayList();
        for (int i=0;i<5;i++){
            DemoMessage demoMessage = new DemoMessage();
            demoMessage.setId(i);
            list.add(demoMessage);
        }
        rocketMqManager.syncBatchSend(list);
        // 阻塞等待，保证消费
        new CountDownLatch(1).await();
    }



    //异步发送
    @Test
    public void testAsyncSend() throws InterruptedException {
        int id = (int) (System.currentTimeMillis() / 1000);
        DemoMessage message = new DemoMessage();
        message.setId(id);
        rocketMqManager.asyncSend(message, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                logger.info("[testASyncSend][发送编号：[{}] 发送成功，结果为：[{}]]", id, sendResult);
            }

            @Override
            public void onException(Throwable e) {
                logger.info("[testASyncSend][发送编号：[{}] 发送异常]]", id, e);
            }
        });

        // 阻塞等待，保证消费
        new CountDownLatch(1).await();
    }



    @Test
    public void testOnewaySend() throws InterruptedException {
        int id = (int) (System.currentTimeMillis() / 1000);
        DemoMessage message = new DemoMessage();
        message.setId(id);
        rocketMqManager.oneWaySend(message);
        logger.info("[testOnewaySend][发送编号：[{}] 发送完成]", id);
        // 阻塞等待，保证消费
        new CountDownLatch(1).await();
    }



    @Test
    public void testDelaySyncSend() throws InterruptedException {
        int id = (int) (System.currentTimeMillis() / 1000);
        DemoMessage message = new DemoMessage();
        message.setId(id);
        rocketMqManager.delaySyncSend(message, DelayEnum.TWO.getLevel());
        logger.info("[testDelaySyncSend][发送编号：[{}] 发送完成]", id);
        // 阻塞等待，保证消费
        new CountDownLatch(1).await();
    }

    @Test
    public void testDelayAsyncSend() throws InterruptedException {
        int id = (int) (System.currentTimeMillis() / 1000);
        DemoMessage message = new DemoMessage();
        message.setId(id);
        rocketMqManager.delayAsyncSend(message, DelayEnum.TWO.getLevel(), new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                logger.info("[testDelayAsyncSend][发送编号：[{}] 发送成功，结果为：[{}]]", id, sendResult);
            }

            @Override
            public void onException(Throwable e) {
                logger.info("[testDelayAsyncSend][发送编号：[{}] 发送异常]]", id, e);
            }
        });
        logger.info("[testDelaySyncSend][发送编号：[{}] 发送完成]", id);
        // 阻塞等待，保证消费
        new CountDownLatch(1).await();
    }



    //模拟同步发送失败的重试机制
    @Test
    public void testRetrySyncSend() throws InterruptedException {
        int id = (int) (System.currentTimeMillis() / 1000);
        DemoMessage message = new DemoMessage();
        message.setId(id);
        rocketMqManager.syncSend(message, RocketMqBasicConstant.MY_RETRY_TAG,RocketMqBasicConstant.MY_KEY);

        // 阻塞等待，保证消费
        new CountDownLatch(1).await();
    }








}
