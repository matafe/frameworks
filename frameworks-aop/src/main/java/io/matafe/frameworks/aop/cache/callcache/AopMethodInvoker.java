package io.matafe.frameworks.aop.cache.callcache;

import org.aspectj.lang.ProceedingJoinPoint;

import io.matafe.frameworks.common.cache.callcache.IMethodInvoker;

/**
 * AOP Method Invoker
 * 
 * @author matafe@gmail.com
 */
public class AopMethodInvoker implements IMethodInvoker {

    private final ProceedingJoinPoint pjp;

    private final Object[] args;

    private final String methodName;

    public AopMethodInvoker(final ProceedingJoinPoint pjp) {
	this.pjp = pjp;
	this.args = pjp.getArgs();
	this.methodName = getMethodName(pjp);
    }

    @Override
    public String getMethodName() {
	return methodName;
    }

    @Override
    public Object[] getArgs() {
	return args;
    }

    @Override
    public Object call() {
	try {
	    return pjp.proceed();
	} catch (Throwable e) {
	    throw new RuntimeException("Aspect Exception", e);
	}

    }

    private String getMethodName(final ProceedingJoinPoint pjp) {
	StringBuilder sb = new StringBuilder().append(pjp.getTarget().getClass().getName()).append(".")
		.append(pjp.getSignature().getName());
	return sb.toString();
    }

}
