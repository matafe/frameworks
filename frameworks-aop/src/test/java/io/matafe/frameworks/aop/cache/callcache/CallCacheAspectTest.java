package io.matafe.frameworks.aop.cache.callcache;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Before;
import org.junit.Test;

import io.matafe.frameworks.aop.cache.callcache.CallCachable;

/**
 * <p>
 * Unit Test for <code>CallCacheAspectTest</code>.
 * <p>
 * The -javaagent:"/path/to/aspectjweaver-version.jar" should be in system env.
 * 
 * @author matafe@gmail.com
 */
public class CallCacheAspectTest {

    private CallMonitor monitor;

    @Before
    public void before() {
	this.monitor = new CallMonitor();
    }

    @Test
    public void testCall() {
	Service service = ServiceFactory.getService();

	int calls = 10;

	for (int i = 0; i < calls; i++) {
	    service.process("A", monitor);
	}

	assertThat(monitor.get(), is(calls));

	service.process("B", monitor);

	assertThat(monitor.get(), is(calls + 1));
    }

    @Test
    public void testCachedCall() {

	Service service = ServiceFactory.getCachedService();

	int calls = 10;

	for (int i = 0; i < calls; i++) {
	    service.process("A", monitor);
	}

	assertThat(monitor.get(), is(1));

	service.process("B", monitor);

	assertThat(monitor.get(), is(2));
    }

}

class CallMonitor {
    AtomicInteger i = new AtomicInteger();

    public int incrementAndGet() {
	return i.incrementAndGet();
    }

    public int get() {
	return i.get();
    }
}

interface Service {
    String process(String str, CallMonitor monitor);
}

class ServiceImpl implements Service {

    public String process(String str, CallMonitor monitor) {
	monitor.incrementAndGet();
	try {
	    Thread.sleep(100);
	} catch (InterruptedException e) {
	    throw new RuntimeException(e);
	}
	return str + new Date();
    }
}

class CachedServiceImpl extends ServiceImpl {

    @CallCachable
    public String process(String str, CallMonitor monitor) {
	return super.process(str, monitor);
    }
}

class ServiceFactory {
    static Service getCachedService() {
	return new CachedServiceImpl();
    }

    static Service getService() {
	return new ServiceImpl();
    }

}
