/*
package cn.nj.demo2.rocketMq.Consumer;

import cn.nj.demo2.rocketMq.Contant.RocketMqBasicConstant;
import cn.nj.demo2.rocketMq.Message.DemoMessage;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

*/
/**
 * @author ZTY
 * @classname DemoConsumer
 * @description 消费者
 * 一般情况下，我们建议一个消费者分组，仅消费一个 Topic 。这样做会有两个好处：
 * 每个消费者分组职责单一，只消费一个 Topic 。
 * 每个消费者分组是独占一个线程池，这样能够保证多个 Topic 隔离在不同线程池，保证隔离性，从而避免一个 Topic 消费很慢，影响到另外的 Topic 的消费。
 * @date 2020/11/3015:39
 *//*

@Component
@RocketMQMessageListener(
        topic = RocketMqBasicConstant.MY_TOPIC,
        consumerGroup = RocketMqBasicConstant.MY_CONSUMER_GROUP + "RETRY",
        selectorType = SelectorType.TAG,
        selectorExpression = RocketMqBasicConstant.MY_RETRY_TAG
)
public class DemoConsumerRetry implements RocketMQListener<DemoMessage> {

    private final Logger logger = LoggerFactory.getLogger(getClass());


    @Override
    public void onMessage(DemoMessage message) {
        logger.info("线程编号:{},消息内容:{}", Thread.currentThread().getId(), message);
        // <X> 注意，此处抛出一个 RuntimeException 异常，模拟消费失败
        //throw new RuntimeException("我就是故意抛出一个异常");
        //https://github.com/apache/rocketmq-spring/issues/322  关于rocketmq-spring 消费端消费重试的疑点
    }
}
*/
