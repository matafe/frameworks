package io.matafe.frameworks.aop.cache.callcache;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import io.matafe.frameworks.common.cache.callcache.CallCacheService;

/**
 * Define the Aspect that uses the @<code>CallCachable</code> annotation to do
 * the caching.
 * 
 * @author matafe@gmail.com
 */
@Aspect
public class CallCacheAspect {

    @Around("@annotation(CallCachable) && execution(* *(..))")
    public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
	return CallCacheService.getInstance().cache(new AopMethodInvoker(pjp));
    }

}