package cn.nj.demo2.rocketMq.Producer;

import cn.nj.demo2.rocketMq.Message.DemoMessage;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * @author ZTY
 * @classname TransactionProducer
 * @description TODO
 * @date 2020/12/918:00
 */
@Component
public class TransactionProducer {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;


    public void sendMessageInTransaction(DemoMessage message) throws Exception{

        try {
            // Build a SpringMessage for sending in transaction
            Message<DemoMessage> msg = MessageBuilder.withPayload(message).build();
            // In sendMessageInTransaction(), the first parameter transaction name ("test")
            // must be same with the @RocketMQTransactionListener's member field 'transName'
            rocketMQTemplate.sendMessageInTransaction("test-topic", msg, null);
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }

    }

    @RocketMQTransactionListener
    public class  TransactionListenerImpl implements RocketMQLocalTransactionListener{

        @Override
        public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
            // ... local transaction process, return bollback, commit or unknown
            return RocketMQLocalTransactionState.UNKNOWN;
        }

        @Override
        public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
            // ... check transaction status and return bollback, commit or unknown
            return RocketMQLocalTransactionState.COMMIT;
        }
    }



}
