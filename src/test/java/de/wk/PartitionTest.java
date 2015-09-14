package de.wk;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import de.wk.date.Days;
import de.wk.date.WKDateTime;
import de.wk.date.WKInterval;
import de.wk.util.Partitions;

public class PartitionTest {
	Partitions partition;
	WKDateTime wkDate1 = new WKDateTime(2015, 9, 11);
	WKDateTime wkDate2 = new WKDateTime(2015, 9, 12);
	WKDateTime wkDate3 = new WKDateTime(2015, 9, 13);
	WKDateTime wkDate4 = new WKDateTime(2015, 9, 14);
	WKDateTime wkDate5 = new WKDateTime(2015, 9, 15);
	WKDateTime wkDate6 = new WKDateTime(2015, 9, 16);
	WKDateTime wkDate7 = new WKDateTime(2015, 9, 17);
	WKDateTime wkDate8 = new WKDateTime(2015, 9, 18);
	WKDateTime wkDate9 = new WKDateTime(2015, 9, 19);
	WKDateTime wkDate10 = new WKDateTime(2015, 9, 20);
	WKInterval wkInterval = new WKInterval(wkDate1, wkDate10);

	@Before
	public void setUp() throws Exception {
		this.partition = new Partitions(wkInterval);
	}

	@Test
	public void testFindWithSingleElementUnionTrue() {
		this.partition.union(wkDate1, wkDate2);
		assertTrue(this.partition.find(wkDate1).equals(
				this.partition.find(wkDate2)));

	}

	@Test
	public void testFindWithSingeElementUnionFalse() {
		this.partition.union(wkDate1, wkDate2);
		assertFalse(this.partition.find(wkDate1).equals(
				this.partition.find(wkDate3)));

	}

	@Test
	public void testFindWithUnionSets() {
		this.partition.union(wkDate1, wkDate2);
		this.partition.union(wkDate3, wkDate4);
		this.partition.union(wkDate1, wkDate3);
		assertTrue(this.partition.find(wkDate1).equals(
				this.partition.find(wkDate3)));
		assertTrue(this.partition.find(wkDate1).equals(
				this.partition.find(wkDate4)));
		assertTrue(this.partition.find(wkDate2).equals(
				this.partition.find(wkDate3)));
		assertTrue(this.partition.find(wkDate2).equals(
				this.partition.find(wkDate4)));

	}

	@Test
	public void testInSameSetMethod() {
		this.partition.union(wkDate1, wkDate2);
		assertTrue(this.partition.inSameSet(wkDate1, wkDate2));
		assertFalse(this.partition.inSameSet(wkDate1, wkDate3));

	}

	@Test
	public void testMethodGetAllValuesByKey() {
		this.partition.union(wkDate1, wkDate2);
		this.partition.union(wkDate1, wkDate3);
		this.partition.union(wkDate2, wkDate4);
		this.partition.union(wkDate5, wkDate6);
		this.partition.union(wkDate4, wkDate6);
		Days oracle = new Days();
		oracle.add(wkDate1);
		oracle.add(wkDate2);
		oracle.add(wkDate3);
		oracle.add(wkDate4);
		oracle.add(wkDate5);
		oracle.add(wkDate6);
		assertTrue(this.partition.getAllValuesByElement(wkDate1).containsAll(
				oracle));
	}

	@Test
	public void testSearchByEqualDateWithDiffRef() {
		WKDateTime wkDate3 = new WKDateTime(2015, 9, 13);
		WKDateTime wkDateTime = this.partition.find(wkDate3);
		assertTrue(wkDate3.compareTo(wkDateTime) == 0);
	}
}