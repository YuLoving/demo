package cn.nj.demo2.rocketMq.Message;

import lombok.Data;
import org.apache.poi.ss.formula.functions.T;

/**
 * @author ZTY
 * @classname DemoMessage
 * @description  消息体
 * @date 2020/11/3012:10
 */
@Data
public class DemoMessage {

    /**
     * 编号
     */
    private Integer id;

    private String body;

    private String tag;

    private String topic;

}
