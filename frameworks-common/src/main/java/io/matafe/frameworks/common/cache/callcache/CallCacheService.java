package io.matafe.frameworks.common.cache.callcache;

import java.lang.reflect.InvocationTargetException;

/**
 * Call Cache Service
 * 
 * @author matafe@gmail.com
 */
public class CallCacheService {

    /**
     * The private default constructor.
     */
    private CallCacheService() {
    }

    /**
     * Helper class for Singleton
     */
    private static class CallCacheServiceHelper {

	/**
	 * The one and only instance of the main class.
	 */
	private static final CallCacheService INSTANCE = new CallCacheService();
    }

    /**
     * Returns the singleton of this class.
     * 
     * @return the singleton.
     */
    // Bill Pugh Pattern
    public static CallCacheService getInstance() {
	return CallCacheServiceHelper.INSTANCE;
    }

    /**
     * Returns the cached object after the execution
     * 
     * @param invoker The invoker
     * 
     * @return The cached object.
     */
    public Object cache(final IMethodInvoker invoker) throws Throwable {
	Object result = null;
	try {
	    CallCache cache = CallCacheHolder.getCallCache();
	    if (cache == null) {
		cache = CallCacheHolder.createCallCache();
	    }
	    CallCacheArgs cArgs = new CallCacheArgs(invoker.getArgs());
	    result = cache.get(invoker.getMethodName(), cArgs);
	    if (result != null) {
		return result;
	    } else {
		result = invoker.call();
		cache.put(invoker.getMethodName(), cArgs, result);
	    }
	} catch (InvocationTargetException e) {
	    throw e.getTargetException();
	} catch (Exception e) {
	    throw new RuntimeException("Unexpected invocation exception: ", e);
	}
	return result;
    }

}
