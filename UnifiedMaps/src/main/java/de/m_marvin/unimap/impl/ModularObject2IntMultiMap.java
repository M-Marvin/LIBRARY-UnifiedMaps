package de.m_marvin.unimap.impl;

import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

import de.m_marvin.unimap.types.Object2IntMultiMap;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;

public class ModularObject2IntMultiMap<K> extends ModularMultiMap<K, Integer> implements Object2IntMultiMap<K> {

	public ModularObject2IntMultiMap(Map<K, IntSet> map, Supplier<IntSet> setFactory) {
		super(map, () -> (Set<Integer>) setFactory.get());
		// TODO Auto-generated constructor stub
	}

	@Override
	public void defaultReturnValue(int rv) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int defaultReturnValue() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ObjectSet<it.unimi.dsi.fastutil.objects.Object2IntMap.Entry<K>> object2IntEntrySet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean containsValue(int value) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getInt(Object key) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public IntCollection getAllInt(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IntCollection getAllInt(K key, boolean makeIfNull) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IntCollection replaceInt(K key, IntCollection values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean putAllInt(K key, IntCollection values) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public IntCollection removeAllInt(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Entry<K, IntCollection>> object2IntEntryValuesSet() {
		// TODO Auto-generated method stub
		return null;
	}

}
