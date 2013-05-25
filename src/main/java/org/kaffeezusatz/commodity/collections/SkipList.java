package org.kaffeezusatz.commodity.collections;

import java.util.ArrayList;
import java.util.List;

public class SkipList<T extends Comparable<? super T>> {

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

    public T search(T e) {
        SkipNode<T> current = this.sentinels.get(0);

        while (current != null && current.compareTo(e) != 0) {
            if (current.getRight() != null && current.getRight().compareTo(e) <= 0) {
                current = current.getRight();
            } else {
                current = current.getDown();
            }
        }

        return (current == null) ? null : current.getPayload();
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
        while (currentLevel < this.maxLevel && current != null && current.compareTo(e) != 0) {
            if (current.getRight().compareTo(e) <= 0) {
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
        while (currentLevel < this.maxLevel && current != null && current.compareTo(e) != 0) {
            if (current.getRight().compareTo(e) <= 0) {
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
    }

    public String toString() {
        final StringBuilder string = new StringBuilder();

        for (int i = 0; i < this.maxLevel; i++) {
            SkipNode<T> current = this.sentinels.get(i);

            int count = 0;
            while (current != null) {
                count++;
                string.append(current.getPayload()).append(" ");
                current = current.getRight();
            }

            string.append(" count: ").append(count - 2).append("\n");
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
