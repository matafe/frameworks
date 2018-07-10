package io.matafe.common.cache.callcache;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit Test for <code>CallCacheProxy</code>.
 * 
 * @author matafe@gmail.com
 */
public class CallCacheProxyTest {

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

class ServiceFactory {
    static Service getCachedService() {
	return (Service) CallCacheProxy.newInstance(new ServiceImpl());
    }

    static Service getService() {
	return new ServiceImpl();
    }

}
