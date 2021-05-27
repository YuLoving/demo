package cn.nj.demo2.elasticsearch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author ZTY
 * @classname myEsUser
 * @description 文档实体类
 * @date 2020/12/1018:22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
//@Component
public class MyEsUser {
    private String id;
    private String name;
    private Integer age;
}
