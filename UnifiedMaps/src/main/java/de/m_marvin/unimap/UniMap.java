package de.m_marvin.unimap;

import java.util.HashMap;
import java.util.Map;

import de.m_marvin.unimap.impl.ArrayBiMap;
import de.m_marvin.unimap.impl.HashBiMap;
import de.m_marvin.unimap.impl.HashMultiBiMap;
import de.m_marvin.unimap.impl.HashMultiMap;

public class UniMap {
	
	private UniMap() {}
	
	public static <K, V> HashMap<K, V> newHashMap() {
		return new HashMap<K, V>();
	}
	
	public static <K, V> HashBiMap<K, V> newHashBiMap() {
		return new HashBiMap<K, V>();
	}
	
	public static <K, V> HashMultiMap<K, V> newHashMultiMap() {
		return new HashMultiMap<K, V>();
	}
	
	public static <K, V> HashMultiBiMap<K, V> newHashMultiBiMap() {
		return new HashMultiBiMap<K, V>();
	}
	
	public static <K, V> ArrayBiMap<K, V> newArrayBiMap() {
		return new ArrayBiMap<K, V>();
	}

	public static <K, V> HashMap<K, V> newHashMap(Map<K, V> map) {
		return new HashMap<K, V>(map);
	}
	
	public static <K, V> HashBiMap<K, V> newHashBiMap(Map<K, V> map) {
		return new HashBiMap<K, V>(map);
	}
	
	public static <K, V> HashMultiMap<K, V> newHashMultiMap(Map<K, V> map) {
		return new HashMultiMap<K, V>(map);
	}
	
	public static <K, V> HashMultiBiMap<K, V> newHashMultiBiMap(Map<K, V> map) {
		return new HashMultiBiMap<K, V>(map);
	}
	
	public static <K, V> ArrayBiMap<K, V> newArrayBiMap(Map<K, V> map) {
		return new ArrayBiMap<K, V>(map);
	}
	
}
