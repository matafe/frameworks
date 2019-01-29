package io.matafe.frameworks.common.cache.callcache;

/**
 * Represents the the call cache config.
 * 
 * @author matafe@gmail.com
 */
public class CallCacheConfig {

    private final long timeToLiveMillis;

    public CallCacheConfig() {
	this(-1);
    }

    /**
     * @param timeToLiveMillis
     *            the constant amount of time (in milliseconds) an entry is
     *            available before it expires. A negative value results in entries
     *            that NEVER expire. A zero value results in entries that ALWAYS
     *            expire.
     */
    public CallCacheConfig(final long timeToLiveMillis) {
	this.timeToLiveMillis = timeToLiveMillis;
    }

    public long getTimeToLiveMillis() {
	return timeToLiveMillis;
    }

    @Override
    public String toString() {
	return "CacheConfig [timeToLiveMillis=" + timeToLiveMillis + "]";
    }

}
