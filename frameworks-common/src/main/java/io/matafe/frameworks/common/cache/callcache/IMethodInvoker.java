package io.matafe.frameworks.common.cache.callcache;

import java.util.concurrent.Callable;

/**
 * Method Invoker
 * 
 * @author matafe@gmail.com
 */
public interface IMethodInvoker extends Callable<Object> {

    /**
     * Get the class name.
     * 
     * @return The class name.
     */
    String getClassName();
    
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
