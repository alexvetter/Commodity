package org.kaffeezusatz.commodity.collections;

import org.junit.Test;

import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import static org.junit.Assert.*;

/**
 * Tests SkipList
 *
 * @author Alexander Vetter <vettera@gmail.com>
 */
public class SkipListTest {

    @Test
    public void testContains() throws Exception {
        SkipList<Integer> l = new SkipList<Integer>();

        l.add(4);
        l.add(1);

        assertTrue(l.contains(4));
        assertTrue(l.contains(1));

        assertFalse(l.contains(0));
        assertFalse(l.contains(5));
    }

    @Test
    public void testIterator() throws Exception {
        SkipList<Integer> l = new SkipList<Integer>();

        l.add(4);
        l.add(1);
        l.add(3);
        l.add(5);
        l.add(6);

        Iterator<Integer> i = l.iterator();

        assertTrue(i.hasNext());

        assertEquals(new Integer(1), i.next());
        assertEquals(new Integer(3), i.next());
        assertEquals(new Integer(4), i.next());
        assertEquals(new Integer(5), i.next());

        assertTrue(i.hasNext());

        assertEquals(new Integer(6), i.next());

        l.clear();

        int a = 0;
        for (Integer e : l) {
            a++;
        }

        assertEquals(0, a);

        l.add(4);
        l.add(7);
        l.add(1);

        i = l.iterator();

        assertEquals(new Integer(1), i.next());
        assertEquals(new Integer(4), i.next());
        assertEquals(new Integer(7), i.next());
    }

    @Test
    public void testToTypedArray() throws Exception {
        Integer a[] = new Integer[3];

        a[0] = 1;
        a[1] = 4;
        a[2] = 7;

        SkipList<Integer> l = new SkipList<Integer>();

        l.add(4);
        l.add(7);
        l.add(1);

        Integer b[] = new Integer[3];

        l.toArray(b);

        assertArrayEquals(a, b);
    }

    @Test
    public void testToObjectArray() throws Exception {
        Object a[] = new Object[3];

        a[0] = 1;
        a[1] = 4;
        a[2] = 7;

        SkipList<Integer> l = new SkipList<Integer>();

        l.add(4);
        l.add(7);
        l.add(1);

        Object b[] = l.toArray();

        assertArrayEquals(a, b);
    }

    @Test
    public void testAddAndRemoveAndSizeAndIsEmpty() throws Exception {
        SkipList<Character> l = new SkipList<Character>();

        String chars = "ascnvfhtuzpiom96";

        for (int a = 0; a < chars.length(); a++) {
            l.add(chars.charAt(a));

            assertTrue(l.contains(chars.charAt(a)));
        }

        assertEquals(chars.length(), l.size());
        assertFalse(l.isEmpty());

        for (int a = chars.length()-1; a >= 0; a--) {
            l.remove(chars.charAt(a));

            assertFalse(l.contains(chars.charAt(a)));
        }

        assertEquals(0, l.size());
        assertTrue(l.isEmpty());
    }

    @Test
    public void testContainsAll() throws Exception {

    }

    @Test
    public void testAddAll() throws Exception {

    }

    @Test
    public void testRetainAll() throws Exception {

    }

    @Test
    public void testRemoveAll() throws Exception {

    }

    @Test
    public void testClear() throws Exception {
        SkipList<Character> l = new SkipList<Character>();

        String chars = "ascnvfhtuzpiom96";

        for (int a = 0; a < chars.length(); a++) {
            l.add(chars.charAt(a));

            assertTrue(l.contains(chars.charAt(a)));
        }

        l.clear();

        assertTrue(l.isEmpty() && l.size() == 0);
    }

    @Test
    public void testEquals() throws Exception {

    }

    @Test
    public void testHashCode() throws Exception {

    }

    @Test
    public final void testSpecialInsert() {
        SkipList<Integer> list = new SkipList<Integer>(20, 0.5);

        long start = System.currentTimeMillis();

        for (int i = 1; i <= 100000; i++) {
            list.add((3*i + i*i) % 123456761);
        }


        System.out.println(System.currentTimeMillis() - start + " ms");

        assertEquals(99985, list.size());

        Thread.yield();

        start = System.currentTimeMillis();

        for (int i = 0; i < 100000; i++) {
            assertTrue(list.contains(100030000));
        }

        System.out.println(System.currentTimeMillis() - start + " ms");

        Thread.yield();
    }

    @Test
    public final void testHeadSet() {
        final SkipList<Integer> set = new SkipList<Integer>();
        set.add(4);
        set.add(2);
        set.add(1);
        set.add(3);
        set.add(6);
        set.add(5);

        final SortedSet<Integer> headSet = set.headSet(4);

        assertEquals(3, headSet.size());

        assertEquals(new Integer(1), headSet.first());
        assertEquals(new Integer(3), headSet.last());
    }

    @Test
    public final void testTailSet() {
        final SkipList<Integer> set = new SkipList<Integer>();
        set.add(4);
        set.add(2);
        set.add(1);
        set.add(3);
        set.add(6);
        set.add(5);
        set.add(7);

        final SortedSet<Integer> tailSet = set.tailSet(4);

        assertEquals(4, tailSet.size());

        assertEquals(new Integer(4), tailSet.first());
        assertEquals(new Integer(7), tailSet.last());
    }

    @Test
    public final void testSubSet() {
        final SkipList<Integer> set = new SkipList<Integer>();
        set.add(4);
        set.add(2);
        set.add(1);
        set.add(3);
        set.add(6);
        set.add(0);
        set.add(5);
        set.add(7);
        set.add(8);
        set.add(9);

        final SortedSet<Integer> subSet = set.subSet(4, 8);

        assertEquals(4, subSet.size());

        assertEquals(new Integer(4), subSet.first());
        assertEquals(new Integer(7), subSet.last());
    }
}