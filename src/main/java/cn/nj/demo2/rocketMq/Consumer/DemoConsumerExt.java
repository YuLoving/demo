package cn.nj.demo2.rocketMq.Consumer;

import cn.nj.demo2.rocketMq.Contant.RocketMqBasicConstant;
import cn.nj.demo2.rocketMq.Message.DemoMessage;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author ZTY
 * @classname DemoConsumer
 * @description 消费者
 * 一般情况下，我们建议一个消费者分组，仅消费一个 Topic 。这样做会有两个好处：
 * 每个消费者分组职责单一，只消费一个 Topic 。
 * 每个消费者分组是独占一个线程池，这样能够保证多个 Topic 隔离在不同线程池，保证隔离性，从而避免一个 Topic 消费很慢，影响到另外的 Topic 的消费。
 * @date 2020/11/3015:39
 */
/*@Component
@RocketMQMessageListener(
        topic = RocketMqBasicConstant.MY_TOPIC,
        consumerGroup = RocketMqBasicConstant.MY_CONSUMER_GROUP+"EXT",
        selectorType = SelectorType.TAG,
        selectorExpression = RocketMqBasicConstant.MY_TAG
)*/
public class DemoConsumerExt implements RocketMQListener<MessageExt> {
    /**
     * 在 T 泛型里，设置消费的消息对应的类不是 Demo01Message 类，而是 RocketMQ 内置的 MessageExt 类。
     * 通过 MessageExt 类，我们可以获取到消费的消息的更多信息，例如说消息的所属队列、创建时间等等属性，不过消息的内容(body)就需要自己去反序列化。当然，一般情况下，我们不会使用 MessageExt 类。
     */


    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void onMessage(MessageExt message) {
        logger.info("线程编号:{},消息内容:{}",Thread.currentThread().getId(),message);
    }
}