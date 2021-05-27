package cn.nj.demo2.cache.caffeine;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @author ：zty
 * @date ：Created in 2021/1/20 16:33
 * @description ：caffeine初始化配置
 */
@Configuration
public class caffeineConfig {

    @Bean
    public Cache<String,Object> caffeineCache(){

        return Caffeine.newBuilder()
                //设置最后一次写入或访问后经过固定时间过期
                .expireAfterWrite(60, TimeUnit.SECONDS)
                //初始化缓存空间大小
                .initialCapacity(100)
                //缓存最大条数
                .maximumSize(1000)
                .build();

    }
}
