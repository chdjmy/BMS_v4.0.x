package com.example.http;

import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPool {

	static ArrayBlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue(15);

	static AbstractExecutorService pool = new ThreadPoolExecutor(20, 30 , 15L,
			TimeUnit.SECONDS, blockingQueue,
			new ThreadPoolExecutor.DiscardOldestPolicy());

	private static ThreadPool instance = null;

	public static ThreadPool getSingle() {
		if (instance == null) {
			instance = new ThreadPool();
		}
		return instance;
	}

	public void execute(Runnable r) {
		pool.execute(r);
	}

	public static void shutdown() {
		if (pool != null) {
			pool.shutdown();
		}
	}

	public static void shutdownRightnow() {
		if (pool == null){
			return;
		}
		pool.shutdownNow();
		try {
			pool.awaitTermination(1L, TimeUnit.MICROSECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void removeTaskFromQueue() {
	}
}
