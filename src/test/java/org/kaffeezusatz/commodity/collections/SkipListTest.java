package org.kaffeezusatz.commodity.collections;

import org.junit.Test;

import java.util.AbstractSet;
import java.util.Iterator;

import static org.junit.Assert.*;

public class SkipListTest {
    @Test
	public final void testInsert() {
		SkipList<Integer> list = new SkipList<Integer>(6, 0.5);

        assertEquals(0, list.size());

		list.add(1, 3); // 1
		list.add(0, 6); // 0
		list.add(0, 6);
		list.add(2, 3); // 2
		list.add(2, 3);
		list.add(4, 5); // 4
		list.add(4, 5);
		list.add(3, 6); // 3
		list.add(3, 6);
		list.add(3, 6);
		list.add(7, 3); // 7

        assertEquals(6, list.size());
		
		list.add(5, 3); // 5

        assertEquals(7, list.size());

        System.out.println(list.toString());

		list.remove(4);

        System.out.println(list.toString());

        assertEquals(6, list.size());

        list.remove(1);

        System.out.println(list.toString());
    }
	
	//@Test
	public final void testSpecialInsert() {
		SkipList<Integer> list = new SkipList<Integer>(6, 0.5);
		
		for (int i = 1; i < 100000; i++) {
			list.add((3*i + i*i) % 123456761);
		}
		
		System.out.println(list.toString());
	}
}