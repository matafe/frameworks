package io.matafe.frameworks.common.cache.callcache;

import java.util.Date;

class ServiceImpl implements Service {
    @Override
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
