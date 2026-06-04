/*
 * Copyright (C) 2026 SoManySweats contributors.
 *
 * This file is part of SoManySweats.
 *
 * SoManySweats is free software: you can redistribute it
 * and/or modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation, either
 * version 3 of the License, or (at your option) any later version.
 *
 * SoManySweats is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with SoManySweats. If not, see <https://www.gnu.org/licenses/>.
 */

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
