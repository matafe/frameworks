package io.matafe.aop.cache.callcache;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Define the method to be cacheble.
 * 
 * @author matafe@gmail.com
 */
@Retention(RUNTIME)
@Target(METHOD)
public @interface CallCachable {
}
