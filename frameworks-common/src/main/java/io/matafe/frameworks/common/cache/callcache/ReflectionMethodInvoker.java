package io.matafe.frameworks.common.cache.callcache;

import java.lang.reflect.Method;

/**
 * Reflection Invoker
 * 
 * @author matafe@gmail.com
 */
public class ReflectionMethodInvoker implements IMethodInvoker {

    private final Object obj;

    private final Method method;

    private final Object[] args;

    public ReflectionMethodInvoker(final Object obj, final Method method, final Object[] args) {
	this.obj = obj;
	this.method = method;
	this.args = args;
    }

    @Override
    public String getMethodName() {
	return method.getName();
    }

    @Override
    public Object[] getArgs() {
	return args;
    }

    @Override
    public Object call() throws Exception {
	return method.invoke(obj, args);
    }

    @Override
    public String getClassName() {
	return obj.getClass().getName();
    }

}
