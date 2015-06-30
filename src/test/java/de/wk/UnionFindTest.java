package de.wk;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.wk.util.UnionFind;

public class UnionFindTest {
	UnionFind<Integer> unionFind;

	@Before
	public void setUp() throws Exception {
		List<Integer> universe = new ArrayList<Integer>();
		for (int i = 0; i <= 20; i++) {
			universe.add(i);
		}
		this.unionFind = new UnionFind<Integer>(universe);
	}

	@Test
	public void testInteger() {
		System.out.println(this.unionFind.find(1));
		System.out.println(this.unionFind.find(2));
		System.out.println(this.unionFind.find(3));
		System.out.println(this.unionFind.find(4));
		System.out.println(this.unionFind.find(5));
		System.out.println("####################");
		this.unionFind.union(1,2);
		this.unionFind.union(3,4);
		this.unionFind.union(1,5);
		System.out.println(this.unionFind.find(1));
		System.out.println(this.unionFind.find(2));
		System.out.println(this.unionFind.find(3));
		System.out.println(this.unionFind.find(4));
		System.out.println(this.unionFind.find(5));
		System.out.println("####################");
		System.out.println(this.unionFind.getAllValuesByKey(1));
		System.out.println(this.unionFind.getAllValuesByKey(2));
		System.out.println(this.unionFind.getAllValuesByKey(3));
		System.out.println(this.unionFind.getAllValuesByKey(4));
		System.out.println(this.unionFind.getAllValuesByKey(5));
		System.out.println(this.unionFind.getAllValuesByKey(6));

	}

}
