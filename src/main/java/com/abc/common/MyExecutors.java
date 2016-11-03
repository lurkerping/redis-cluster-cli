package com.abc.common;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * predefined executors
 */
public class MyExecutors {

    private static class DefaultThreadFactory implements ThreadFactory {

        private String group = null;

        public DefaultThreadFactory(String group) {
            this.group = group;
        }

        private ThreadGroup getThreadGroup() {
            SecurityManager sm = System.getSecurityManager();
            if (sm == null) {
                return Thread.currentThread().getThreadGroup();
            } else {
                return sm.getThreadGroup();
            }
        }

        private final AtomicInteger count = new AtomicInteger(0);

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(getThreadGroup(), r, group + count.incrementAndGet(), 0);
            if (t.getPriority() != Thread.NORM_PRIORITY) {
                t.setPriority(Thread.NORM_PRIORITY);
            }
            if (t.isDaemon() == false) {
                t.setDaemon(true);
            }

            return t;
        }
    }

    public static final ExecutorService FIXED = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), new DefaultThreadFactory("FIXED"));


}
