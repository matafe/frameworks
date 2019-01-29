package io.matafe.frameworks.common.cache.callcache;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Mock;
import mockit.MockUp;
import mockit.integration.junit4.JMockit;

/**
 * Unit Test for <code>CallCacheProxyFactory</code>.
 * 
 * @author matafe@gmail.com
 */
@RunWith(JMockit.class)
public class CallCacheProxyFactoryTest {

    private CallMonitor monitor;

    @Before
    public void before() {
	this.monitor = new CallMonitor();
    }

    @Test
    public void testCall() throws Exception {
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
    public void testCachedCall() throws Exception {

	Service service = ServiceFactory.getCachedService();

	int calls = 10;

	for (int i = 0; i < calls; i++) {
	    service.process("A", monitor);
	}

	assertThat(monitor.get(), is(1));

	service.process("B", monitor);

	assertThat(monitor.get(), is(2));
    }

    @Test
    public void testCachedCallExpiration() throws Exception {

	new MockUp<CallCacheConfig>() {
	    @Mock
	    public long getTimeToLiveMillis() {
		return 3000L;
	    }
	};

	Service service = ServiceFactory.getCachedService();

	int calls = 10;

	for (int i = 0; i < calls; i++) {
	    service.process("A", monitor);
	}

	assertThat(monitor.get(), is(1));

	TimeUnit.SECONDS.sleep(3L);

	// already expired
	service.process("A", monitor);

	assertThat(monitor.get(), is(2));

	service.process("B", monitor);

	assertThat(monitor.get(), is(3));
    }

}
