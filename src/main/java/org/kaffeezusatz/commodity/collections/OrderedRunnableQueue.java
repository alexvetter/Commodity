package org.kaffeezusatz.commodity.collections;

import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

public class OrderedRunnableQueue {
	
	private final int runEveryEntries;
		
	private final TreeMap<Integer, Runnable> queue;

	private final List<OrderedRunnableQueueListener> listener;

	private Integer lastNumber;
	
	private Integer runCountPerAdd;
	
	/**
	 * Ausfuehren wenn kein Leak oder Ausfuehren nach X neuen Elementen.
	 */
	public OrderedRunnableQueue(final int runEveryEntries) {
		this.runEveryEntries = runEveryEntries;
		
		this.lastNumber = -1;
		
		this.queue = new TreeMap<Integer, Runnable>();
		
		this.listener = new LinkedList<OrderedRunnableQueue.OrderedRunnableQueueListener>();
	}

	public void addListener(final OrderedRunnableQueueListener listener) {
		this.listener.add(listener);
	}

	public OrderedRunnableQueueListener removeListener(final OrderedRunnableQueueListener listener) {
		this.listener.remove(listener);
		return listener;
	}

	public synchronized void add(final Integer number, final Runnable runnable) {
		if (number == null) {
			throw new IllegalArgumentException("Integer number can't be null!");
		}
		
		if (runnable == null) {
			throw new IllegalArgumentException("Runnable runnable can't be null!");
		}
		
		fireAddEvent(number);
		
		runCountPerAdd = 0;
		
		queue.put(number, runnable);
		
		runOrdered();
		
		if (queue.size() > 0) {
			/*
			 * TODO Add TimerTask which runs runForced, 
			 * because it's possible that we don't get a new Runnable.
			 */
		}
		
		fireDoneEvent(number, runCountPerAdd);
	}
	
	public synchronized void add(final OrderedRunnable or) {
		if (or == null) {
			throw new IllegalArgumentException("OrderedRunnable or can't be null!");
		}
		
		add(or.getNumber(), or);
	}
	
	public synchronized int size() {
		return this.queue.size();
	}
	
	public synchronized int getLast() {
		return this.lastNumber.intValue();
	}
	
	/**
	 * Iterates over queue and run only queued runnables without leak!
	 * After X leaks queue will be handled as it is, disregard its leaks!
	 */
	protected void runOrdered() {
		TreeMap<Integer, Runnable> queueCopy = new TreeMap<Integer, Runnable>(queue);
		
		for (Entry<Integer, Runnable> runnable : queueCopy.entrySet()) {
			int currentKey = runnable.getKey().intValue();
			int nextKey = lastNumber + 1;
			
			if (nextKey == currentKey) {
				/* yeaah, right order, run it! */
				
				fireRunEvent(runnable.getKey());
				
				lastNumber = currentKey;
				queue.remove(runnable.getKey()).run();
			} else if (nextKey > currentKey) {
				/* ooh, we got an old one, run it anyway! */

				fireRunEvent(runnable.getKey());
				queue.remove(runnable.getKey()).run();
			} else {
				/* damn, wrong order! don't run! */
			}
		}
		
		queueCopy.clear();
		queueCopy = null;
		
		if (queue.size() >= runEveryEntries) {
			runForced();
		}
	}
	
	/**
	 * Iterates ordered over queue and runs every single runnable! After that queue will be cleared!
	 */
	protected void runForced() {
		fireRunForcedEvent();
		
		for (Entry<Integer, Runnable> entry : queue.entrySet()) {
			fireRunEvent(entry.getKey());
			lastNumber = entry.getKey();
			entry.getValue().run();
		}
		
		queue.clear();
	}
	
	private void fireAddEvent(final Integer number) {
		for (OrderedRunnableQueueListener listener : this.listener) {
			listener.addEvent(number);
		}
	}
	
	private void fireRunEvent(final Integer number) {
		runCountPerAdd++;
		
		for (OrderedRunnableQueueListener listener : this.listener) {
			listener.runEvent(number);
		}
	}
	
	private void fireRunForcedEvent() {
		for (OrderedRunnableQueueListener listener : this.listener) {
			listener.runForcedEvent();
		}
	}
	
	private void fireDoneEvent(final Integer number, final Integer runCount) {
		for (OrderedRunnableQueueListener listener : this.listener) {
			listener.doneEvent(number, runCount);
		}
	}
	
	public static interface OrderedRunnable extends Runnable {
		public int getNumber();
	}
	
	public static interface OrderedRunnableQueueListener {
		
		public void addEvent(final Integer number);
		
		public void runEvent(final Integer number);
		
		public void doneEvent(final Integer number, final Integer runCount);
		
		public void runForcedEvent();
	}
}
