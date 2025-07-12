package de.m_marvin.unimap.api;

import java.util.List;

public interface Stack<T> extends List<T> {

	public T peek();
	
	public T pop();
	
	public void push(T value);
	
}
