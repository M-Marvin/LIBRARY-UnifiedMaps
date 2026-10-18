package de.m_marvin.unimap.types;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface MultiMap<K, V> extends Map<K, V> {
	
	@Override
	boolean remove(Object key, Object value);
	
	/**
	 * Like {@link Map#get(Object)} returns the value for the supplied key, just that this one returns all mapped values as collection.<br>
	 * The returned map is backed by the set in the map, thus modifications to it are reflected on the map and vice versa.
	 * @return The list of values mapped to the key, never null but may be empty
	 */
	public Collection<V> getAll(Object key);

	/**
	 * Like {@link MultiMap#putAll(Object, Collection)} adds the supplied values to the map, just that this one removes all previously mapped values for the key.<br>
	 * The returned set is already removed from the map once returned, it will be unmodifiable.
	 * @return The list of all previous and now replaced elements, never null but may be empty
	 */
	public Collection<V> replace(K key, Collection<V> values);

	/**
	 * Like {@link Map#put(Object, Object)} adds the supplied value to the map, just for a list of values for the same key.
	 * @return True if the entry collection changed as a result of this operation
	 */
	public boolean putAll(K key, Collection<V> values);
	
	/**
	 * Like {@link Map#remove(Object)} removes the value mapped for the key, just that this one removes all values mapped to the key.<br>
	 * The returned set is already removed from the map once returned, it will be unmodifiable.
	 * @return The list of values mapped to the key which where removed from the map, never null but may be empty
	 */
	public Collection<V> removeAll(Object key);
	
	/**
	 * Like {@link Map#entrySet()} returns an set of all entries of the map, just that this one returns only one entry per key, with an list of all values as second entry.
	 * The set is backed by the map, thus modifications are reflected on the map and vice versa.
	 * @return An set of entries, one for each key
	 */
	public Set<Map.Entry<K, Collection<V>>> entryValuesSet();
	
}
