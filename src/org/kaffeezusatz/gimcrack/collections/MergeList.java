package org.kaffeezusatz.gimcrack.collections;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <p>
 * The idea behind this class is to merge multiple sorted lists without the
 * Collection Framework.<br>
 * No merged list will be saved, there is a iterator with it you can get the
 * merged list.
 * </p>
 * 
 * @author Alexander Vetter <vettera@gmail.com>
 */
public class MergeList<E extends Comparable<E>> implements Iterable<E> {
	/**
	 * All lists which will be merged with the iterator.
	 */
	private List<List<E>> lists;

	/**
	 * <p>
	 * Merges two or more lists into one. The List item type must be
	 * <code>Comparable</code>!<br>
	 * The lists must be sorted. See
	 * <code>MergeList.isSorted(List<T> list);</code>.
	 * </p>
	 * 
	 * @param lists
	 */
	public MergeList() {
		this.lists = new ArrayList<List<E>>(5);
	}

	/**
	 * Chain adding because of the fucking varargs compiler warning. Fix this
	 * guys!
	 * 
	 * @param list
	 * 
	 * @throws a
	 *             RuntimeException if the <code>list</code> isn't sorted.
	 * 
	 * @return
	 */
	public MergeList<E> add(List<E> list) {
		if (MergeList.isSorted(list)) {
			lists.add(list);
			return this;
		}
		throw new RuntimeException();
	}

	/**
	 * 
	 * @return
	 */
	private List<Iterator<E>> getIteratorList() {
		List<Iterator<E>> iterators = new ArrayList<Iterator<E>>(5);

		for (List<E> list : lists) {
			iterators.add(list.iterator());
		}

		return iterators;
	}

	/**
	 * Returns whether a list is strictly sorted.
	 * 
	 * @param list
	 *            List
	 * @return whether list is sorted
	 */
	public static <T extends Comparable<T>> boolean isSorted(List<T> list) {
		T prev = null;
		for (T t : list) {
			if (prev != null && prev.compareTo(t) >= 0) {
				return false;
			}
			prev = t;
		}
		return true;
	}

	/**
	 * 
	 */
	public Iterator<E> iterator() {
		return new Iterator<E>() {
			List<Iterator<E>> iterators = getIteratorList();
			List<E> next = new ArrayList<E>();
			boolean init = false;

			@Override
			public boolean hasNext() {
				init();

				for (Iterator<E> iterator : this.iterators) {
					if (iterator.hasNext()) {
						return true;
					}
				}

				for (E i : this.next) {
					if (i != null) {
						return true;
					}
				}

				return false;
			}

			@Override
			public E next() {
				init();

				int iterators = this.iterators.size();
				for (int i = 0; i < iterators; i++) {
					if (this.next.get(i) == null && this.iterators.get(i).hasNext()) {
						this.next.set(i, this.iterators.get(i).next());
					}
				}

				E r = null;
				Integer ir = null;
				for (int i = 0; i < iterators; i++) {
					E n1 = r;
					Integer i1 = ir;
					E n2 = null;
					Integer i2 = null;

					if (n1 == null) {
						n1 = this.next.get(i);
						i1 = i;
						if ((i + 1) < iterators) {
							n2 = this.next.get(++i);
							i2 = i;
						}
					} else {
						n2 = this.next.get(i);
						i2 = i;
					}

					if (n1 != null && n2 != null && n1.compareTo(n2) > 0) {
						r = n2;
						ir = i2;
					} else if (n1 != null && n2 != null && n1.compareTo(n2) == 0) {
						if (ir == null) {
							ir = i1;
						}
					} else if (n1 != null) {
						r = n1;
						ir = i1;
					} else if (n2 != null) {
						r = n2;
						ir = i2;
					}
				}
				this.next.set(ir, null);

				return r;
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}

			private void init() {
				if (!init) {
					int iterators = this.iterators.size();
					for (int i = 0; i < iterators; i++) {
						next.add(null);
					}
					init = true;
				}
			}
		};
	}
}
