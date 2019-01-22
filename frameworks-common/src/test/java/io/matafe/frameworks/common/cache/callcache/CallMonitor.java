package io.matafe.frameworks.common.cache.callcache;

import java.util.concurrent.atomic.AtomicInteger;

class CallMonitor {
    AtomicInteger i = new AtomicInteger();

    public int incrementAndGet() {
	return i.incrementAndGet();
    }

    public int get() {
	return i.get();
    }
}
