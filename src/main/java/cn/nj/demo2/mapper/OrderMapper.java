package cn.nj.demo2.mapper;

import cn.nj.demo2.pojo.MyOrderBO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ：zty
 * @date ：Created in 2020/12/23 14:38
 * @description ：
 */
@Mapper
@Repository
public interface OrderMapper {

    int batchAdd(List<MyOrderBO> list);

    List<MyOrderBO> search(MyOrderBO orderPO);


}
