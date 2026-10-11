package de.m_marvin.unimap.impl;

import java.util.Map;

import de.m_marvin.unimap.types.Int2ObjectBiMap;
import de.m_marvin.unimap.types.MultiMap;
import de.m_marvin.unimap.types.Object2IntMultiMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.objects.ObjectSet;

public class ModularInt2ObjectBiMap<V> extends ModularBiMap<Integer, V> implements Int2ObjectBiMap<V> {

	public ModularInt2ObjectBiMap(Int2ObjectMap<V> k2vMap, Object2IntMultiMap<V> v2kMap) {
		super(k2vMap, v2kMap);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void defaultReturnValue(V rv) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public V defaultReturnValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ObjectSet<it.unimi.dsi.fastutil.ints.Int2ObjectMap.Entry<V>> int2ObjectEntrySet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean containsKey(int key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public V get(int key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getIntKey(Object value) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getIntKeyOrDefault(Object value, int defaultValue) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public IntCollection getIntKeys(Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int removeValueInt(V value) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public IntCollection removeValuesInt(V value) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
