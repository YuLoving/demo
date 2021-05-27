package cn.nj.demo2.aop;


import cn.hutool.core.net.Ipv4Util;
import cn.hutool.core.util.ObjectUtil;
import cn.nj.demo2.annotation.OperLog;
import cn.nj.demo2.mapper.OperExcMapper;
import cn.nj.demo2.mapper.OperLogMapper;
import cn.nj.demo2.pojo.OperExcBo;
import cn.nj.demo2.pojo.OperLogBo;
import cn.nj.demo2.util.IPUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ZTY
 * @classname OperLogAspect
 * @description 切面处理类，操作日志异常日志记录处理
 * @date 2020/10/2012:12
 */
@Aspect
@Order(2)
@Component
public class OperLogAspect {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${test.version}")
    private String version;


    @Autowired
    private OperLogMapper operLogMapper;

    @Autowired
    private OperExcMapper operExcMapper;


    //设置操作日志切入点 记录操作日志 在注解的位置切入代码
    @Pointcut("@annotation(cn.nj.demo2.annotation.OperLog)")
    public void operLogPoinCut() {
    }


    /**
     * 设置操作异常切入点记录异常日志 扫描所有controller包下操作
     */
    @Pointcut("execution(* cn.nj.demo2.controller..*.*(..))")
    public void operExceptionLogPoinCut() {
    }


    /**
     * 正常返回通知，拦截用户操作日志，连接点正常执行完成后执行， 如果连接点抛出异常，则不会执行
     *
     * @param joinpoint 切入点
     * @param keys      返回结果
     */
    @AfterReturning(value = "operLogPoinCut()", returning = "keys")
    public void saveOperLog(JoinPoint joinpoint, Object keys) {
        //获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // 从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);

        OperLogBo operLogBo = new OperLogBo();
        try {
            // 从切面织入点处通过反射机制获取织入点处的方法
            MethodSignature signature = (MethodSignature) joinpoint.getSignature();
            //获取切入点所在的方法
            Method method = signature.getMethod();
            // 获取操作
            OperLog log = method.getAnnotation(OperLog.class);
            if (null != log) {
                operLogBo.setOperModul(log.operModul());
                operLogBo.setOperType(log.operType());
                operLogBo.setOperDesc(log.operDesc());
            }
            //获取请求的类名
            String className = joinpoint.getTarget().getClass().getName();
            //获取请求的方法名
            String methodName = method.getName();
            operLogBo.setOperMethod(className + "." + methodName);
            //请求的参数
            Object[] args = joinpoint.getArgs();
            logger.error("入参列表={}", JSON.toJSONString(args));
            //去除首位的[]
            String strip = StringUtils.strip(JSON.toJSONString(args), "[]");
            logger.error("入参列表数组转对象={}", strip);
            operLogBo.setOperRequest(strip);
            if(!"null".equals(keys)){
                operLogBo.setOperResponse(JSONObject.parseObject(keys.toString()).toJSONString());
            }
            operLogBo.setOperUserId(Long.valueOf(request.getHeader("user_id")));
            operLogBo.setOperUserName(request.getHeader("user_name"));
            operLogBo.setOperUri(request.getRequestURI());
            operLogBo.setOperIp(IPUtil.getIp(request));
            operLogBo.setOperCreateTime(LocalDateTime.now());
            operLogBo.setOperVersion(version);
            operLogMapper.insert(operLogBo);
        } catch (Exception e) {
            logger.info("=================日志操作入库失败==================");
            e.printStackTrace();
        }

    }

    /**
     * 异常返回通知，用于拦截异常日志信息 连接点抛出异常后执行
     *
     * @param joinPoint 切入点
     * @param e         异常信息
     */
    @AfterThrowing(pointcut = "operExceptionLogPoinCut()", throwing = "e")
    public void saveExceptionLog(JoinPoint joinPoint, Throwable e) {
        //获取requestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        //获取httpServletRequest
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);

        OperExcBo operExcBo = new OperExcBo();
        try {
            //从切面织入点处通过反射机制获取织入点处的方法
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            //获取方法
            Method method = signature.getMethod();
            //获取请求的类名
            String className = joinPoint.getTarget().getClass().getName();
            //获取请求的方法名
            String methodName = method.getName();
            //请求的参数
            Object[] args = joinPoint.getArgs();
            String strip = StringUtils.strip(JSON.toJSONString(args), "[]");
            operExcBo.setExcRequset(strip);
            operExcBo.setExcName(e.getClass().getName());
            String excMessage = stackTraceToString(e.getClass().getName(), e.getMessage(), e.getStackTrace());
            operExcBo.setExcMessage(excMessage);
            operExcBo.setOperUserId(Long.valueOf(request.getHeader("user_id")));
            operExcBo.setOperUserName(request.getHeader("user_name"));
            operExcBo.setOperMethod(className + "." + methodName);
            operExcBo.setOperUri(request.getRequestURI());
            operExcBo.setOperIp(IPUtil.getIp(request));
            operExcBo.setOperVersion(version);
            operExcBo.setOperCreateTime(LocalDateTime.now());
            operExcMapper.insert(operExcBo);
        } catch (Exception ex) {
            logger.info("=================异常信息操作入库失败==================");
            e.printStackTrace();
        }


    }

    /**
     * 193      * 转换异常信息为字符串
     * 194      *
     * 195      * @param exceptionName    异常名称
     * 196      * @param exceptionMessage 异常信息
     * 197      * @param elements         堆栈信息
     * 198
     */
    public String stackTraceToString(String exceptionName, String exceptionMessage, StackTraceElement[] elements) {
        StringBuffer strbuff = new StringBuffer();
        for (StackTraceElement stet : elements) {
            strbuff.append(stet + "\n");
        }
        String message = exceptionName + ":" + exceptionMessage + "\n\t" + strbuff.toString();
        return message;
    }


}
