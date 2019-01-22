package io.matafe.frameworks.common.cache.callcache;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit Test for <code>CallCacheProxyFactory</code>.
 * 
 * @author matafe@gmail.com
 */
public class CallCacheProxyFactoryTest {

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
