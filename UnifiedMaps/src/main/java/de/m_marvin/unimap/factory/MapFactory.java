package de.m_marvin.unimap.factory;

import java.util.List;
import java.util.Map;
import java.util.Set;

import de.m_marvin.unimap.impl.ModularBiMap;
import de.m_marvin.unimap.impl.ModularMultiBiMap;
import de.m_marvin.unimap.impl.ModularMultiMap;
import de.m_marvin.unimap.types.BiMap;
import de.m_marvin.unimap.types.MultiBiMap;
import de.m_marvin.unimap.types.MultiMap;

public abstract class MapFactory {
	
	public abstract <E> List<E> newList();
	
	public abstract <E> Set<E> newSet();
	
	public abstract <K, V> Map<K, V> newMap();
	
	public <K, V> MultiMap<K, V> newMultiMap() {
		return new ModularMultiMap<K, V>(newMap(), this::newSet);
	}
	
	public <K, V> BiMap<K, V> newBiMap() {
		return new ModularBiMap<K, V>(newMap(), newMultiMap());
	}
	
	public <K, V> MultiBiMap<K, V> newMultiBiMap() {
		return new ModularMultiBiMap<K, V>(newMultiMap(), newMultiMap());
	}
	
}
