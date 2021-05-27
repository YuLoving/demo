package cn.nj.demo2.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author zty
 * @since 2020-10-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("oper_exc")

public class OperExcBo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id")
    private Long id;


    private String excRequset;


    private String excName;


    private String excMessage;


    private Long operUserId;


    private String operUserName;


    private String operMethod;


    private String operUri;


    private String operIp;


    private String operVersion;


    private LocalDateTime operCreateTime;


}
