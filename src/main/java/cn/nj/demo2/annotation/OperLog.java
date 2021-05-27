package cn.nj.demo2.annotation;

import java.lang.annotation.*;

/**
 * @author ZTY
 * @classname OperLog
 * @description 日志记录自定义注解
 * @date 2020/10/2012:01
 */
@Target(ElementType.METHOD)  //注解放置的目标位置,METHOD是可注解在方法级别上
@Retention(RetentionPolicy.RUNTIME)   //注解在哪个阶段执行
@Documented   //注解是否将包含在JavaDoc中
public @interface OperLog {

    //操作模块
    String operModul() default "";
    //操作类型
    String operType() default "";
    //操作说明
    String operDesc() default  "";
}
