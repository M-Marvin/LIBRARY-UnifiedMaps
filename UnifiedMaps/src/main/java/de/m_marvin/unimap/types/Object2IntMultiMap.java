package de.m_marvin.unimap.types;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.objects.Object2IntMap;

//TODO update comments
public interface Object2IntMultiMap<K> extends MultiMap<K, Integer>, Object2IntMap<K> {
	
	@Override
	boolean remove(Object key, Object value);
	
	/**
	 * Like {@link Map#get(Object)} returns the value for the supplied key, just that this one returns all mapped values as collection.<br>
	 * The returned map is backed by the set in the map, thus modifications to it are reflected on the map and vice versa.
	 * @return The list of values mapped to the key.
	 */
	public IntCollection getAllInt(Object key);

	/**
	 * Like {@link Map#get(Object)} returns the value for the supplied key, just that this one returns all mapped values as collection.<br>
	 * The returned map is backed by the set in the map, thus modifications to it are reflected on the map and vice versa.
	 * @param makeIfNull If set to true, if no entry exists for the key, creates an new empty collection which entries can be added to instead of returning null
	 * @return The list of values mapped to the key.
	 */
	public IntCollection getAllInt(K key, boolean makeIfNull);

	/**
	 * Like {@link MultiMap#putAll(Object, Collection)} adds the supplied values to the map, just that this one removes all previously mapped values for the key.<br>
	 * The returned set is already removed from the map once returned, thus modifications to it are <b>not</b> reflected on the map.
	 * @return The list of all previous and now replaced elements.
	 */
	public IntCollection replaceInt(K key, IntCollection values);

	/**
	 * Like {@link Map#put(Object, Object)} adds the supplied value to the map, just for a list of values for the same key.
	 * @return True if the entry collection changed as a result of this operation
	 */
	public boolean putAllInt(K key, IntCollection values);
	
	/**
	 * Like {@link Map#remove(Object)} removes the value mapped for the key, just that this one removes all values mapped to the key.<br>
	 * The returned set is already removed from the map once returned, thus modifications to it are <b>not</b> reflected on the map.
	 * @return The list of values mapped to the key which where removed from the map.
	 */
	public IntCollection removeAllInt(Object key);
	
	/**
	 * Like {@link Map#entrySet()} returns an set of all entries of the map, just that this one returns only one entry per key, with an list of all values as second entry.
	 * The set is backed by the map, thus modifications are reflected on the map and vice versa.
	 * @return An set of entries, one for each key.
	 */
	public Set<Map.Entry<K, IntCollection>> object2IntEntryValuesSet();
	
}
