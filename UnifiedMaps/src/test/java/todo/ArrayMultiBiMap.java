package todo;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import de.m_marvin.unimap.api.MultiBiMap;
import de.m_marvin.unimap.impl.ArrayBiMap;

public class ArrayMultiBiMap<K, V> extends ArrayBiMap<K, V> implements MultiBiMap<K, V> {

	@SuppressWarnings("unchecked")
	@Override
	public V put(K key, V value) {
		for (int i = 0; i < entries.size(); i += 2)
			if (Objects.equals(entries.get(i), key) && Objects.equals(entries.get(i + 1), value)) {
				V replaced = (V) entries.get(i + 1);
				entries.set(i + 1, value);
				return replaced;
			}
		entries.add(key);
		entries.add(value);
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<V> getAll(Object key) {
		List<V> values = new ArrayList<>();
		for (int i = 0; i < entries.size(); i += 2)
			if (Objects.equals(entries.get(i), key)) {
				values.add((V) entries.get(i));
			}
		return Collections.unmodifiableCollection(values);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<V> getAll(K key, boolean makeIfNull) {
		List<V> values = new ArrayList<>();
		for (int i = 0; i < entries.size(); i += 2)
			if (Objects.equals(entries.get(i), key)) {
				values.add((V) entries.get(i));
			}
		return (values.isEmpty() && !makeIfNull) ? null : Collections.unmodifiableCollection(values);
	}

	@Override
	public Collection<V> replace(K key, Collection<V> values) {
		Collection<V> replaced = removeAll(key);
		putAll(key, values);
		return replaced;
	}

	@Override
	public boolean putAll(K key, Collection<V> values) {
		return values.stream().map(v -> v != put(key, v)).reduce(Boolean::logicalOr).orElse(false);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<V> removeAll(K key) {
		List<V> values = new ArrayList<>();
		for (int i = 0; i < entries.size(); i += 2)
			if (Objects.equals(entries.get(i), key)) {
				values.add((V) entries.get(i));
				entries.remove(i + 1);
				entries.remove(i);
				i--;
			}
		return Collections.unmodifiableCollection(values);
	}

	private AbstractSet<Entry<K, Collection<V>>> cachedEntryValuesSet = null;
	
	@Override
	public Set<Entry<K, Collection<V>>> entryValuesSet() {
		if (this.cachedEntryValuesSet == null) {
			this.cachedEntryValuesSet = new AbstractSet<Map.Entry<K,Collection<V>>>() {

				@Override
				public int size() {
					// TODO Auto-generated method stub
					return 0;
				}

				@Override
				public Iterator<Entry<K, Collection<V>>> iterator() {
					// TODO Auto-generated method stub
					return null;
				}
				
			};
		}
		return this.cachedEntryValuesSet;
	}
	
}
