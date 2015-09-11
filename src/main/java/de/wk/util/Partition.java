package de.wk.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.wk.date.Days;
import de.wk.date.WKDateTime;

public class Partition extends UnionFind<WKDateTime> {

	private Map<WKDateTime, WKDateTime> parent;

	/**
	 * Default constructor.
	 */
	public Partition() {
		this.parent = new HashMap<WKDateTime, WKDateTime>();
	}

	/**
	 * This Constructor gets a list of elements (starting universe). Initially,
	 * every element is its own parent thus it is mapped to itself.
	 * 
	 * @param elements
	 */
	public Partition(List<WKDateTime> elements) {
		this.parent = new HashMap<WKDateTime, WKDateTime>(elements.size());
		for (WKDateTime element : elements) {
			makeSet(element);
		}
	}

	/**
	 * See {@link UnionFind#makeSet(Object) makeSet(Object)}
	 */
	@Override
	public void makeSet(WKDateTime element) {
		this.parent.put(element, element);
	}

	/**
	 * 
	 * @param item
	 * @return the representative of the element
	 */
	public WKDateTime find(WKDateTime element) {
		if (this.parent.get(element) == element) {
			return element;
		} else {
			return find(this.parent.get(element));
		}
	}

	/**
	 * Union two sets in the data structure. TODO: Check for key first in order
	 * a "member" is provided
	 * 
	 * @param set1
	 *            first set to union
	 * @param set2
	 *            second set to union
	 */
	public void union(WKDateTime set1, WKDateTime set2) {
		WKDateTime representative = find(set1);
		WKDateTime representative2 = find(set2);
		parent.put(representative2, representative);
	}

	/**
	 * 
	 * @param element
	 * @return a list of all elements within the set (partition)
	 */
	public Days getAllValuesByElement(WKDateTime element) {
		Days result = new Days();
		WKDateTime representative = find(element);
		for (Map.Entry<WKDateTime, WKDateTime> entry : this.parent.entrySet()) {
			if (representative == find(entry.getValue())) {
				result.add(entry.getKey());
			}
		}
		return result;
	}

	/**
	 * Checks whether two elements are in the same set.
	 * 
	 * @param element1
	 * @param element2
	 * @return true if the given elements are in the same set
	 */
	public boolean inSameSet(WKDateTime element1, WKDateTime element2) {
		return find(element1) == find(element2) ? true : false;
	}
}
