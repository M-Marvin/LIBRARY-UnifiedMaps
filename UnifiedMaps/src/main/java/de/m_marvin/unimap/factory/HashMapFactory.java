package de.m_marvin.unimap.factory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HashMapFactory extends MapFactory {
	
	private final int initialCapacity;
	private final float loadFactor;
	
	public HashMapFactory(int initialCapacity, float loadFactor) {
		this.initialCapacity = initialCapacity;
		this.loadFactor = loadFactor;
	}
	
	public static final HashMapFactory DEFAULT = new HashMapFactory(16, 0.75F);
	public static final HashMapFactory FAST = new HashMapFactory(16, 0.50F);
	public static final HashMapFactory VERY_FAST = new HashMapFactory(16, 0.25F);
	
	public static HashMapFactory defaultMaps() {
		return DEFAULT;
	}
	
	public static HashMapFactory fastMaps() {
		return FAST;
	}
	
	public static HashMapFactory veryFastMaps() {
		return VERY_FAST;
	}
	
	public int getInitialCapacity() {
		return initialCapacity;
	}
	
	public float getLoadFactor() {
		return loadFactor;
	}
	
	@Override
	public <E> List<E> newList() {
		return new ArrayList<E>(this.initialCapacity); // no hash specific implementation
	}

	@Override
	public <E> Set<E> newSet() {
		return new HashSet<E>(this.initialCapacity, this.loadFactor);
	}

	@Override
	public <K, V> Map<K, V> newMap() {
		return new HashMap<K, V>(this.initialCapacity, this.loadFactor);
	}
	
}
