package io.matafe.frameworks.common.cache.callcache;

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

    public static CallCache createCallCache(final CallCacheConfig cacheConfig) {
	THREAD_LOCAL_CACHE.set(new CallCache(cacheConfig));
	return getCallCache();
    }
    
    public static void removeCallCache() {
	THREAD_LOCAL_CACHE.remove();
    }

}
