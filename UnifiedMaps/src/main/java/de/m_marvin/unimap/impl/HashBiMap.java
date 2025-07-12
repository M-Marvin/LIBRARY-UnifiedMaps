package de.m_marvin.unimap.impl;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import de.m_marvin.unimap.api.BiMap;
import de.m_marvin.unimap.api.MultiMap;

public class HashBiMap<K, V> extends AbstractMap<K, V> implements BiMap<K, V> {

	private final Map<K, V> k2v;
	private final MultiMap<V, K> v2k;

	public HashBiMap(int initialCapacity, float loadFactor) {
		this.k2v = new HashMap<K, V>(initialCapacity, loadFactor);
		this.v2k = new HashMultiMap<V, K>(initialCapacity, loadFactor);
	}

	public HashBiMap(int initialCapacity) {
		this.k2v = new HashMap<K, V>(initialCapacity);
		this.v2k = new HashMultiMap<V, K>(initialCapacity);
	}

	public HashBiMap() {
		this.k2v = new HashMap<K, V>();
		this.v2k = new HashMultiMap<V, K>();
	}

	public HashBiMap(Map<K, V> map) {
		this.k2v = new HashMap<K, V>();
		this.v2k = new HashMultiMap<V, K>();
		putAll(map);
	}

	@Override
	public int size() {
		return this.k2v.size();
	}

	@Override
	public boolean isEmpty() {
		return this.k2v.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return this.k2v.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return this.k2v.containsValue(value);
	}

	@Override
	public V get(Object key) {
		return this.k2v.get(key);
	}

	@Override
	public V put(K key, V value) {
		this.v2k.put(value, key);
		return this.k2v.put(key, value);
	}

	@Override
	public V remove(Object key) {
		V removed = this.k2v.remove(key);
		this.v2k.remove(removed, key);
		return removed;
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		m.entrySet().forEach(e -> put(e.getKey(), e.getValue()));
	}

	@Override
	public void clear() {
		this.k2v.clear();
		this.v2k.clear();
	}

	@Override
	public K getKey(Object value) {
		return this.v2k.get(value);
	}

	@Override
	public K getKeyOrDefault(Object value, K defaultValue) {
		return this.v2k.getOrDefault(value, defaultValue);
	}

	@Override
	public Collection<K> getKeys(Object value) {
		return this.v2k.getAll(value);
	}

	@Override
	public K removeValue(Object value) {
		K key = this.v2k.remove(value);
		this.k2v.remove(key);
		return key;
	}

	@Override
	public Collection<K> removeValues(V value) {		
		Collection<K> keys = this.v2k.removeAll(value);
		keys.forEach(this.k2v::remove);
		return keys;
	}
	
	private AbstractSet<Entry<K, V>> cachedEntrySet = null;
	
	@Override
	public Set<Entry<K, V>> entrySet() {
		if (this.cachedEntrySet == null) {
			this.cachedEntrySet = new AbstractSet<Map.Entry<K,V>>() {

				@Override
				public boolean add(Entry<K, V> e) {
					if (containsKey(e.getKey())) {
						V replaced = put(e.getKey(), e.getValue());
						return replaced != e.getValue();
					}
					put(e.getKey(), e.getValue());
					return true;
				}
				
				@Override
				public int size() {
					return HashBiMap.this.size();
				}

				@Override
				public Iterator<Entry<K, V>> iterator() {
					return new Iterator<Map.Entry<K,V>>() {

						private final Iterator<Entry<K, V>> entryIter = HashBiMap.this.k2v.entrySet().iterator();
						private final Iterator<Entry<V, K>> entryRevIter = HashBiMap.this.v2k.entrySet().iterator();
						
						@Override
						public boolean hasNext() {
							return entryIter.hasNext() && entryRevIter.hasNext();
						}

						@Override
						public Entry<K, V> next() {
							if (!hasNext()) throw new NoSuchElementException();
							entryRevIter.next();
							return entryIter.next();
						}
						
						@Override
						public void remove() {
							entryIter.remove();
							entryRevIter.remove();
						}
						
					};
				}
				
			};
		}
		return this.cachedEntrySet;
	}

	private AbstractSet<K> cachedKeySet = null;
	
	@Override
	public Set<K> keySet() {
		if (this.cachedKeySet == null) {
			this.cachedKeySet = new AbstractSet<K>() {
				
				@Override
				public int size() {
					return HashBiMap.this.k2v.keySet().size();
				}

				@Override
				public Iterator<K> iterator() {
					return new Iterator<K>() {
						
						private final Iterator<K> keyIter = HashBiMap.this.k2v.keySet().iterator();
						private final Iterator<K> keyRevIter = HashBiMap.this.v2k.values().iterator();
						
						@Override
						public boolean hasNext() {
							return keyIter.hasNext() && keyIter.hasNext();
						}
						
						@Override
						public K next() {
							if (!hasNext()) throw new NoSuchElementException();
							keyRevIter.next();
							return keyIter.next();
						}
						
						@Override
						public void remove() {
							keyIter.remove();
							keyRevIter.remove();
						}
						
					};
				}
				
			};
		}
		return this.cachedKeySet;
	}

	private AbstractSet<V> cachedValueSet = null;
	
	@Override
	public Collection<V> values() {
		if (this.cachedValueSet == null) {
			this.cachedValueSet = new AbstractSet<V>() {
				
				@Override
				public int size() {
					return HashBiMap.this.k2v.values().size();
				}

				@Override
				public Iterator<V> iterator() {
					return new Iterator<V>() {
						
						private final Iterator<V> valueIter = HashBiMap.this.k2v.values().iterator();
						private final Iterator<V> valueRevIter = HashBiMap.this.v2k.keySet().iterator();
						
						@Override
						public boolean hasNext() {
							return valueIter.hasNext() && valueIter.hasNext();
						}
						
						@Override
						public V next() {
							if (!hasNext()) throw new NoSuchElementException();
							valueRevIter.next();
							return valueIter.next();
						}
						
						@Override
						public void remove() {
							valueIter.remove();
							valueRevIter.remove();
						}
						
					};
				}
				
			};
		}
		return this.cachedValueSet;
	}

}
