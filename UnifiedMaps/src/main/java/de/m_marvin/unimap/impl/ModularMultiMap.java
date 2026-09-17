package de.m_marvin.unimap.impl;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

import de.m_marvin.unimap.impl.internal.Map2FlatEntrySet;
import de.m_marvin.unimap.impl.internal.SetCollection2FlatCollection;
import de.m_marvin.unimap.types.MultiMap;

public class ModularMultiMap<K, V> extends AbstractMap<K, V> implements MultiMap<K, V> {
	
	protected Map<K, Set<V>> map;
	protected Supplier<Set<V>> setFactory;

	public ModularMultiMap(Map<K, Set<V>> map, Supplier<Set<V>> setFactory) {
		this.map = map;
		this.setFactory = setFactory;
	}
	
	@Override
	public Collection<V> getAll(Object key) {
		return this.map.get(key);
	}
	
	@Override
	public Collection<V> getAll(K key, boolean makeIfNull) {
		Set<V> values = this.map.get(key);
		if (makeIfNull && values == null) {
			values = this.setFactory.get();
			this.map.put(key, values);
		}
		return values;
	}
	
	@Override
	public Collection<V> replace(K key, Collection<V> values) {
		Set<V> entries = this.setFactory.get();
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
	
	@Override
	public Set<Entry<K, V>> entrySet() {
		return new Map2FlatEntrySet<K, V>(
				this.map, 
				this.setFactory
		);
	}

	@Override
	public Set<K> keySet() {
		return this.map.keySet();
	}
	
	@Override
	public Collection<V> values() {
		return new SetCollection2FlatCollection<V>(
				this.map.values()
		);
	}
	
}
