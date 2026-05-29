package com.replaymod.sms.util;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class RateLimiter {
    private final int limit;
    private final long windowMillis;
    private final ConcurrentHashMap<String, WindowCounter> counters = new ConcurrentHashMap<>();

    public RateLimiter(int limit, long windowSeconds) {
        this.limit = limit;
        this.windowMillis = TimeUnit.SECONDS.toMillis(windowSeconds);
    }

    public synchronized boolean check(String serviceName, String operationName) {
        String key = serviceName + ":" + operationName;
        WindowCounter counter = counters.computeIfAbsent(key, k -> new WindowCounter());

        return counter.tryIncrement();
    }

    private class WindowCounter {
        private AtomicInteger count = new AtomicInteger(0);
        private volatile long windowStart = System.currentTimeMillis();

        synchronized boolean tryIncrement() {
            long now = System.currentTimeMillis();
            if (now - windowStart > windowMillis) {
                count.set(0);
                windowStart = now;
            }
            return count.incrementAndGet() <= limit;
        }
    }
}
