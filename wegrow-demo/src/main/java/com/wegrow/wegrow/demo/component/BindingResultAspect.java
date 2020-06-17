package com.wegrow.wegrow.demo.component;

import com.alibaba.fastjson.JSON;
import com.wegrow.wegrow.common.api.CommonResult;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.List;

/**
 * HibernateValidator错误结果处理切面
 * Created by macro on 2018/4/26.
 */
@Aspect
@Component
@Order(2)
public class BindingResultAspect {
    @Pointcut("execution(public * com.wegrow.wegrow.demo.controller.*.*(..))")
    public void BindingResult() {
    }

    @Around("BindingResult()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof BindingResult) {
                BindingResult result = (BindingResult) arg;
                if (result.hasErrors()) {
                    List<FieldError> fieldErrors = result.getFieldErrors();
                    HashMap<String, String> errorInfo = new HashMap<>();
                    fieldErrors.forEach(fieldError -> {
                        //日志打印不符合校验的字段名和错误提示
                        errorInfo.put(fieldError.getField(), fieldError.getDefaultMessage());
                    });
                    return CommonResult.validateFailed(JSON.toJSONString(errorInfo));
                }
            }
        }
        return joinPoint.proceed();
    }
}
