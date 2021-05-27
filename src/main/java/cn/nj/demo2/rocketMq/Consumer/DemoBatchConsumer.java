package cn.nj.demo2.rocketMq.Consumer;

import cn.nj.demo2.rocketMq.Contant.RocketMqBasicConstant;
import cn.nj.demo2.rocketMq.Message.DemoMessage;
import com.alibaba.fastjson.JSON;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ZTY
 * @classname DemoConsumer
 * @description 消费者
 * 批量消费的接口  指定固定标签进行消费
 * @date 2020/11/3015:39
 */
/*@Component
@RocketMQMessageListener(
        topic = RocketMqBasicConstant.MY_TOPIC2,
        consumerGroup = RocketMqBasicConstant.MY_CONSUMER_GROUP+"LIST",
        selectorType = SelectorType.TAG,
        selectorExpression = RocketMqBasicConstant.MY_BATHC_TAG
)*/
public class DemoBatchConsumer implements RocketMQListener<DemoMessage> {

    private final Logger logger = LoggerFactory.getLogger(getClass());


    @Override
    public void onMessage(DemoMessage message) {
        logger.info("线程编号:{},消息内容:{}",Thread.currentThread().getId(), message);
    }
}
