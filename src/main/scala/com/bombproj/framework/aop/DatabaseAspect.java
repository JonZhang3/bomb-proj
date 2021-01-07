package com.bombproj.framework.aop;

import com.queryflow.accessor.A;
import com.queryflow.accessor.Accessor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
@Aspect
public class DatabaseAspect {

    @Pointcut("execution(public * com.bombproj..*Service.*(..))")
    private void pointcut() {

    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Class<?> targetClass = joinPoint.getTarget().getClass();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method targetMethod = targetClass.getMethod(signature.getName(), signature.getParameterTypes());
        DataSource dataSource = targetMethod.getAnnotation(DataSource.class);
        Transaction transaction = targetMethod.getAnnotation(Transaction.class);
        String dataSourceTag = dataSource == null ? "" : dataSource.value();
        Accessor accessor = Utils.isNotEmpty(dataSourceTag) ? A.tag(dataSourceTag) : A.tag("");
        if(transaction != null) {
            accessor.openTransaction();
        }
        try {
            Object result = joinPoint.proceed(joinPoint.getArgs());
            if(transaction != null) {
                accessor.commit(false);
            }
            return result;
        } catch (Throwable t) {
            if(transaction != null) {
                accessor.rollback(false);
            }
            throw t;
        } finally {
            accessor.close();
        }
    }

}
