package de.m_marvin.unimap.impl;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import de.m_marvin.unimap.impl.internal.DualModCollection;
import de.m_marvin.unimap.impl.internal.DualModSet;
import de.m_marvin.unimap.types.BiMap;
import de.m_marvin.unimap.types.MultiMap;

public class ModularBiMap<K, V> extends AbstractMap<K, V> implements BiMap<K, V> {

	private final Map<K, V> k2v;
	private final MultiMap<V, K> v2k;

	public ModularBiMap(Map<K, V> k2vMap, MultiMap<V, K> v2kMap) {
		this.k2v = k2vMap;
		this.v2k = v2kMap;
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

	@SuppressWarnings("unchecked")
	@Override
	public Collection<K> getKeys(Object value) {
		return new DualModCollection<K>(
				this.v2k.getAll(value), 
				k -> this.k2v.put((K) k, (V) value) != value,
				k -> this.k2v.remove(k, value) 
		);
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
	
	@SuppressWarnings("unchecked")
	@Override
	public Set<Entry<K, V>> entrySet() {
		return new DualModSet<Map.Entry<K,V>>(
				this.k2v.entrySet(), 
				e -> this.v2k.put(((Map.Entry<K, V>) e).getValue(), ((Map.Entry<K, V>) e).getKey()) != ((Map.Entry<K, V>) e).getKey(), 
				e -> this.v2k.remove(((Map.Entry<K, V>) e).getValue(), ((Map.Entry<K, V>) e).getKey())
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
		return new DualModCollection<V>(
			this.v2k.keySet(),
			v -> { throw new UnsupportedOperationException("values add element"); },
			v -> this.k2v.values().removeIf(v1 -> Objects.equals(v, v1))
		);
	}

}
