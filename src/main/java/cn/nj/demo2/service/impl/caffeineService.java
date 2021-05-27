package cn.nj.demo2.service.impl;

import cn.nj.demo2.pojo.testDTO;
import com.alibaba.fastjson.JSON;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author ：zty
 * @date ：Created in 2021/1/20 16:44
 * @description ：
 */
@Service
@Slf4j
public class caffeineService {

    @Autowired
    private Cache<String, Object> caffeine;


    /**
     * 模拟数据库存储数据
     */
    private Map<Long, testDTO> userInfoMap = new HashMap<>();


    public void add(testDTO testDTO) {
        log.info("===========新增==========");
        //放入模拟数据库中
        userInfoMap.put(testDTO.getId(), testDTO);
        //放入缓存
        caffeine.put(String.valueOf(testDTO.getId()), testDTO);
        log.info("userInfoMap={}", JSON.toJSONString(userInfoMap));
        testDTO aa = (cn.nj.demo2.pojo.testDTO) caffeine.asMap().get(String.valueOf(testDTO.getId()));
        log.info("caffeine={}", JSON.toJSONString(aa));
    }

    public testDTO getByid(Long id) {
        log.info("===========通过ID查询==========");
        //1.查询缓存
        testDTO testDTO = (cn.nj.demo2.pojo.testDTO) caffeine.asMap().get(String.valueOf(id));
        if (null != testDTO) {
            log.info("===========缓存存在==========");
            return testDTO;
        }
        log.info("===========缓存不存在,从模拟数据库中捞取==========");
        cn.nj.demo2.pojo.testDTO testDTO1 = userInfoMap.get(id);
        log.info("testDTO1={}", JSON.toJSONString(testDTO1));
        if (null != testDTO1) {
            log.info("===========重新加入缓存==========");
            caffeine.put(String.valueOf(id), testDTO1);
        }
        return testDTO1;
    }



    public void detele(Long id) {
        log.info("===========删除==========");
        userInfoMap.remove(id);
        caffeine.asMap().remove(String.valueOf(id));
    }


}
