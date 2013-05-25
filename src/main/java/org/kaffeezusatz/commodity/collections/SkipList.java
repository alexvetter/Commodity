package org.kaffeezusatz.commodity.collections;
import java.util.ArrayList;
import java.util.List;

public class SkipList<T extends Comparable<? super T>> {

	private List<SkipNode<T>> sentinels;
	
	private final int maxLevel;
	private final double riseRate; // 0.25 - 0.5

	public SkipList(int maxLevel, double riseRate, T left, T right) {
		this.maxLevel = maxLevel;
		this.riseRate = riseRate;
		
		this.sentinels = new ArrayList<SkipNode<T>>(this.maxLevel);
		for (int i = 0; i < this.maxLevel; i++) {
			this.sentinels.add(null);
		}
		
		
		SkipNode<T> rightSentinel = null;
		SkipNode<T> leftSentinel = null;
		
		for (int i = this.maxLevel - 1; i >= 0; i--) {
			rightSentinel = new SkipNode<T>(right, null, rightSentinel);
			leftSentinel = new SkipNode<T>(left, rightSentinel, leftSentinel);
			
			this.sentinels.set(i, leftSentinel);
		}
	}

	public T search(T e) {
		SkipNode<T> current = this.sentinels.get(0);
		
		while (current != null && current.getElement().compareTo(e) != 0) {
			if (current.getRight() != null && e.compareTo(current.getRight().getElement()) >= 0) {
				current = current.getRight();
			} else {
				current = current.getDown();
			}
		}

		return (current == null) ? null : current.getElement();
	}

	public T insert(T e) {
		return insert(e, computeRandomLevel());
	}
	
	public T insert(T e, int levels) {
		if (search(e) != null) {
			return null;
		}
		
		SkipNode<T> current = this.sentinels.get(0);
		List<SkipNode<T>> predecessors = new ArrayList<SkipNode<T>>(this.sentinels);
		
		int currentLevel = 0;
		while (currentLevel < this.maxLevel && current != null && current.getElement().compareTo(e) != 0) {
			
			if (e.compareTo(current.getRight().getElement()) > 0) {
				current = current.getRight();
			} else {
				predecessors.set(currentLevel++, current);
				current = current.getDown();
			}
		}
		
		SkipNode<T> newNode = null;
		for (int i = 1; i <= levels; i++) {
			newNode = new SkipNode<T>(e, predecessors.get(this.maxLevel-i).getRight(), newNode);
			predecessors.get(this.maxLevel-i).setRight(newNode);
		}
		
		return e;
	}

	protected int computeRandomLevel() {
		int randomLevel = 1;
		while (Math.random() < this.riseRate) {
			randomLevel++;
			if (randomLevel > maxLevel) {
				randomLevel = maxLevel;
				break;
			}
		}
		
		return randomLevel;
	}

	public void delete(T e) {
		if (search(e) == null) {
			return;
		}
		
		SkipNode<T> current = this.sentinels.get(0);
		List<SkipNode<T>> predecessors = new ArrayList<SkipNode<T>>(this.sentinels);
		
		int currentLevel = 0;
		while (currentLevel < this.maxLevel && current != null && current.getElement().compareTo(e) != 0) {
			if (e.compareTo(current.getRight().getElement()) > 0) {
				current = current.getRight();
			} else {
				predecessors.set(currentLevel++, current);
				current = current.getDown();
			}
		}
		
		for (int i = 0; i < predecessors.size(); i++) {
			if (predecessors.get(i).getRight().getElement().compareTo(e) == 0) {
				SkipNode<T> right = predecessors.get(i).getRight().getRight();
				predecessors.get(i).setRight(right);
			}
		}
	}

	public String toString() {
		final StringBuffer string = new StringBuffer();
		
		for (int i = 0; i < this.maxLevel; i++) {
			SkipNode<T> current = this.sentinels.get(i);
			
			int count = 0;
			while (current != null) {
				count++;
				string.append(current.getElement().toString() + " ");
				current = current.getRight();
			}
			
			string.append(" count: " + (count-2) + "\n");
		}
				
		return string.toString();
	}
	
	static class SkipNode<B extends Comparable<? super B>> {
		private B element;
		private SkipNode<B> right;
		private SkipNode<B> down;

		SkipNode(B element, SkipNode<B> right, SkipNode<B> down) {
			this.element = element;
			this.right = right;
			this.down = down;
		}

		B getElement() {
			return element;
		}

		SkipNode<B> getRight() {
			return right;
		}

		void setRight(SkipNode<B> right) {
			this.right = right;
		}

		SkipNode<B> getDown() {
			return down;
		}
		
		public String toString() {
			return this.element.toString();
		}
	}
}
