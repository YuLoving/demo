package cn.nj.demo2.controller;


import cn.nj.demo2.annotation.OperLog;
import cn.nj.demo2.constant.OperConstant;
import cn.nj.demo2.pojo.OperLogBo;
import cn.nj.demo2.pojo.testDTO;
import cn.nj.demo2.service.OperLogService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zty
 * @since 2020-10-20
 */
@RestController
@RequestMapping("/operLogBo")
public class OperLogController {

    @Autowired
    private OperLogService logService;

    @PostMapping("/query")
    @OperLog(operModul = "日志模块",operType = OperConstant.OPER_QUERY,operDesc = "测试日志查询的列表")
    public String query(@RequestBody testDTO dto){
        OperLogBo bo = logService.getById(dto.getId());
        String jsonString = JSON.toJSONString(bo);
        return jsonString;
    }

    @PostMapping("/exc")
    @OperLog(operModul = "日志模块",operType = OperConstant.OPER_QUERY,operDesc = "测试异常")
    public int exc(@RequestBody testDTO dto){
        int a=1/0;
        return a;
    }


}

