package de.m_marvin.unimap.impl.internal;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

public class SetCollection2FlatCollection<E> extends AbstractCollection<E> {
	
	private final Collection<Set<E>> backingCollection;
	
	public SetCollection2FlatCollection(Collection<Set<E>> backingCollection) {
		this.backingCollection = backingCollection;
	}

	@Override
	public int size() {
		return this.backingCollection.stream().mapToInt(Set::size).sum();
	}
	
	@Override
	public Iterator<E> iterator() {
		return new Iterator<E>() {
			
			private final Iterator<Set<E>> valuesIter = SetCollection2FlatCollection.this.backingCollection.iterator();
			private Set<E> values = null;
			private Iterator<E> valueIter = null;
			
			@Override
			public boolean hasNext() {
				if (valueIter != null && valueIter.hasNext()) return true;
				return valuesIter.hasNext();
			}

			@Override
			public E next() {
				if (!hasNext()) throw new NoSuchElementException();
				if (valueIter == null || !valueIter.hasNext()) {
					values = valuesIter.next();
					valueIter = values.iterator();
				}
				if (!valueIter.hasNext()) throw new NoSuchElementException();
				return valueIter.next();
			}
			
			@Override
			public void remove() {
				valueIter.remove();
				if (values.isEmpty())
					valuesIter.remove();
			}
			
		};
	}

}
