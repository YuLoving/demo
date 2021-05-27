package cn.nj.demo2.pojo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author ：zty
 * @date ：Created in 2020/12/24 16:03
 * @description ：
 */
@Data
public class MyOrderBO {

    private Long id;
    private Integer status;
    private String name;
    private Integer type;
    private LocalDateTime gmtCreate;


}
