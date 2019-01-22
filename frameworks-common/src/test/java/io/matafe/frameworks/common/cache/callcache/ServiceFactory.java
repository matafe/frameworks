package io.matafe.frameworks.common.cache.callcache;

class ServiceFactory {

    static Service getCachedService() {
	return CallCacheProxyFactory.newInstance(new ServiceImpl(), Service.class);
    }

    static Service getService() {
	return new ServiceImpl();
    }
}
