package org.hackrspace.commons.collections.mergelist;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

public class TestMergeList extends TestCase {
	public void testIsSorted() {
		List<Integer> l1 = new ArrayList<Integer>();
		List<Integer> l2 = new ArrayList<Integer>();

		l1.add(9);
		l1.add(2);
		l1.add(4);
		l1.add(6);
		l1.add(8);

		l2.add(1);
		l2.add(9);
		l2.add(5);

		try {
			new MergeList<Integer>().add(l1).add(l2).add(l1).add(l2);
		} catch (RuntimeException re) {
			return;
		}

		fail();
	}

	public void testIterator() {
		List<Integer> l1 = new ArrayList<Integer>();
		List<Integer> l2 = new ArrayList<Integer>();

		l1.add(0);
		l1.add(2);
		l1.add(4);
		l1.add(6);
		l1.add(8);

		l2.add(1);
		l2.add(3);
		l2.add(5);

		List<Integer> result = new ArrayList<Integer>();
		result.add(0);
		result.add(0);
		result.add(1);
		result.add(1);
		result.add(2);
		result.add(2);
		result.add(3);
		result.add(3);
		result.add(4);
		result.add(4);
		result.add(5);
		result.add(5);
		result.add(6);
		result.add(6);
		result.add(8);
		result.add(8);

		MergeList<Integer> m;

		m = new MergeList<Integer>().add(l1).add(l2).add(l1).add(l2);

		List<Integer> compareTo = new ArrayList<Integer>();
		for (Integer i : m) {
			compareTo.add(i);
		}

		assertTrue(result.equals(compareTo));
	}

	public void testIteratorString() {
		List<String> l1 = new ArrayList<String>();
		List<String> l2 = new ArrayList<String>();

		l1.add("a");
		l1.add("d");

		l2.add("blah");
		l2.add("fuck");
		l2.add("zende");

		MergeList<String> m;

		m = new MergeList<String>().add(l1).add(l2).add(l1).add(l2);

		String res = "aablahblahddfuckfuckzendezende";

		StringBuilder toCompare = new StringBuilder();

		for (String i : m) {
			toCompare.append(i);
		}

		assertEquals(res, toCompare.toString());
	}

}
