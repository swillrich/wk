package de.wk.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is an implementation of a tree based union find data structure (disjoint set).
 * 
 * 
 * @author Sascha
 *
 * @param <T> generic type of elements we want to store
 */
public class UnionFind<T> {
	
	private Map<T, T> parent;
	
	/**
	 * This Constructor gets a list of elements (starting universe).
	 * Initially, every element is its own parent thus it is mapped to itself.
	 * 
	 * @param elements
	 */
	public UnionFind(List<T> elements){
		this.parent = new HashMap<T, T>(elements.size());
		for (T t : elements) {
			this.parent.put(t, t);
		}
	}
	
	/**
	 * 
	 * @param item
	 * @return the representative of the element
	 */
	public T find(T element) {
		if(this.parent.get(element) == element){
			return element;
		}
		else {
			return find(this.parent.get(element));
		}
	}
	
	/**
	 * Union two sets in the data structure.
	 * TODO: Check for key first in order a "member" is provided
	 * 
	 * @param set1 first set to union
	 * @param set2 second set to union
	 */
	public void union(T set1, T set2){
		parent.put(set2, set1);
	}
	
	/**
	 * 
	 * @param element
	 * @return a list of all elements within the set (partition)
	 */
	public ArrayList<T> getAllValuesByKey(T element) {
		ArrayList<T> result = new ArrayList<T>();
		T representative = find(element);
		for(Map.Entry<T, T> entry : this.parent.entrySet()){
			if(representative == find(entry.getValue())){
				result.add(entry.getKey()); 
			}
		}
		return result;
	}

}
