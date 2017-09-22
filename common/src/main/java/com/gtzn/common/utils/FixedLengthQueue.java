package com.gtzn.common.utils;

import java.util.concurrent.LinkedBlockingQueue;

public class FixedLengthQueue<T> {
	LinkedBlockingQueue<T> queque;
	T last;
	public LinkedBlockingQueue<T> getQueque() {
		return queque;
	}

	public void setQueque(LinkedBlockingQueue<T> queque) {
		this.queque = queque;
	}

	public T getLast() {
		return last;
	}

	public void setLast(T last) {
		this.last = last;
	}

	int queueLength ;
	public FixedLengthQueue(int length){
		this.queueLength = length;
		queque = new LinkedBlockingQueue<T>(length);
	}
	
	public boolean add(T e){
		if(queueLength==queque.size()){
			queque.remove();
		}
		last = e;
		return queque.add(e);
	}
	
	public Object remove(){
		return queque.remove();
	}
	
	public void clear(){
		 queque.clear();
	}
	
	public LinkedBlockingQueue<T>  getQueue(){
		return queque;
	}
	
	public T peek(){
		return queque.peek();
	}
	
	
	
}