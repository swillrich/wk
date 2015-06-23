package de.wk.util;

import java.util.ArrayList;

/**
 * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/util/ArrayList.html">ArrayList</a>
 * Utility class to realize a simple priority queue to store lists of elements with certain priorities.
 * The actual priority queue is represented as java.util.ArrayList internally.
 * 
 *
 * @param T generic type of the elements that are stored within the queue
 */
public class PriorityQueue<T>{
    private int numberOfPriorities;
    private ArrayList<ArrayList<T>> prioQueue;
    
    /**
     * 
     * @param numberOfPriorities determine number of different priorities
     */
    public PriorityQueue(int numberOfPriorities){
        this.numberOfPriorities = numberOfPriorities;
        this.prioQueue = new ArrayList<ArrayList<T>>(numberOfPriorities);
        initQueue();
    }
    
    /**
     * Initialize the priority queue by creating a new ArrayList for 
     * each priority.
     */
    private void initQueue(){
        for(int i=0; i<this.numberOfPriorities; i++){
            this.prioQueue.add(i, new ArrayList<T>());
        }
    }
    
    /**
     * Give all elements with a given priority.
     * 
     * @param priority the priority
     * @return ArrayList<T> list of elements with given priority
     */
    public ArrayList<T> getAllElementsByPriority(int priority){
        ArrayList<T> tmp = this.prioQueue.get(priority);
        return new ArrayList<T>(tmp);
    }
    
    /**
     * Add a new element with a given priority.
     * 
     * @param priority priority of the element
     * @param element element to add
     * @return boolean true if element was added successfully
     */
    public boolean addElementWithPriority(int priority, T element){
        ArrayList<T> tmp = this.prioQueue.get(priority);
        return tmp.add(element);
    }
}

