package io.matafe.frameworks.common.cache.callcache;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents the the call cache.
 * 
 * @author matafe@gmail.com
 */
public class CallCache {

    /**
     * The cache
     */
    private final Map<CallCacheKey, CallCacheValue> cache;

    /**
     * Contructor
     */
    public CallCache() {
	this.cache = new HashMap<>();
    }

    /**
     * Put a method and its result to a cache.
     * 
     * @param key
     *            The cache key.
     * @param args
     *            The method arguments.
     * @param result
     *            The result of the method call.
     * 
     * @return The result of the method call.
     */
    public Object put(final CallCacheKey key, final Object result) {
	CallCacheValue cv = this.cache.get(key);
	if (cv == null) {
	    cv = new CallCacheValue(result);
	    this.cache.put(key, cv);
	}
	return result;
    }

    /**
     * Get the result of a method call from the cache.
     * 
     * @param cacheKey
     *            The cache key.
     * @param args
     *            The arguments.
     * 
     * @return The result of the method call from the cache.
     */
    public Object get(final CallCacheKey cacheKey) {
	final CallCacheValue cv = this.cache.get(cacheKey);
	return cv == null ? null : cv.getResult();
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
