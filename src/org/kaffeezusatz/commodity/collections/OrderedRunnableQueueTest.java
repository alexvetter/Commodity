package org.kaffeezusatz.commodity.collections;

import org.junit.Test;

public class OrderedRunnableQueueTest {
	@Test
	public final void test() {
		final Runnable r = new Runnable() {
			@Override
			public void run() {
				//System.out.println("Peter");
			}
		};
		
		final OrderedRunnableQueue orq = new OrderedRunnableQueue(5, 4000);
		
		orq.addRunnable(0, r); // 0
		orq.addRunnable(3, r); // 1
		orq.addRunnable(2, r); // 2
		orq.addRunnable(1, r); // 3
		orq.addRunnable(4, r); // 4
		orq.addRunnable(6, r); // 5
		orq.addRunnable(5, r); // 6
		orq.addRunnable(7, r); // 7
		orq.addRunnable(9, r); // 8
		orq.addRunnable(8, r); // 9
		orq.addRunnable(10, r); // 10
		orq.addRunnable(11, r); // 11
		orq.addRunnable(16, r); // 12
		orq.addRunnable(13, r); // 13
		orq.addRunnable(12, r); // 14
		orq.addRunnable(15, r); // 15
		orq.addRunnable(18, r); // 16
		orq.addRunnable(14, r); // 17
		orq.addRunnable(19, r); // 18
		orq.addRunnable(17, r); // 19
		orq.addRunnable(20, r); // 20
 		orq.addRunnable(26, r); // 21
		orq.addRunnable(24, r); // 22
		orq.addRunnable(23, r); // 23
		orq.addRunnable(22, r); // 24
		orq.addRunnable(25, r); // 25
		orq.addRunnable(21, r); // 26
	}
	
	@Test
	public final void addNull() {
		final OrderedRunnableQueue orq = new OrderedRunnableQueue(5, 4000);
		try {
			orq.addRunnable(null);
		} catch (Exception e) {}
		
		try {
			orq.addRunnable(1, null);
		} catch (Exception e) {}
		
		try {
			orq.addRunnable(null, null);
		} catch (Exception e) {
		}
	}
}
