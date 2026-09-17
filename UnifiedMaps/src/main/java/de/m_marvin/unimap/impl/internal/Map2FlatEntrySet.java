package de.m_marvin.unimap.impl.internal;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.Supplier;

public class Map2FlatEntrySet<K, V> extends AbstractSet<Map.Entry<K, V>> {
	
	private final Map<K, Set<V>> backingMap;
	private final Supplier<Set<V>> setFactory;
	
	public Map2FlatEntrySet(Map<K, Set<V>> backingSet, Supplier<Set<V>> setFactory) {
		this.backingMap = backingSet;
		this.setFactory = setFactory;
	}
	
	@Override
	public int size() {
		return backingMap.values().stream().mapToInt(Set::size).sum();
	}
	
	@Override
	public boolean add(Map.Entry<K, V> e) {
		Set<V> set = this.backingMap.get(e);
		if (set == null) {
			set = this.setFactory.get();
			set.add(e.getValue());
			this.backingMap.put(e.getKey(), set);
			return true;
		} else {
			return set.add(e.getValue());
		}
	}
	
	@Override
	public boolean remove(Object o) {
		if (o instanceof Map.Entry e) {
			Set<V> set = this.backingMap.get(e.getKey());
			if (set == null)
				return false;
			if (set.remove(e.getValue())) {
				if (set.isEmpty())
					this.backingMap.remove(e.getKey());
				return true;
			}
		}
		return false;
	}
	
	public Iterator<Map.Entry<K, V>> iterator() {
		return new Iterator<Map.Entry<K, V>>() {
			
			private final Iterator<K> keyIter = Map2FlatEntrySet.this.backingMap.keySet().iterator();
			private K key = null;
			private Iterator<V> valueIter = null;
			
			@Override
			public boolean hasNext() {
				if (valueIter != null && valueIter.hasNext()) return true;
				return keyIter.hasNext();
			}
	
			@Override
			public Map.Entry<K, V> next() {
				if (!hasNext()) throw new NoSuchElementException();
				if (valueIter == null || !valueIter.hasNext()) {
					key = keyIter.next();
					valueIter = Map2FlatEntrySet.this.backingMap.get(key).iterator();
				}
				return new Map.Entry<K, V>() {
					private final V value = valueIter.next();
					
					@Override
					public K getKey() {
						return key;
					}
					
					@Override
					public V getValue() {
						return value;
					}
	
					@Override
					public V setValue(V value) {
						throw new UnsupportedOperationException("setValue");
					}
				};
			}
			
			@Override
			public void remove() {
				valueIter.remove();
			}
			
		};
	}

}
