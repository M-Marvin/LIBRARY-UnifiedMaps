package de.m_marvin.unimap.impl;

import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

import de.m_marvin.unimap.api.BiMap;

public class ArrayBiMap<K, V> extends AbstractMap<K, V> implements BiMap<K, V> {

	protected final List<Object> entries;
	
	public ArrayBiMap() {
		this.entries = new ArrayList<Object>();
	}

	public ArrayBiMap(Map<K, V> map) {
		this.entries = new ArrayList<Object>();
		putAll(map);
	}
	
	@Override
	public int size() {
		return this.entries.size() / 2;
	}

	@Override
	public boolean isEmpty() {
		return this.entries.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		for (int i = 0; i < entries.size(); i += 2)
			if (Objects.equals(entries.get(i), key)) return true;
		return false;
	}

	@Override
	public boolean containsValue(Object value) {
		for (int i = 0; i < entries.size(); i += 2)
			if (Objects.equals(entries.get(i + 1), value)) return true;
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public V get(Object key) {
		for (int i = 0; i < entries.size(); i += 2)
			if (Objects.equals(entries.get(i), key)) return (V) entries.get(i + 1);
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public K getKey(Object value) {
		for (int i = 0; i < entries.size(); i += 2)
			if (Objects.equals(entries.get(i + 1), value)) return (K) entries.get(i);
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public K getKeyOrDefault(Object value, K defaultValue) {
		for (int i = 0; i < entries.size(); i += 2)
			if (Objects.equals(entries.get(i + 1), value)) return (K) entries.get(i);
		return defaultValue;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<K> getKeys(Object value) {
		List<K> keys = new ArrayList<>();
		for (int i = 0; i < entries.size(); i += 2)
			if (Objects.equals(entries.get(i + 1), value)) keys.add((K) entries.get(i));
		return Collections.unmodifiableCollection(keys);
	}

	@SuppressWarnings("unchecked")
	@Override
	public V put(K key, V value) {
		for (int i = 0; i < entries.size(); i += 2)
			if (Objects.equals(entries.get(i), key)) {
				V replaced = (V) entries.get(i + 1);
				entries.set(i + 1, value);
				return replaced;
			}
		entries.add(key);
		entries.add(value);
		return null;
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		m.entrySet().forEach(e -> put(e.getKey(), e.getValue()));
	}

	@SuppressWarnings("unchecked")
	@Override
	public V remove(Object key) {
		for (int i = 0; i < entries.size(); i += 2)
			if (Objects.equals(entries.get(i), key)) {
				V removed = (V) entries.remove(i + 1);
				entries.remove(i);
				return removed;
			}
		return null;
	}

	@Override
	public boolean remove(Object key, Object value) {
		for (int i = 0; i < entries.size(); i += 2)
			if (Objects.equals(entries.get(i), key) && Objects.equals(entries.get(i + 1), value)) {
				entries.remove(i + 1);
				entries.remove(i);
				return true;
			}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public K removeValue(V value) {
		for (int i = 0; i < entries.size(); i += 2)
			if (Objects.equals(entries.get(i + 1), value)) {
				K removed = (K) entries.get(i);
				entries.remove(i + 1);
				entries.remove(i);
				return removed;
			}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<K> removeValues(V value) {
		List<K> keys = new ArrayList<>();
		for (int i = 0; i < entries.size(); i += 2)
			if (Objects.equals(entries.get(i + 1), value)) {
				keys.add((K) entries.get(i));
				entries.remove(i + 1);
				entries.remove(i);
				i--;
			}
		return Collections.unmodifiableCollection(keys);
	}

	@Override
	public void clear() {
		this.entries.clear();
	}
	
	private AbstractSet<K> cachedKeysSet = null;
	
	@Override
	public Set<K> keySet() {
		if (this.cachedKeysSet == null) {
			this.cachedKeysSet = new AbstractSet<K>() {
				
				@Override
				public int size() {
					return ArrayBiMap.this.size();
				}

				@Override
				public Iterator<K> iterator() {
					return new Iterator<K>() {
						
						private int index = -2;

						@Override
						public void remove() {
							ArrayBiMap.this.entries.remove(index + 1);
							ArrayBiMap.this.entries.remove(index);
						}
						
						@Override
						public boolean hasNext() {
							return this.index < ArrayBiMap.this.entries.size() - 2;
						}

						@SuppressWarnings("unchecked")
						@Override
						public K next() {
							if (!hasNext()) throw new NoSuchElementException();
							index += 2;
							return (K) ArrayBiMap.this.entries.get(this.index);
						}
						
					};
				}
				
			};
		}
		return this.cachedKeysSet;
	}

	private AbstractCollection<V> cachedValuesCollection = null;
	
	@Override
	public Collection<V> values() {
		if (this.cachedValuesCollection == null) {
			this.cachedValuesCollection = new AbstractCollection<V>() {
				
				@Override
				public int size() {
					return ArrayBiMap.this.size();
				}

				@Override
				public Iterator<V> iterator() {
					return new Iterator<V>() {
						
						private int index = -2;

						@Override
						public void remove() {
							ArrayBiMap.this.entries.remove(index + 1);
							ArrayBiMap.this.entries.remove(index);
						}
						
						@Override
						public boolean hasNext() {
							return this.index < ArrayBiMap.this.entries.size() - 2;
						}

						@SuppressWarnings("unchecked")
						@Override
						public V next() {
							if (!hasNext()) throw new NoSuchElementException();
							index += 2;
							return (V) ArrayBiMap.this.entries.get(this.index);
						}
						
					};
				}
				
			};
		}
		return this.cachedValuesCollection;
	}

	private AbstractSet<Entry<K, V>> cachedEntrySet = null;
	
	@Override
	public Set<Entry<K, V>> entrySet() {
		if (this.cachedEntrySet == null) {
			this.cachedEntrySet = new AbstractSet<Map.Entry<K,V>>() {
				
				@Override
				public boolean add(Entry<K, V> e) {
					if (e.getValue() == null && !containsValue(null)) {
						put(e.getKey(), e.getValue());
						return true;
					}
					return e.getValue() != put(e.getKey(), e.getValue());
				}
				
				@Override
				public int size() {
					return ArrayBiMap.this.size();
				}

				@Override
				public Iterator<Entry<K, V>> iterator() {
					return new Iterator<Map.Entry<K,V>>() {
						
						private int index = -2;

						@Override
						public void remove() {
							ArrayBiMap.this.entries.remove(index + 1);
							ArrayBiMap.this.entries.remove(index);
						}
						
						@Override
						public boolean hasNext() {
							return this.index < ArrayBiMap.this.entries.size() - 2;
						}

						@Override
						public Entry<K, V> next() {
							if (!hasNext()) throw new NoSuchElementException();
							index += 2;
							return new Map.Entry<K, V>() {
								private int entryIndex = index;
								
								@SuppressWarnings("unchecked")
								@Override
								public K getKey() {
									return (K) ArrayBiMap.this.entries.get(this.entryIndex);
								}
								
								@SuppressWarnings("unchecked")
								@Override
								public V getValue() {
									return (V) ArrayBiMap.this.entries.get(this.entryIndex + 1);
								}

								@Override
								public V setValue(V value) {
									V replaced = getValue();
									ArrayBiMap.this.entries.set(this.entryIndex + 1, value);
									return replaced;
								}
							};
						}
						
					};
				}
				
			};
		}
		return this.cachedEntrySet;
	}

}
