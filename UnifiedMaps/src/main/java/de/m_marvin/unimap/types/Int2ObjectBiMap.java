package de.m_marvin.unimap.types;

import java.util.Map;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.IntCollection;

// TODO update comments
/**
 * A extension of the {@link Map} which allows to retrieve keys by their values.
 * @param <K> The type of the keys
 * @param <V> The type of the values
 */
public interface Int2ObjectBiMap<V> extends BiMap<Integer, V>, Int2ObjectMap<V> {
	
	/**
	 * Returns the key mapped to the supplied value.<br>
	 * More specific, returns the first key that is mapped to the supplied value that is found, if a consistent order is used depends on the implementation of the map.<br>
	 * This method should only be used if the map is guaranteed to contain at most one matching key value value mapping.
	 * 
	 * @param value The value to retrieve the key for
	 * @return The key which is mapped to the value
	 */
	public int getIntKey(Object value);

	/**
	 * Returns the key mapped to the supplied value.<br>
	 * More specific, returns the first key that is mapped to the supplied value that is found, if a consistent order is used depends on the implementation of the map.<br>
	 * This method should only be used if the map is guaranteed to contain at most one matching key value value mapping.
	 * 
	 * @param value The value to retrieve the key for
	 * @return The key which is mapped to the value
	 */
	public int getIntKeyOrDefault(Object value, int defaultValue);
	
	/**
	 * Returns all keys mapped to the supplied value.
	 * 
	 * @param value The value to retrieve the keys for
	 * @return An set of all keys which are mapped to the value, never null but might be empty
	 */
	public IntCollection getIntKeys(Object value);

	/**
	 * Removes the key value mapping which's value is equal to the supplied value.<br>
	 * More specific, removes the first mapping which matches the value, if a consistent order is used depends on the implementation of the map.
	 * This method should only be used if the map is guaranteed to contain at most one matching key value value mapping.
	 * 
	 * @param value The value to remove the mapping for
	 * @return The key that was mapped to the value
	 */
	public int removeValueInt(V value);
	
	/**
	 * Removes all key value mappings which's value is equal to the supplied value.
	 * 
	 * @param value The value to remove the mappings for
	 * @return A set of keys that where mapped to the value
	 */
	public IntCollection removeValuesInt(V value);
	
}
