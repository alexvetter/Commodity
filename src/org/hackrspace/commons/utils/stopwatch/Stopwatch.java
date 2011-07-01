package org.hackrspace.commons.utils.stopwatch;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Stopwatch {
	private static HashMap<String, Stopwatch> instances;
	private static Logger logger;
	
	private long startTime;
	private long stopTime;
	private boolean running;
	private String name;
	
	public synchronized static Stopwatch getInstance(Class<?> c) {
		return getInstance(c.getName());
	}
	
	public synchronized static Stopwatch getInstance(String name) {
		if (instances == null) {
			instances = new HashMap<String, Stopwatch>();
		}
		
		if (logger == null) {
			logger = Logger.getLogger(Stopwatch.class.getName());
		}
		
		Stopwatch instance = instances.get(name);
		if (instance == null) {
			instances.put(name, new Stopwatch(name));
		}
		
		return instances.get(name);
	}
	
	private Stopwatch() {
	}

	private Stopwatch(String name) {
		startTime = 0;
		stopTime = 0;
		running = false;
		this.name = name;
	}
	
	/**
	 * Start the Stopwatch.
	 */
	public void start() {
		logger.log(Level.INFO, "Start <" + name + ">");
		
		this.startTime = System.currentTimeMillis();
		this.running = true;
	}

	/**
	 * Stop the Stopwatch. Now you can get the elapsed time.
	 */
	public void stop() {
		logger.log(Level.INFO, "Stop <" + name + ">");
		
		this.stopTime = System.currentTimeMillis();
		this.running = false;
		
		logger.log(Level.INFO, "Elapsed time <" + name + "> <" + getElapsedTime() + "> millis / <" + getElapsedTimeSecs() + "> secs");
	}

	/**
	 * elaspsed time in milliseconds
	 * 
	 * @return
	 */
	public long getElapsedTime() {
		long elapsed;
		if (this.running) {
			elapsed = (System.currentTimeMillis() - this.startTime);
		} else {
			elapsed = (this.stopTime - this.startTime);
		}
		return elapsed;
	}

	/**
	 * elaspsed time in seconds
	 * 
	 * @return
	 */
	public long getElapsedTimeSecs() {
		long elapsed;
		if (this.running) {
			elapsed = ((System.currentTimeMillis() - this.startTime) / 1000);
		} else {
			elapsed = ((this.stopTime - this.startTime) / 1000);
		}
		return elapsed;
	}
}