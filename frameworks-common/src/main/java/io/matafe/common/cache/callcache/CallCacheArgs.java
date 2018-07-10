package io.matafe.common.cache.callcache;

import java.util.Arrays;

/**
 * Represents the arguments from the call cache.
 * 
 * @author matafe@gmail.com
 */
public class CallCacheArgs {

    private final int hashCode;

    private final Object[] args;

    public CallCacheArgs(Object[] args) {
	this.args = args;
	this.hashCode = Arrays.hashCode(args);
    }

    public Object[] getArgs() {
	return this.args;
    }

    @Override
    public boolean equals(Object o) {
	if (o instanceof CallCacheArgs) {
	    CallCacheArgs other = (CallCacheArgs) o;
	    return Arrays.equals(getArgs(), other.getArgs());
	}
	return false;
    }

    @Override
    public int hashCode() {
	return this.hashCode;
    }

    @Override
    public String toString() {
	return "CallCacheArgs [hashCode=" + hashCode + ", args=" + Arrays.toString(args) + "]";
    }

}
