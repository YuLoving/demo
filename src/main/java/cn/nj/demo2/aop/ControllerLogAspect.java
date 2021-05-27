package cn.nj.demo2.aop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

/**
 * @author ZTY
 * @classname ControllerLogAspect
 * @description 打印controller接口出参入参
 * @date 2020/10/2117:14
 */
@Aspect
@Order(1)
public class ControllerLogAspect {
    private  final Logger logger= LoggerFactory.getLogger(getClass());

    @Pointcut("execution(* cn.nj.demo2.controller..*.*(..))")
    public void request(){

    }
    @Around(value = "request()")
    public  void doAroundLogPrint(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        long start = System.currentTimeMillis();
        Object proceed = proceedingJoinPoint.proceed();
        //获取ServletRequestAttributes
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //获取request
        HttpServletRequest request = requestAttributes.getRequest();
        //获取头部信息
        Enumeration<String> headerNames = request.getHeaderNames();
        List<String> hears = Lists.newArrayList();
        while (headerNames.hasMoreElements()){
            String nextElement = headerNames.nextElement();
            hears.add(nextElement+"="+request.getHeader(nextElement));
        }
        //获取入参
        Object[] args = proceedingJoinPoint.getArgs();
        String strip = StringUtils.strip(JSON.toJSONString(args), "[]");
        //获取结果集





       // logger.info();










    }

}
