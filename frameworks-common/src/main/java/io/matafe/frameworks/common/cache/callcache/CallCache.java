package io.matafe.frameworks.common.cache.callcache;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

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

    /** map used to manage expiration times for the actual map entries. */
    private final Map<CallCacheKey, Long> expirationMap = new HashMap<>();

    private final long timeToLiveMillis;

    /**
     * Contructor
     */
    public CallCache(final CallCacheConfig cacheConfig) {
	this.timeToLiveMillis = cacheConfig.getTimeToLiveMillis();
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

	    // record expiration time of new entry
	    final long expirationTime = expirationTime(key, cv);
	    expirationMap.put(key, Long.valueOf(expirationTime));

	    this.cache.put(key, cv);
	}
	return result;
    }

    private long expirationTime(final CallCacheKey key, final CallCacheValue value) {
	if (timeToLiveMillis >= 0L) {
	    final long now = System.currentTimeMillis();
	    if (now > Long.MAX_VALUE - timeToLiveMillis) {
		// never expire
		return -1;
	    }

	    // timeToLiveMillis in the future
	    return now + timeToLiveMillis;
	}

	// never expire
	return -1L;
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
	removeIfExpired(cacheKey, now());
	final CallCacheValue cv = this.cache.get(cacheKey);
	return cv == null ? null : cv.getResult();
    }

    /**
     * Removes the entry with the given key if the entry's expiration time is less
     * than <code>now</code>. If the entry has a negative expiration time, the entry
     * is never removed.
     */
    private void removeIfExpired(final CallCacheKey key, final long now) {
	final Long expirationTimeObject = expirationMap.get(key);
	if (isExpired(now, expirationTimeObject)) {
	    this.cache.remove(key);
	}
    }

    /**
     * Determines if the given expiration time is less than <code>now</code>.
     *
     * @param now
     *            the time in milliseconds used to compare against the expiration
     *            time.
     * @param expirationTimeObject
     *            the expiration time value retrieved from {@link #expirationMap},
     *            can be null.
     * @return <code>true</code> if <code>expirationTimeObject</code> is &ge; 0 and
     *         <code>expirationTimeObject</code> &lt; <code>now</code>.
     *         <code>false</code> otherwise.
     */
    private boolean isExpired(final long now, final Long expirationTimeObject) {
	if (expirationTimeObject != null) {
	    final long expirationTime = expirationTimeObject.longValue();
	    return expirationTime >= 0 && now >= expirationTime;
	}
	return false;
    }

    /**
     * The current time in milliseconds.
     */
    private long now() {
	return System.currentTimeMillis();
    }

    /**
     * If the cache is empty.
     * 
     * @return <code>true</code>If the cache is empty. <code>false</code> otherwise.
     */
    public boolean isEmpty() {
	removeAllExpired(now());
	return this.cache.isEmpty();
    }

    /**
     * Clear the entries of the cache. All expired entries are removed from the map
     * prior to clear.
     */
    public void clear() {
	removeAllExpired(now());
	this.cache.clear();
	this.expirationMap.clear();
    }

    /**
     * All expired entries are removed from the map prior to returning the size.
     */
    public int size() {
	removeAllExpired(now());
	return this.cache.size();
    }

    /**
     * Removes all entries in the map whose expiration time is less than
     * <code>now</code>. The exceptions are entries with negative expiration times;
     * those entries are never removed.
     *
     * @see #isExpired(long, Long)
     */
    private void removeAllExpired(final long now) {
	final Iterator<Entry<CallCacheKey, Long>> iter = expirationMap.entrySet().iterator();
	while (iter.hasNext()) {
	    final Entry<CallCacheKey, Long> expirationEntry = iter.next();
	    if (isExpired(now, expirationEntry.getValue())) {
		// remove entry from collection
		this.cache.remove(expirationEntry.getKey());
		// remove entry from expiration map
		iter.remove();
	    }
	}
    }

}
