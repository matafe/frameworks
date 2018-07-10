package io.matafe.aop.cache.callcache;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import io.matafe.common.cache.callcache.CallCacheService;

@Aspect
public class CallCacheAspect {

    @Around("@annotation(CallCachable) && execution(* *(..))")
    public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
	return CallCacheService.getInstance().cache(new AopMethodInvoker(pjp));
    }

}