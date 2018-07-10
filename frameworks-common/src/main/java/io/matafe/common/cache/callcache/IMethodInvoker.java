package io.matafe.common.cache.callcache;

/**
 * Method Invoker
 * 
 * @author matafe@gmail.com
 */
public interface IMethodInvoker {

    String getMethodName();

    Object[] getArgs();

    Object invoke() throws Throwable;
}
