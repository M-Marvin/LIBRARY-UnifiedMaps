package de.m_marvin.unimap.impl.internal;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.function.Function;

public class DualModCollection<E> extends AbstractCollection<E> implements Collection<E> {
	
	protected Collection<E> backing;
	protected Function<E, Boolean> addMod;
	protected Function<Object, Boolean> removeMod;
	
	public DualModCollection(Collection<E> backing, Function<E, Boolean> addMod, Function<Object, Boolean> removeMod) {
		this.backing = backing == null ? Collections.emptyList() : backing;
		this.addMod = addMod;
		this.removeMod = removeMod;
	}
	
	@Override
	public Iterator<E> iterator() {
		return new Iterator<E>() {
			
			private Iterator<E> iter = DualModCollection.this.backing.iterator();
			private E current;
			
			@Override
			public boolean hasNext() {
				return this.iter.hasNext();
			}

			@Override
			public E next() {
				return this.current = this.iter.next();
			}
			
			@Override
			public void remove() {
				this.iter.remove();
				DualModCollection.this.removeMod.apply(this.current);
			}
			
		};
	}

	@Override
	public int size() {
		return this.backing.size();
	}
	
	@Override
	public boolean add(E e) {
		boolean result1 = this.backing.add(e);
		boolean result2 = this.addMod.apply(e);
		return result1 || result2;
	}
	
	@Override
	public boolean remove(Object o) {
		boolean result1 = this.backing.remove(o);
		boolean result2 = this.removeMod.apply(o);
		return result1 || result2;
	}
	
}