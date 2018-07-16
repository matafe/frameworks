package io.matafe.frameworks.common.cache.callcache;

import java.util.concurrent.Callable;

/**
 * Method Invoker
 * 
 * @author matafe@gmail.com
 */
public interface IMethodInvoker extends Callable<Object> {

    /**
     * Get the method name.
     * 
     * @return The method name.
     */
    String getMethodName();

    /**
     * Get the arguments.
     * 
     * @return The arguments.
     */
    Object[] getArgs();
}
