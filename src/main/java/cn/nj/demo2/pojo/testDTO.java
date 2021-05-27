package cn.nj.demo2.pojo;

import lombok.Data;
import lombok.ToString;

/**
 * @author ZTY
 * @classname testDTO
 * @description TODO
 * @date 2020/10/2017:46
 */

@Data
@ToString
public class testDTO {
    private  Long id;
    private String name;
    private String sex;
    private Integer age;
}
