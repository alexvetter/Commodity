package org.kaffeezusatz.commodity.collections;

import java.util.*;

public class SkipList<T extends Comparable<? super T>> implements SortedSet<T> {

    private static final int DEFAULT_MAXLEVEL = 6;
    private static final double DEFAULT_RISERATE = 0.25;

    /**
     *
     */
    private final List<SkipNode<T>> sentinels;

    /**
     *
     */
    private final int maxLevel;

    /**
     * riseRate should be between 0.25 and 0.5
     */
    private final double riseRate;

    public SkipList() {
        this(DEFAULT_MAXLEVEL, DEFAULT_RISERATE);
    }
    
    public SkipList(int maxLevel, double riseRate) {
        this.maxLevel = maxLevel;
        this.riseRate = riseRate;
        this.sentinels = getSentinels(this.maxLevel);
    }

    protected List<SkipNode<T>> getSentinels(int maxLevel) {
        List<SkipNode<T>> sentinels = new ArrayList<SkipNode<T>>(maxLevel);

        /* init array list */
        for (int i = 0; i < maxLevel; i++) {
            sentinels.add(null);
        }

        SentinelSkipNode rightSentinel = null;
        SentinelSkipNode leftSentinel = null;

        for (int i = maxLevel - 1; i >= 0; i--) {
            rightSentinel = new SentinelSkipNode(null, rightSentinel);
            leftSentinel = new SentinelSkipNode(rightSentinel, leftSentinel);

            sentinels.set(i, leftSentinel);
        }

        return sentinels;
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
        return new SkipListIterator(this.sentinels.get(this.maxLevel - 1));
    }

    @Override
    public Object[] toArray() {
        Object array[] = new Object[this.size()];

        int i = 0;
        for (T e : this) {
            array[i++] = e;
        }

        return array;
    }

    @Override
    public <E> E[] toArray(E[] array) {
        int i = 0;
        for (T e : this) {
            array[i++] = (E) e;
        }

        return array;
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
        this.sentinels.clear();
        this.sentinels.addAll(getSentinels(this.maxLevel));
    }

    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof SkipList)) return false;

        return this.size() == ((SkipList<T>) o).size() && this.hashCode() == o.hashCode();
    }

    public int hashCode() {
        int hashCode = 1;
        for (T e : this) {
            hashCode = 31 * hashCode + (e == null ? 0 : e.hashCode());
        }

        return hashCode;
    }

    @Override
    public int size() {
        if (isEmpty()) {
            return 0;
        }

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
        SkipNode<T> node = this.sentinels.get(this.maxLevel - 1).getRight();

        // if right of lowest sentinels is also a sentinel than the list is empty
        return node.isSentinel();
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

    @Override
    public Comparator<? super T> comparator() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Returns a view of the portion of this set whose elements range from fromElement, inclusive, to toElement, exclusive.
     *
     * @param fromElement low endpoint (inclusive) of the returned set
     * @param toElement high endpoint (exclusive) of the returned set
     * @return a view of the portion of this set whose elements range from fromElement, inclusive, to toElement, exclusive
     */
    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {
        SkipList<T> subSet = new SkipList<T>(this.maxLevel, this.riseRate);

        for (T element : this) {
            if (element.compareTo(fromElement) >= 0 && element.compareTo(toElement) < 0) {
                subSet.add(element);
            } else if (element.compareTo(toElement) >= 0) {
                break;
            }
        }

        return subSet;
    }


    /**
     * Returns a view of the portion of this set whose elements are strictly less than toElement.
     *
     * @param toElement high endpoint (exclusive) of the returned set
     * @return
     */
    @Override
    public SortedSet<T> headSet(T toElement) {
        SkipList<T> headSet = new SkipList<T>(this.maxLevel, this.riseRate);

        for (T element : this) {
            if (element.compareTo(toElement) >= 0) {
                break;
            }

            headSet.add(element);
        }

        return headSet;
    }

    /**
     * Returns a view of the portion of this set whose elements are greater than or equal to fromElement.
     *
     * @param fromElement low endpoint (inclusive) of the returned set
     * @return
     */
    @Override
    public SortedSet<T> tailSet(T fromElement) {
        SkipList<T> tailSet = new SkipList<T>(this.maxLevel, this.riseRate);

        for (T element : this) {
            if (element.compareTo(fromElement) >= 0) {
                tailSet.add(element);
            }
        }

        return tailSet;
    }

    /**
     * lowest element
     *
     * @return lowest element
     */
    @Override
    public T first() {
        return iterator().next();
    }

    /**
     * greatest element
     *
     * @return greatest element
     */
    @Override
    public T last() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        T last = null;
        for (T temp : this) {
            last = temp;
        }

        return last;
    }

    /**
     * Helper class for SkipList
     *
     * SentinelSkipNode is always greater compareTo other <T>
     */
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

        @Override
        public boolean isSentinel() {
            return true;
        }
    }

    /**
     * Helper class for SkipList
     *
     * @param <T> the payload of SkipNode must
     */
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

        public boolean isSentinel() {
            return false;
        }
    }

    /**
     * Iterator for SkipList
     */
    public class SkipListIterator implements Iterator<T> {
        private SkipNode<T> current;

        public SkipListIterator(SkipNode<T> sentinel) {
            this.current = sentinel.getRight();
        }

        @Override
        public boolean hasNext() {
            return ( (current != null) && (!current.isSentinel()) );
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            SkipNode<T> next = this.current;
            this.current = this.current.getRight();

            return next.getPayload();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}