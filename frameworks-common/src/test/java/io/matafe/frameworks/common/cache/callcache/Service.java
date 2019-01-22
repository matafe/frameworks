package io.matafe.frameworks.common.cache.callcache;

interface Service {
    String process(String str, CallMonitor monitor);
}