package io.matafe.frameworks.common.cache.callcache;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * This is an <strong>InvocationHandler</strong> for the call cache.
 * 
 * @author matafe@gmail.com
 */
public class CallCacheInvocationHandler implements InvocationHandler {

    private Object obj;

    public CallCacheInvocationHandler(Object obj) {
	this.obj = obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

	Object result = null;
	try {
	    result = CallCacheService.getInstance().cache(new ReflectionMethodInvoker(obj, method, args));
	} catch (InvocationTargetException e) {
	    throw e.getTargetException();
	} catch (Exception e) {
	    throw new RuntimeException("Unexpected invocation exception: ", e);
	}
	return result;
    }

}
