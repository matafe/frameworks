package io.matafe.frameworks.common.cache.callcache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Represents the the call cache.
 * 
 * @author matafe@gmail.com
 */
public class CallCache {

    /**
     * The cache
     */
    private final Map<String, Map<CallCacheArgs, Object>> cache;

    /**
     * Contructor
     */
    public CallCache() {
	this.cache = new ConcurrentHashMap<>();
    }

    /**
     * Put a method and its result to a cache.
     * 
     * @param methodName
     *            The method name.
     * @param args
     *            The method arguments.
     * @param result
     *            The result of the method call.
     * 
     * @return The result of the method call.
     */
    public Object put(String methodName, CallCacheArgs args, Object result) {
	Map<CallCacheArgs, Object> cacheArgs = this.cache.get(methodName);
	if (cacheArgs == null) {
	    cacheArgs = new HashMap<>();
	    this.cache.put(methodName, cacheArgs);
	}
	cacheArgs.put(args, result);
	return result;
    }

    /**
     * Get the result of a method call from the cache.
     * 
     * @param methodName
     *            The method name.
     * @param args
     *            The arguments.
     * 
     * @return The result of the method call from the cache.
     */
    public Object get(String methodName, CallCacheArgs args) {
	Map<CallCacheArgs, Object> cacheArgs = this.cache.get(methodName);
	return cacheArgs == null ? null : cacheArgs.get(args);
    }

    /**
     * If the cache is empty.
     * 
     * @return <code>true</code>If the cache is empty. <code>false</code> otherwise.
     */
    public boolean isEmpty() {
	return this.cache.isEmpty();
    }

    /**
     * Clear the entries of the cache.
     */
    public void clear() {
	this.cache.clear();
    }
}
