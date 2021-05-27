package cn.nj.demo2.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

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
@TableName("oper_log")
public class OperLogBo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id")
    private Long id;


    private String operModul;


    private String operType;


    private String operDesc;


    private String operRequest;


    private String operResponse;


    private Long operUserId;


    private String operUserName;


    private String operMethod;


    private String operUri;


    private String operIp;


    private LocalDateTime operCreateTime;

    private String operVersion;


}
