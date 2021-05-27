package cn.nj.demo2.controller;

import cn.hutool.system.UserInfo;
import cn.nj.demo2.pojo.testDTO;
import cn.nj.demo2.service.impl.caffeineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author ：zty
 * @date ：Created in 2021/1/20 16:43
 * @description ：
 */
@RestController
@RequestMapping("/caffeine/")
public class caffeineController {

    @Autowired
    private caffeineService caffeineService;


    @GetMapping("/get")
    public Object getUserInfo( Long id) {

        return caffeineService.getByid(id);
    }

    @PostMapping("/add")
    public Object createUserInfo(@RequestBody testDTO userInfo) {
        caffeineService.add(userInfo);
        return "SUCCESS";
    }


    @DeleteMapping("/detele")
    public Object deleteUserInfo( Long id) {
        caffeineService.detele(id);
        return "SUCCESS";
    }




}
