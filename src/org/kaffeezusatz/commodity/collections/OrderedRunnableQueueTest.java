package org.kaffeezusatz.commodity.collections;

import static org.junit.Assert.*;

import org.junit.Test;
import org.kaffeezusatz.commodity.collections.OrderedRunnableQueue.OrderedRunnableQueueListener;

public class OrderedRunnableQueueTest implements OrderedRunnableQueueListener {
	@Test
	public final void add() {
		final Runnable r = new Runnable() {
			@Override
			public void run() {
				//System.out.println("Peter");
			}
		};
		
		final OrderedRunnableQueue orq = new OrderedRunnableQueue(5);
		
		orq.addListener(this);
		
		orq.add(0, r); // 0
		orq.add(3, r); // 1
		orq.add(2, r); // 2
		orq.add(1, r); // 3
		orq.add(4, r); // 4
		orq.add(6, r); // 5
		orq.add(5, r); // 6
		orq.add(7, r); // 7
		orq.add(9, r); // 8
		orq.add(8, r); // 9
		orq.add(10, r); // 10
		orq.add(11, r); // 11
		orq.add(16, r); // 12
		orq.add(13, r); // 13
		orq.add(12, r); // 14
		orq.add(15, r); // 15
		orq.add(18, r); // 16
		orq.add(14, r); // 17
		orq.add(19, r); // 18
		orq.add(17, r); // 19
		orq.add(20, r); // 20
 		orq.add(26, r); // 21
		orq.add(24, r); // 22
		orq.add(23, r); // 23
		orq.add(22, r); // 24
		orq.add(25, r); // 25
		orq.add(21, r); // 26
	}
	
	@Test
	public final void addNull() {
		final OrderedRunnableQueue orq = new OrderedRunnableQueue(5);
		try {
			orq.add(null);
		} catch (Exception e) {}
		
		try {
			orq.add(1, null);
		} catch (Exception e) {}
		
		try {
			orq.add(null, null);
		} catch (Exception e) {
		}
	}

	private boolean runForced = false;
	
	@Override
	public synchronized void runForcedEvent() {
		System.out.println("Run forced!");
		
		if (!runForced) {
			runForced = true;
		} else {
			fail("Run should only be forced once!");
		}
	}
	
	private Integer lastNumber = -1;
	
	@Override
	public synchronized void runEvent(Integer number) {
		System.out.println("Run " + number);
		
		assertEquals((lastNumber + 1), number.intValue());
		lastNumber++;
		
		if (number.intValue() == 20) {
			lastNumber++;
		}
	}
	
	@Override
	public synchronized void addEvent(Integer number) {
		System.out.println("\tAdd " + number);
	}
}
