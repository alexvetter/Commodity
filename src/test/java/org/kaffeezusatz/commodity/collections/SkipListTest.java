package org.kaffeezusatz.commodity.collections;

import org.junit.Test;

public class SkipListTest {
    @Test
	public final void testInsert() {
		SkipList<Integer> list = new SkipList<Integer>(6, 0.5);
		
		list.insert(1, 3);
		list.insert(0, 6);
		list.insert(2, 3);
		list.insert(4, 5);
		list.insert(3, 6);
		list.insert(3, 6);
		
		list.insert(5, 3);
		
		System.out.println(list.toString());
		
		list.delete(4);		
		System.out.println(list.toString());
		
		list.delete(1);
		System.out.println(list.toString());
		
		list.delete(0);
		System.out.println(list.toString());
	}
	
	@Test
	public final void testSpecialInsert() {
		SkipList<Integer> list = new SkipList<Integer>(6, 0.5);
		
		for (int i = 1; i < 100000; i++) {
			list.insert((3*i + i*i) % 123456761);
		}
		
		System.out.println(list.toString());
	}
}