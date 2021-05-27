package cn.nj.demo2.mapper;

import cn.nj.demo2.pojo.OperLogBo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zty
 * @since 2020-10-20
 */
@Mapper
@Repository
public interface OperLogMapper extends BaseMapper<OperLogBo> {

}
