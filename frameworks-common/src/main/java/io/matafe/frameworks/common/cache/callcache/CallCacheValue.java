package io.matafe.frameworks.common.cache.callcache;

/**
 * Represents the arguments from the call cache.
 * 
 * @author matafe@gmail.com
 */
public class CallCacheValue {

    private final Object result;

    public CallCacheValue(final Object result) {
	this.result = result;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int res = 1;
	res = prime * res + ((result == null) ? 0 : result.hashCode());
	return res;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	CallCacheValue other = (CallCacheValue) obj;
	if (result == null) {
	    if (other.result != null)
		return false;
	} else if (!result.equals(other.result))
	    return false;

	return true;
    }

    @Override
    public String toString() {
	return result != null ? result.toString() : "null";
    }

    public Object getResult() {
        return result;
    }

}
