package io.matafe.frameworks.common.cache.callcache;

import java.lang.reflect.Proxy;

/**
 * This is a <strong>Proxy</strong> design pattern for the Cache.
 * 
 * @author matafe@gmail.com
 */
public final class CallCacheProxyFactory {

    private CallCacheProxyFactory() {
    }

    /**
     * Get a proxied instance of the object.
     * 
     * @param obj
     *            The object to proxy.
     * @param interfaceClass
     *            The object interface.
     * 
     * @return The proxied interface instance.
     */
    @SuppressWarnings("unchecked")
    public static <I, T extends I> I newInstance(T obj, Class<I> interfaceClass) {
	return (I) Proxy.newProxyInstance(obj.getClass().getClassLoader(), new Class[] { interfaceClass },
		new CallCacheInvocationHandler(obj));
    }

}
