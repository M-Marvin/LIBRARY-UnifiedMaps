package de.m_marvin.unimap.impl;

import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import de.m_marvin.unimap.api.MultiMap;

public class HashMultiMap<K, V> extends AbstractMap<K, V> implements MultiMap<K, V> {
	
	protected Map<K, HashSet<V>> map;

	public HashMultiMap(int initialCapacity, float loadFactor) {
		this.map = new HashMap<K, HashSet<V>>(initialCapacity, loadFactor);
	}

	public HashMultiMap(int initialCapacity) {
		this.map = new HashMap<K, HashSet<V>>(initialCapacity);
	}
	
	public HashMultiMap() {
		this.map = new HashMap<K, HashSet<V>>();
	}

	public HashMultiMap(Map<K, V> map) {
		this.map = new HashMap<K, HashSet<V>>();
		putAll(map);
	}
	
	@Override
	public Collection<V> getAll(Object key) {
		return this.map.get(key);
	}
	
	@Override
	public Collection<V> getAll(K key, boolean makeIfNull) {
		HashSet<V> values = this.map.get(key);
		if (makeIfNull && values == null) {
			values = new HashSet<V>();
			this.map.put(key, values);
		}
		return values;
	}
	
	@Override
	public Collection<V> replace(K key, Collection<V> values) {
		HashSet<V> entries = new HashSet<V>();
		entries.addAll(values);
		return this.map.put(key, entries);
	}

	@Override
	public boolean putAll(K key, Collection<V> values) {
		return getAll(key, true).addAll(values);
	}

	@Override
	public Collection<V> removeAll(Object key) {
		return this.map.remove(key);
	}

	@Override
	public int size() {
		return entryValuesSet().stream().mapToInt(e -> e.getValue().size()).reduce((a, b) -> {
			if (a == Integer.MAX_VALUE || b == Integer.MAX_VALUE) return Integer.MAX_VALUE;
			int i = a + b;
			return i < 0 ? Integer.MAX_VALUE : i;
		}).orElse(0);
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public boolean containsKey(Object key) {
		return this.map.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		for (Collection<V> entries : this.map.values())
			if (entries.contains(value)) return true;
		return false;
	}

	@Override
	public V get(Object key) {
		Collection<V> entries = this.map.get(key);
		if (entries == null || entries.isEmpty()) return null;
		return entries.iterator().next();
	}

	@Override
	public V put(K key, V value) {
		Collection<V> entries = getAll(key, true);
		entries.add(value);
		return null;
	}
	
	@Override
	public boolean remove(Object key, Object value) {
		Collection<V> entries = this.map.get(key);
		if (entries == null) return false;
		if (entries.isEmpty()) {
			this.map.remove(key);
			return false;
		} else {
			if (entries.remove(value)) {
				if (entries.isEmpty()) {
					this.map.remove(key);
				}
				return true;
			}
			return false;
		}
	}

	@Override
	public V remove(Object key) {
		Collection<V> entries = this.map.get(key);
		if (entries == null) return null;
		if (entries.isEmpty()) {
			this.map.remove(key);
			return null;
		} else {
			V value = entries.iterator().next();
			entries.remove(value);
			if (entries.isEmpty()) {
				this.map.remove(key);
			}
			return value;
		}
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		m.entrySet().forEach(e -> put(e.getKey(), e.getValue()));
	}

	@Override
	public void clear() {
		this.map.clear();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Set<Entry<K, Collection<V>>> entryValuesSet() {
		return (Set<Entry<K, Collection<V>>>) ((Set) this.map.entrySet());
	}
	
	private AbstractSet<Entry<K, V>> cachedEntrySet = null;
	
	@Override
	public Set<Entry<K, V>> entrySet() {
		if (this.cachedEntrySet == null) {
			this.cachedEntrySet = new AbstractSet<Entry<K, V>>() {
				
				public boolean add(Entry<K, V> e) {
					if (containsKey(e.getKey())) {
						V replaced = put(e.getKey(), e.getValue());
						return replaced != e;
					}
					put(e.getKey(), e.getValue());
					return true;
				};
				
				@Override
				public int size() {
					return HashMultiMap.this.size();
				}

				@Override
				public Iterator<Entry<K, V>> iterator() {
					return new Iterator<Entry<K, V>>() {
						
						private final Iterator<K> keyIter = keySet().iterator();
						private K key = null;
						private Iterator<V> valueIter = null;
						
						@Override
						public boolean hasNext() {
							if (valueIter != null && valueIter.hasNext()) return true;
							return keyIter.hasNext();
						}

						@Override
						public Entry<K, V> next() {
							if (!hasNext()) throw new NoSuchElementException();
							if (valueIter == null || !valueIter.hasNext()) {
								key = keyIter.next();
								System.out.println("it key: " + key + " > " + HashMultiMap.this.map.get(key));
								valueIter = HashMultiMap.this.map.get(key).iterator();
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
				
			};
		}
		return this.cachedEntrySet;
	}

	@Override
	public Set<K> keySet() {
		return this.map.keySet();
	}

	private AbstractCollection<V> cachedValueSet = null;
	
	@Override
	public Collection<V> values() {
		if (this.cachedValueSet == null) {
			this.cachedValueSet = new AbstractCollection<V>() {
				
				@Override
				public int size() {
					return HashMultiMap.this.size();
				}

				@Override
				public Iterator<V> iterator() {
					return new Iterator<V>() {
						
						private final Iterator<HashSet<V>> valuesIter = HashMultiMap.this.map.values().iterator();
						private HashSet<V> values = null;
						private Iterator<V> valueIter = null;
						
						@Override
						public boolean hasNext() {
							if (valueIter != null && valueIter.hasNext()) return true;
							return valuesIter.hasNext();
						}

						@Override
						public V next() {
							if (!hasNext()) throw new NoSuchElementException();
							if (valueIter == null || !valueIter.hasNext()) {
								values = valuesIter.next();
								valueIter = values.iterator();
							}
							if (!valueIter.hasNext()) throw new NoSuchElementException();
							return valueIter.next();
						}
						
						@Override
						public void remove() {
							valueIter.remove();
							if (values.isEmpty())
								valuesIter.remove();
						}
						
					};
				}
				
			};
		}
		return this.cachedValueSet;
	}
	
}
