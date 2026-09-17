package de.m_marvin.unimap.impl;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import de.m_marvin.unimap.impl.internal.DualModCollection;
import de.m_marvin.unimap.impl.internal.DualModSet;
import de.m_marvin.unimap.types.MultiBiMap;
import de.m_marvin.unimap.types.MultiMap;

public class ModularMultiBiMap<K, V>extends AbstractMap<K, V> implements MultiBiMap<K, V> {
	
	private MultiMap<K, V> k2v;
	private MultiMap<V, K> v2k;
	
	public ModularMultiBiMap(MultiMap<K, V> k2vMap, MultiMap<V, K> v2kMap) {
		 this.k2v = k2vMap;
		 this.v2k = v2kMap;
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
	@SuppressWarnings("unchecked")
	public Collection<K> getKeys(Object value) {
		return new DualModCollection<K>(
				this.v2k.getAll(value), 
				k -> this.k2v.put((K) k, (V) value) != value, 
				k -> this.k2v.remove(k, value)
		);
	}

	@Override
	public K removeValue(V value) {
		K key = this.v2k.remove(value);
		this.k2v.remove(key, value);
		return key;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<K> removeValues(V value) {
		Collection<K> keys = this.v2k.removeAll(value);
		keys.forEach(key -> this.k2v.remove(key, value));
		return new DualModCollection<K>(
				keys,
				k -> this.k2v.put((K) k, (V) value) != value, 
				k -> this.k2v.remove(k, value)
		);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<V> getAll(Object key) {
		return new DualModCollection<V>(
				this.k2v.getAll(key), 
				v -> this.v2k.put((V) v, (K) key) != key, 
				v -> this.v2k.remove(v, key)
		);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<V> getAll(K key, boolean makeIfNull) {
		return new DualModCollection<V>(
				this.k2v.getAll(key, makeIfNull), 
				v -> this.v2k.put((V) v, (K) key) != key, 
				v -> this.v2k.remove(v, key)
		);
	}
	
	@Override
	public Collection<V> replace(K key, Collection<V> values) {
		Collection<V> replaced = this.k2v.replace(key, values);
		replaced.forEach(v -> this.v2k.remove(v, key));
		values.forEach(v -> this.v2k.put(v, key));
		return replaced;
	}

	@Override
	public boolean putAll(K key, Collection<V> values) {
		return getAll(key, true).addAll(values);
	}

	@Override
	public Collection<V> removeAll(Object key) {
		Collection<V> removed = this.k2v.removeAll(key);	
		removed.forEach(v -> this.v2k.remove(v, key));
		return removed;
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
		return this.k2v.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return this.v2k.containsKey(value);
	}

	@Override
	public V get(Object key) {
		return this.k2v.get(key);
	}

	@Override
	public V put(K key, V value) {
		V replaced = this.k2v.put(key, value);
		this.v2k.put(value, key);
		return replaced;
	}
	
	@Override
	public boolean remove(Object key, Object value) {
		return this.k2v.remove(key, value) || this.v2k.remove(value, key);
	}

	@Override
	public V remove(Object key) {
		V removed = this.k2v.remove(key);
		this.v2k.remove(removed, key);
		return removed;
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		m.forEach(this::put);
	}

	@Override
	public void clear() {
		this.k2v.clear();
		this.v2k.clear();
	}

	@SuppressWarnings({"unchecked" })
	@Override
	public Set<Entry<K, Collection<V>>> entryValuesSet() {
		return new DualModSet<Map.Entry<K,Collection<V>>>(
				this.k2v.entryValuesSet(), 
				e -> ((Map.Entry<K,Collection<V>>) e).getValue().stream()
					.map(v -> this.v2k.put(v, ((Map.Entry<K,Collection<V>>) e).getKey()) != ((Map.Entry<K,Collection<V>>) e).getKey())
					.reduce(Boolean::logicalOr).orElseGet(() -> false),
				e -> ((Map.Entry<K, Collection<V>>) e).getValue().stream()
					.map(v -> this.v2k.remove(v, ((Map.Entry<K, Collection<V>>) e).getKey()))
					.reduce(Boolean::logicalOr).orElseGet(() -> false)
		) {
			
			@Override
			public Iterator<Entry<K, Collection<V>>> iterator() {
				return new Iterator<Entry<K, Collection<V>>>() {
					
					private Iterator<Entry<K, Collection<V>>> iter = backing.iterator();
					private Entry<K, Collection<V>> current;
					
					@Override
					public boolean hasNext() {
						return this.iter.hasNext();
					}

					@Override
					public Entry<K, Collection<V>> next() {
						return new Entry<K, Collection<V>>() {
							
							Entry<K, Collection<V>> entry = current = iter.next();
							
							@Override
							public K getKey() {
								return this.entry.getKey();
							}

							@Override
							public Collection<V> getValue() {
								return new DualModCollection<V>(
										this.entry.getValue(), 
										v -> ModularMultiBiMap.this.k2v.put(entry.getKey(), (V) v) != v,
										v -> ModularMultiBiMap.this.k2v.remove(entry.getKey(), (V) v)
								);
							}

							@Override
							public Collection<V> setValue(Collection<V> value) {
								return this.entry.setValue(value);
							}
							
						};
					}
					
					@Override
					public void remove() {
						this.iter.remove();
						removeMod.apply(this.current);
					}
					
				};
			}
			
		};
	}
	
	@Override
	public Set<Entry<K, V>> entrySet() {
		return new DualModSet<>(
				this.k2v.entrySet(), 
				k -> { throw new UnsupportedOperationException("entrySet add element"); }, 
				k -> this.v2k.values().removeIf(k1 -> Objects.equals(k, k1))
		);
	}

	@Override
	public Set<K> keySet() {
		return new DualModSet<K>(
				this.k2v.keySet(), 
				k -> { throw new UnsupportedOperationException("keySet add element"); },
				k -> this.v2k.values().removeIf(k1 -> Objects.equals(k, k1))
		);
	}
	
	@Override
	public Collection<V> values() {
		return new DualModCollection<>(
				this.v2k.keySet(),
				v -> { throw new UnsupportedOperationException("values add element"); },
				v -> this.k2v.values().removeIf(v1 -> Objects.equals(v, v1))
		);
	}

}
