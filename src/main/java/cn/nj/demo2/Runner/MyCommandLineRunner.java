package cn.nj.demo2.Runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author ZTY
 * @classname MyBean
 * @description    Spring Boot启动的时候运行一些特定的代码，你可以实现接口 ApplicationRunner或者 CommandLineRunner，这两个接口实现方式一样，它们都只提供了一个run方法。
 * @date 2020/11/2618:29
 */
@Component
public class MyCommandLineRunner implements CommandLineRunner {


    @Override
    public void run(String... args) throws Exception {
        System.out.println("==============测试CommandLineRunner========" );
    }
}
