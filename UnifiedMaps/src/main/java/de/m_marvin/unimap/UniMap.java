package de.m_marvin.unimap;

import de.m_marvin.unimap.factory.HashMapFactory;
import de.m_marvin.unimap.factory.LinkedMapFactory;
import de.m_marvin.unimap.factory.MapFactory;

public class UniMap {
	
	private UniMap() {}
	
	public static final MapFactory HASH = HashMapFactory.defaultMaps();
	public static final MapFactory HASH_FAST = HashMapFactory.fastMaps();
	public static final MapFactory HASH_VERY_FAST = HashMapFactory.veryFastMaps();
	
	public static final MapFactory LINKED = LinkedMapFactory.defaultMaps();
	public static final MapFactory LINKED_FAST = LinkedMapFactory.fastMaps();
	public static final MapFactory LINKED_VERY_FAST = LinkedMapFactory.veryFastMaps();
	
}
