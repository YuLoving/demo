package cn.nj.demo2.controller;

import cn.nj.demo2.service.impl.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：zty
 * @date ：Created in 2020/12/24 18:31
 * @description ：
 */
@RestController
@RequestMapping("/order/")
public class orderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("batchAdd")
    public void test() throws InterruptedException {
        orderService.test();
    }

    @GetMapping("threadbatchAdd")
    public void threadtest() throws InterruptedException {
        orderService.Threadtest();
    }

    @GetMapping("search")
    public void search() throws InterruptedException {
        orderService.search();
    }
}
