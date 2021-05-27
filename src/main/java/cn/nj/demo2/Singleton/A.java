package cn.nj.demo2.Singleton;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author ZTY
 * @classname A
 * @description 测试循环依赖需要的测试Bean类
 * @date 2020/11/2517:17
 */
@Component
public class A {

    @Autowired
    private B b;
}
