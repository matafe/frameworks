package io.matafe.frameworks.common.cache.callcache;

/**
 * Represents the arguments from the call cache.
 * 
 * @author matafe@gmail.com
 */
public class CallCacheKey {

    private final String className;

    private final String methodName;

    private final CallCacheArgs args;

    public CallCacheKey(final String className, final String methodName, final CallCacheArgs args) {
	super();
	this.className = className;
	this.methodName = methodName;
	this.args = args;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((args == null) ? 0 : args.hashCode());
	result = prime * result + ((className == null) ? 0 : className.hashCode());
	result = prime * result + ((methodName == null) ? 0 : methodName.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	CallCacheKey other = (CallCacheKey) obj;
	if (className == null) {
	    if (other.className != null)
		return false;
	} else if (!className.equals(other.className))
	    return false;
	if (methodName == null) {
	    if (other.methodName != null)
		return false;
	} else if (!methodName.equals(other.methodName))
	    return false;
	if (args == null) {
	    if (other.args != null)
		return false;
	} else if (!args.equals(other.args))
	    return false;

	return true;
    }

    @Override
    public String toString() {
	return "CallCacheKey [className=" + className + ", methodName=" + methodName + ", args=" + args + "]";
    }

}
