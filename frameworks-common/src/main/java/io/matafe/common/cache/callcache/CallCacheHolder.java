package io.matafe.common.cache.callcache;

/**
 * Represents the object that hold the cache during the thread execution.
 * 
 * @author matafe@gmail.com
 */
public class CallCacheHolder {

    private static final ThreadLocal<CallCache> THREAD_LOCAL_CACHE = new ThreadLocal<>();

    public static CallCache getCallCache() {
	return THREAD_LOCAL_CACHE.get();
    }

    public static CallCache createCallCache() {
	THREAD_LOCAL_CACHE.set(new CallCache());
	return getCallCache();
    }
}
