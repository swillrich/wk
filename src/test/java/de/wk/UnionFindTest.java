package de.wk;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import de.wk.util.UnionFind;

public class UnionFindTest {
	UnionFind<Integer> unionFind;

	@Before
	public void setUp() throws Exception {
		Integer[] universe = new Integer[6];
		for (int i = 0; i <= universe.length; i++) {
			universe[i] = i;
		}
		this.unionFind = new UnionFind<Integer>(Arrays.asList(universe));
	}

	@Test
	public void testFindWithSingeElementUnionTrue() {
		this.unionFind.union(1, 2);
		assertTrue(this.unionFind.find(1).equals(this.unionFind.find(2)));

	}

	@Test
	public void testFindWithSingeElementUnionFalse() {
		this.unionFind.union(1, 2);
		assertFalse(this.unionFind.find(1).equals(this.unionFind.find(3)));

	}

	@Test
	public void testFindWithUnionSets() {
		this.unionFind.union(1, 2);
		this.unionFind.union(3, 4);
		this.unionFind.union(1, 3);
		assertTrue(this.unionFind.find(1).equals(this.unionFind.find(3)));
		assertTrue(this.unionFind.find(1).equals(this.unionFind.find(4)));
		assertTrue(this.unionFind.find(2).equals(this.unionFind.find(3)));
		assertTrue(this.unionFind.find(2).equals(this.unionFind.find(4)));

	}

	@Test
	public void testInSameSetMethod() {
		this.unionFind.union(1, 2);
		assertTrue(this.unionFind.inSameSet(1, 2));
		assertFalse(this.unionFind.inSameSet(1, 3));

	}

	@Test
	public void testMethodGetAllValuesByKey() {
		this.unionFind.union(1, 2);
		this.unionFind.union(1, 3);
		this.unionFind.union(2, 4);
		this.unionFind.union(5, 6);
		this.unionFind.union(4, 6);
		ArrayList<Integer> oracle = new ArrayList<Integer>(6);
		oracle.add(1);
		oracle.add(2);
		oracle.add(3);
		oracle.add(4);
		oracle.add(5);
		oracle.add(6);
		assertTrue(this.unionFind.getAllValuesByElement(1).equals(oracle));
	}
}
