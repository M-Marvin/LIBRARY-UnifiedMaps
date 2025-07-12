package de.m_marvin.unimap.api;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface MultiMap<K, V> extends Map<K, V> {
	
	@Override
	boolean remove(Object key, Object value);
	
	/**
	 * Like {@link Map#get(Object)} returns the value for the supplied key, just that this one returns all mapped values as collection.
	 * @return The list of values mapped to the key.
	 */
	public Collection<V> getAll(Object key);

	/**
	 * Like {@link Map#get(Object)} returns the value for the supplied key, just that this one returns all mapped values as collection.
	 * @param makeIfNull If set to true, if no entry exists for the key, creates an new empty collection which entries can be added to instead of returning null
	 * @return The list of values mapped to the key.
	 */
	public Collection<V> getAll(K key, boolean makeIfNull);

	/**
	 * Like {@link MultiMap#putAll(Object, Collection)} adds the supplied values to the map, just that this one removes all previously mapped values for the key.
	 * @return The list of all previous and now replaced elements.
	 */
	public Collection<V> replace(K key, Collection<V> values);

	/**
	 * Like {@link Map#put(Object, Object)} adds the supplied value to the map, just for a list of values for the same key.
	 * @return True if the entry collection changed as a result of this operation
	 */
	public boolean putAll(K key, Collection<V> values);
	
	/**
	 * Like {@link Map#remove(Object)} removes the value mapped for the key, just that this one removes all values mapped to the key.
	 * @return The list of values mapped to the key which where removed from the map.
	 */
	public Collection<V> removeAll(K key);
	
	/**
	 * Like {@link Map#entrySet()} returns an set of all entries of the map, just that this one returns only one entry per key, with an list of all values as second entry.
	 * @return An set of entries, one for each key.
	 */
	public Set<Map.Entry<K, Collection<V>>> entryValuesSet();
	
}
