package org.kaffeezusatz.commodity.collections;

import java.util.Map.Entry;
import java.util.TreeMap;

public class OrderedRunnableQueue {
	
	private final int runEveryEntries;
	
	private final int runEveryMillis;

	private Integer maxKey;
	
	private TreeMap<Integer, Runnable> map;

	/**
	 * Ausführen wenn kein Leak oder Ausführen nach X neuen Elementen.
	 */
	public OrderedRunnableQueue(final int runEveryEntries, final int runEveryMillis) {
		this.runEveryEntries = runEveryEntries;
		this.runEveryMillis = runEveryMillis;
		
		this.maxKey = -1;
		
		this.map = new TreeMap<Integer, Runnable>();
	}

	public synchronized void addRunnable(final Integer num, final Runnable r) {
		if (num == null) {
			throw new IllegalArgumentException("Integer num can't be null!");
		}
		if (r == null) {
			throw new IllegalArgumentException("Runnable r can't be null!");
		}
		
		this.map.put(num, r);
		
		System.out.print(num + "... ");
		
		runQueue();
		
		System.out.println("");
	}
	
	public synchronized void addRunnable(final OrderedRunnable or) {
		if (or == null) {
			throw new IllegalArgumentException("OrderedRunnable or can't be null!");
		}
		
		addRunnable(or.getNum(), or);
	}
	
	protected void runQueue() {
		int last = maxKey.intValue() + 1;
		
		TreeMap<Integer, Runnable> mapCopy = new TreeMap<Integer, Runnable>(map);
		
		for (Entry<Integer, Runnable> runnable : mapCopy.entrySet()) {
			if (last == runnable.getKey().intValue()) {
				last += 1;
				maxKey = runnable.getKey();
				
				System.out.print(" " + runnable.getKey());
				
				map.remove(runnable.getKey()).run();
			} else if (runEveryEntries == map.size()) {
				System.out.print(" Run every " + runEveryEntries);
				runCurrentQueue();
				last = maxKey.intValue() + 1;
			}
		}
	}
		
	protected void runCurrentQueue() {
		synchronized (map) {
			for (Entry<Integer, Runnable> entry : map.entrySet()) {
				if (entry.getValue() != null) {
					entry.getValue().run();
					System.out.print(" " + entry.getKey());
				}
				maxKey = entry.getKey();
			}
		}
		
		map.clear();
	}
	
	public static interface OrderedRunnable extends Runnable {
		public int getNum();
	}
}
