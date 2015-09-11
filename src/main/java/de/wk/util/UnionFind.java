package de.wk.util;

/**
 * Extend this class to implement an union find data structure. It only contains
 * the three basic operations for an union find data structure inspired by the
 * original paper of Tarjan
 * "A linear-time algorithm for a special case of disjoint set union" from 1983.
 * 
 * @param <T>
 *            generic type of elements we want to store
 */
public abstract class UnionFind<T> {

	/**
	 * Create a new singleton set {element} whose parent is element. This
	 * operation is only allowed if element is in no existing set.
	 *
	 * @param element
	 */
	public abstract void makeSet(T element);

	/**
	 * Return the name of the set containing element. The name of the set is
	 * equal to its parent.
	 *
	 * @param element
	 * @return Parent of set containing element.
	 */
	public abstract T find(T element);

	/**
	 * Create a new set that is the union of the sets containing element1 and
	 * element2. The name of the new set depends on the strategy of the methods.
	 * This operation destroys the old sets containing element1 and element2.
	 *
	 * @param element1
	 * @param element2
	 */
	public abstract void union(T element1, T element2);
}
