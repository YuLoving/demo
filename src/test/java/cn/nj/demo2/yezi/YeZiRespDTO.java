package cn.nj.demo2.yezi;

import lombok.Data;
import org.apache.poi.ss.formula.functions.T;

/**
 * @author ：zty
 * @date ：Created in 2021/2/22 14:11
 * @description ：
 */
@Data
public class YeZiRespDTO<T> {

    private String code;

    private T data;

    private String message;

    private Boolean success;

    private String error;

}
