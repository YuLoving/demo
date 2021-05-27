package cn.nj.demo2.rocketMq;

import cn.nj.demo2.rocketMq.Contant.RocketMqBasicConstant;
import cn.nj.demo2.rocketMq.Message.DemoMessage;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.common.message.MessageConst;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.assertj.core.internal.bytebuddy.implementation.bytecode.Throw;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ZTY
 * @classname RocketMqManager
 * @description RocketMq基本的工具类
 * @date 2020/11/3011:48
 */
@Component
public class RocketMqManager {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Autowired
    private TransactionMQProducer transactionMQProducer;

    /**
     * 同步发送消息
     *
     * @param message 消息
     * @return SendResult
     */
    public SendResult syncSend(DemoMessage message, String tag, String key) {
        String destination;
        Message<DemoMessage> newMessage;
        if (StringUtils.isNotBlank(tag)) {
            //destination formats: `topicName:tags`
           // destination = RocketMqBasicConstant.MY_TOPIC + ":" + tag;
            destination = message.getTopic() + ":" + tag;
        } else {
            destination = RocketMqBasicConstant.MY_TOPIC;
        }
        if (StringUtils.isNotBlank(key)) {
            newMessage = MessageBuilder.withPayload(message).setHeader(MessageConst.PROPERTY_KEYS, key).build();
        } else {
            newMessage = MessageBuilder.withPayload(message).build();
        }
        // 同步发送消息
        SendResult result = rocketMQTemplate.syncSend(destination, newMessage);
        logger.info("同步发送信息结果集={}", result);
        return result;
    }

    /**
     * 同步发送批量消息
     *
     * @param list 消息集合
     * @return SendResult
     */
    public SendResult syncBatchSend(List<DemoMessage> list) {
        if (CollectionUtils.isEmpty(list)) {
            throw new RuntimeException("消息集合-list不能为空");
        }

        List<Message> messages = Lists.newArrayList();
        //每个集合的元素必须是 Spring Messaging 定义的 Message 消息
        list.forEach(a -> {
            //构建 Spring Messaging 定义的 Message 消息
            messages.add(MessageBuilder.withPayload(a).build());
        });
        // 同步发送消息   并且指定标签    destination formats: `topicName:tags`
        String destination = RocketMqBasicConstant.MY_TOPIC2 + ":" + RocketMqBasicConstant.MY_BATHC_TAG;
        SendResult result = rocketMQTemplate.syncSend(destination, messages, 30 * 1000L);
        logger.info("同步批量发送消息结果集={}", result);
        return result;
    }


    /**
     * 异步发送消息
     *
     * @param message  消息
     * @param callback 消费回调
     */
    public void asyncSend(DemoMessage message, SendCallback callback) {
        // 异步发送消息
        rocketMQTemplate.asyncSend(RocketMqBasicConstant.MY_TOPIC, message, callback);
    }


    /**
     * oneway 发送消息
     * 例如日志收集类应用，此类应用可以采用oneway形式调用，oneway形式只发送请求不等待应答，
     * 而发送请求在客户端实现层面仅仅是一个操作系统系统调用的开销，即将数据写入客户端的socket缓冲区，此过程耗时通常在微秒级。
     *
     * @param message 消息
     */
    public void oneWaySend(DemoMessage message) {
        // 异步发送消息
        rocketMQTemplate.sendOneWay(RocketMqBasicConstant.MY_TOPIC, message);
    }

    /**
     * 延时同步发送
     *
     * @param message    消息
     * @param delayLevel 延时级别
     * @return SendResult
     */
    public SendResult delaySyncSend(DemoMessage message, int delayLevel) {
        Message<DemoMessage> messageMessage = MessageBuilder.withPayload(message).build();
        String destination = RocketMqBasicConstant.MY_TOPIC + ":" + RocketMqBasicConstant.MY_DELAY_TAG;
        // 同步发送延时消息
        return rocketMQTemplate.syncSend(destination, messageMessage, 30 * 1000, delayLevel);
    }

    /**
     * 延时异步发送
     *
     * @param message    消息
     * @param delayLevel 延时级别
     * @param callback   消息回调
     */
    public void delayAsyncSend(DemoMessage message, int delayLevel, SendCallback callback) {
        Message<DemoMessage> messageMessage = MessageBuilder.withPayload(message).build();
        String destination = RocketMqBasicConstant.MY_TOPIC + ":" + RocketMqBasicConstant.MY_DELAY_TAG;
        // 异步发送延时消息a
        rocketMQTemplate.asyncSend(destination, messageMessage, callback, 30 * 1000, delayLevel);

    }


}
