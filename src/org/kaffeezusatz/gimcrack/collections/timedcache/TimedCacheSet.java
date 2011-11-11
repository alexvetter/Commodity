package org.kaffeezusatz.gimcrack.collections.timedcache;

import java.util.AbstractSet;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 
 * <p>
 * <strong>Note that this implementation is not synchronized.</strong> If
 * multiple threads access a hash set concurrently, and at least one of the
 * threads modifies the set, it <i>must</i> be synchronized externally. This is
 * typically accomplished by synchronizing on some object that naturally
 * encapsulates the set.
 * 
 * If no such object exists, the set should be "wrapped" using the
 * {@link Collections#synchronizedSet Collections.synchronizedSet} method. This
 * is best done at creation time, to prevent accidental unsynchronized access to
 * the set:
 * 
 * <pre>
 *   Set s = Collections.synchronizedSet(new HashSet(...));
 * </pre>
 * 
 * <p>
 * The iterators returned by this class's <tt>iterator</tt> method are
 * <i>fail-fast</i>: if the set is modified at any time after the iterator is
 * created, in any way except through the iterator's own <tt>remove</tt> method,
 * the Iterator throws a {@link ConcurrentModificationException}. Thus, in the
 * face of concurrent modification, the iterator fails quickly and cleanly,
 * rather than risking arbitrary, non-deterministic behavior at an undetermined
 * time in the future.
 * 
 * <p>
 * Note that the fail-fast behavior of an iterator cannot be guaranteed as it
 * is, generally speaking, impossible to make any hard guarantees in the
 * presence of unsynchronized concurrent modification. Fail-fast iterators throw
 * <tt>ConcurrentModificationException</tt> on a best-effort basis. Therefore,
 * it would be wrong to write a program that depended on this exception for its
 * correctness: <i>the fail-fast behavior of iterators should be used only to
 * detect bugs.</i>
 * 
 */
public class TimedCacheSet<E> extends AbstractSet<E> implements Set<E>, Cloneable {

	private long cacheExpire;

	private Set<CacheItem<E>> set;

	private Timer timer;

	private TimerTask gc;

	public TimedCacheSet(long cacheExpire) {
		this.cacheExpire = cacheExpire;
		this.set = new HashSet<CacheItem<E>>();

		gc = new TimerTask() {
			public void run() {
				try {
					Set<CacheItem<E>> s = new HashSet<CacheItem<E>>(set);
					for (CacheItem<E> e : s) {
						if (!e.isAlive()) {
							set.remove(e);
						}
					}
				} catch (NoSuchElementException e) {
					//ignore
				}
			}
		};

		timer = new Timer("TimedCacheSet", true);
		timer.schedule(gc, 0, this.cacheExpire);
	}

	public TimedCacheSet(Set<E> toTransform, long cacheExpire) {
		this.cacheExpire = cacheExpire;
		this.set = new HashSet<CacheItem<E>>();
		this.addAll(toTransform);
	}

	private CacheItem<E> newItem(E e) {
		return new CacheItem<E>(e, cacheExpire);
	}

	public boolean add(E e) {
		return set.add(newItem(e));
	}

	public void clear() {
		set.clear();
	}

	public boolean contains(Object o) {
		if (o == null) {
			return false;
		}

		Set<CacheItem<E>> s = new HashSet<CacheItem<E>>(set);

		for (CacheItem<E> e : s) {
			if (e.isAlive() && e.equals(o)) {
				s = null;
				return true;
			}
		}

		return false;
	}

	public Iterator<E> iterator() {
		return new Iterator<E>() {
			private Iterator<CacheItem<E>> i = set.iterator();

			public boolean hasNext() {
				return i.hasNext();
			}

			public E next() {
				if (this.hasNext()) {
					CacheItem<E> item = i.next();
					if (item.isAlive()) {
						return item.getValue();
					} else {
						return this.next();
					}
				}

				throw new NoSuchElementException();
			}

			public void remove() {
				i.remove();
			}
		};
	}

	public boolean remove(Object o) {
		if (o == null) {
			return false;
		}

		Set<CacheItem<E>> s = new HashSet<CacheItem<E>>(set);

		for (CacheItem<E> e : s) {
			if (e.equals(o)) {
				set.remove(o);
				return true;
			}
		}

		return false;
	}

	public int size() {
		gc.run();

		return set.size();
	}

	public TimedCacheSet<E> clone() {
		TimedCacheSet<E> clone = new TimedCacheSet<E>(cacheExpire);
		for (E e : this) {
			clone.add(e);
		}
		return clone;
	}
}

/**
 * This type is used as a container for items in TimedCacheSet.
 * 
 * @author alexandervetter
 * 
 * @param <E>
 */
class CacheItem<E> {
	private long birth;
	private long maxAge;
	private E payload;

	public CacheItem(E payload, long maxAge) {
		this.birth = System.currentTimeMillis();
		this.payload = payload;
		this.maxAge = maxAge;
	}

	public E getValue() {
		return payload;
	}

	public long getBirth() {
		return birth;
	}

	public void resetBirth() {
		this.birth = System.currentTimeMillis();
	}

	public boolean isAlive() {
		return (System.currentTimeMillis() - birth < maxAge);
	}

	public boolean equals(Object obj) {
		if (obj != null && obj instanceof CacheItem<?>) {
			CacheItem<?> o = (CacheItem<?>) obj;
			return this.payload.equals(o.payload);
		} else if (obj != null) {
			return this.payload.equals(obj);
		}
		return false;
	}

	public int hashCode() {
		return payload.hashCode();
	}
}