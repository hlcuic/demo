package com.concurrent;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class CustomLockDemo {
	
	private CustomSync sync;
	
	public CustomLockDemo() {
		this.sync = new NonfairSync();
	}
	
	public void lock() {
		sync.lock();
	}
	
	public void unLock() {
		sync.tryRelease(1);
	}

	abstract static class CustomSync extends AbstractQueuedSynchronizer {

		private static final long serialVersionUID = 1L;

		abstract void lock();

		@Override
		protected boolean tryRelease(int releases) {
			int c = getState() - releases;
			if (Thread.currentThread() != getExclusiveOwnerThread())
				throw new IllegalMonitorStateException();
			boolean free = false;
			if (c == 0) {
				free = true;
				setExclusiveOwnerThread(null);
			}
			setState(c);
			return free;
		}
	}

	final static class NonfairSync extends CustomSync {

		private static final long serialVersionUID = 1L;

		@Override
		final void lock() {
			if (compareAndSetState(0, 1))
				setExclusiveOwnerThread(Thread.currentThread());
			else
				acquire(1);
		}

		protected final boolean tryAcquire(int acquires) {
			final Thread current = Thread.currentThread();
            int c = getState();
            if (c == 0) {
                if (compareAndSetState(0, acquires)) {
                    setExclusiveOwnerThread(current);
                    return true;
                }
            }
            else if (current == getExclusiveOwnerThread()) {
                int nextc = c + acquires;
                if (nextc < 0) // overflow
                    throw new Error("Maximum lock count exceeded");
                setState(nextc);
                return true;
            }
            return false;
        }
		
	}

}
