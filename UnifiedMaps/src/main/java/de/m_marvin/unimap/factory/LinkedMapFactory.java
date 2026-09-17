package de.m_marvin.unimap.factory;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LinkedMapFactory extends MapFactory {
	
	private final int initialCapacity;
	private final float loadFactor;
	
	public LinkedMapFactory(int initialCapacity, float loadFactor) {
		this.initialCapacity = initialCapacity;
		this.loadFactor = loadFactor;
	}

	public static LinkedMapFactory veryFastMaps() {
		return new LinkedMapFactory(16, 0.25F);
	}
	
	public static LinkedMapFactory fastMaps() {
		return new LinkedMapFactory(16, 0.50F);
	}
	
	public static LinkedMapFactory defaultMaps() {
		return new LinkedMapFactory(16, 0.75F);
	}

	public int getInitialCapacity() {
		return initialCapacity;
	}
	
	@Override
	public <E> List<E> newList() {
		return new ArrayList<E>(this.initialCapacity);
	}

	@Override
	public <E> Set<E> newSet() {
		return new LinkedHashSet<E>(this.initialCapacity, this.loadFactor);
	}

	@Override
	public <K, V> Map<K, V> newMap() {
		return new LinkedHashMap<K, V>(this.initialCapacity, this.loadFactor);
	}
	
}
