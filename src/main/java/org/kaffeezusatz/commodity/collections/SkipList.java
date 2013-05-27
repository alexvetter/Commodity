package org.kaffeezusatz.commodity.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class SkipList<T extends Comparable<? super T>> implements Set<T> {

    private final List<SkipNode<T>> sentinels;
    private final int maxLevel;

    /**
     * riseRate should be between 0.25 and 0.5
     */
    private final double riseRate;

    public SkipList(int maxLevel, double riseRate) {
        this.maxLevel = maxLevel;
        this.riseRate = riseRate;

        this.sentinels = new ArrayList<SkipNode<T>>(this.maxLevel);

        /* init array list */
        for (int i = 0; i < this.maxLevel; i++) {
            this.sentinels.add(null);
        }

        SentinelSkipNode rightSentinel = null;
        SentinelSkipNode leftSentinel = null;

        for (int i = this.maxLevel - 1; i >= 0; i--) {
            rightSentinel = new SentinelSkipNode(null, rightSentinel);
            leftSentinel = new SentinelSkipNode(rightSentinel, leftSentinel);

            this.sentinels.set(i, leftSentinel);
        }
    }

    @Override
    public boolean contains(Object o) {
        T e = (T) o;

        SkipNode<T> current = this.sentinels.get(0);

        while (current != null && current.compareTo(e) != 0) {
            if (current.getRight() != null && current.getRight().compareTo(e) <= 0) {
                current = current.getRight();
            } else {
                current = current.getDown();
            }
        }

        return (current != null);
    }

    @Override
    public Iterator<T> iterator() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Object[] toArray() {
        return new Object[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public <T> T[] toArray(T[] t) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean add(T e) {
        return add(e, computeRandomLevel());
    }

    public boolean add(T e, int levels) {
        if (contains(e)) {
            return false;
        }

        SkipNode<T> current = this.sentinels.get(0);
        List<SkipNode<T>> predecessors = new ArrayList<SkipNode<T>>(this.sentinels);

        int currentLevel = 0;
        while (currentLevel < this.maxLevel && current != null && current.compareTo(e) != 0) {
            if (current.getRight().compareTo(e) < 0) {
                current = current.getRight();
            } else {
                predecessors.set(currentLevel++, current);
                current = current.getDown();
            }
        }

        SkipNode<T> newNode = null;
        for (int i = 1; i <= levels; i++) {
            newNode = new SkipNode<T>(e, predecessors.get(this.maxLevel - i).getRight(), newNode);
            predecessors.get(this.maxLevel - i).setRight(newNode);
        }

        return true;
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

    @Override
    public boolean remove(Object o) {
        if (!contains(o)) {
            return false;
        }

        T e = (T) o;

        SkipNode<T> current = this.sentinels.get(0);
        List<SkipNode<T>> predecessors = new ArrayList<SkipNode<T>>(this.sentinels);

        int currentLevel = 0;
        while ((currentLevel < this.maxLevel) && (current != null) && (current.compareTo(e) != 0)) {
            if (current.getRight().compareTo(e) < 0) {
                current = current.getRight();
            } else {
                predecessors.set(currentLevel++, current);
                current = current.getDown();
            }
        }

        for (SkipNode<T> predecessor : predecessors) {
            if (predecessor.getRight().compareTo(e) == 0) {
                SkipNode<T> right = predecessor.getRight().getRight();
                predecessor.setRight(right);
            }
        }

        return true;
    }

    @Override
    public boolean containsAll(Collection<?> objects) {
        boolean all = true;
        for (Object o : objects) {
            all = contains(o);
            if (!all) {
                break;
            }
        }

        return all;
    }

    @Override
    public boolean addAll(Collection<? extends T> es) {
        boolean all = true;
        for (T e : es) {
            all = add(e);
            if (!all) {
                break;
            }
        }

        return all;
    }

    @Override
    public boolean retainAll(Collection<?> objects) {
        // not yet implemented
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> objects) {
        for (Object o : objects) {
            if (contains(o)) {
                remove(o);
            }
        }

        return true;
    }

    public void clear() {
        // not yet implemented
    }

    public boolean equals(Object o) {
        // not yet implemented
        return false;
    }

    public int hashCode() {
        // not yet implemented
        return -1;
    }

    @Override
    public int size() {
        SkipNode<T> current = this.sentinels.get(this.maxLevel - 1);

        int size = -2; // because of sentinels
        while (current != null) {
            size++;
            current = current.getRight();
        }

        return size;
    }

    @Override
    public boolean isEmpty() {
        return (size() == 0);
    }

    public String toString() {
        final StringBuilder string = new StringBuilder();

        for (int i = 0; i < this.maxLevel; i++) {
            SkipNode<T> current = this.sentinels.get(i);

            int count = -2; // because of sentinels
            while (current != null) {
                count++;
                string.append("[").append(current.toString()).append("]").append(" ");
                current = current.getRight();
            }

            string.append(" count: ").append(count).append("\n");
        }

        return string.toString();
    }

    class SentinelSkipNode extends SkipNode<T> {
        SentinelSkipNode(SkipNode<T> right, SkipNode<T> down) {
            super(null, right, down);
        }

        @Override
        public int compareTo(T t) {
            // Sentinel is always greater!
            return 1;
        }

        @Override
        public String toString() {
            return "Sentinel";
        }
    }

    class SkipNode<T extends Comparable<? super T>> implements Comparable<T> {
        private T payload;
        private SkipNode<T> right;
        private SkipNode<T> down;

        SkipNode(T payload, SkipNode<T> right, SkipNode<T> down) {
            this.payload = payload;
            this.right = right;
            this.down = down;
        }

        T getPayload() {
            return payload;
        }

        SkipNode<T> getRight() {
            return right;
        }

        void setRight(SkipNode<T> right) {
            this.right = right;
        }

        SkipNode<T> getDown() {
            return down;
        }

        public String toString() {
            return this.payload.toString();
        }

        @Override
        public int compareTo(T t) {
            return payload.compareTo(t);
        }
    }
}
