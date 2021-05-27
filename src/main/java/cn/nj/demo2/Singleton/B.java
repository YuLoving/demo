package cn.nj.demo2.Singleton;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author ZTY
 * @classname B
 * @description  测试循环依赖需要的测试Bean类
 * @date 2020/11/2517:18
 */
@Component
public class B {

    @Autowired
    private A a;
}
