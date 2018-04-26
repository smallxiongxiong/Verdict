package com.law.verdict.services.options;

import java.util.concurrent.CountDownLatch;

public abstract class CrawlerOption<T>  {
	CountDownLatch countDown;

	public abstract T done();

	public T call() {
		return done();
	}

	public void setCountDown(CountDownLatch countDown) {
		this.countDown = countDown;
	}

}
