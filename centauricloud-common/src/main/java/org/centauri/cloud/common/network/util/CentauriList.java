package org.centauri.cloud.common.network.util;

import java.util.ArrayList;
import java.util.Collection;

public class CentauriList<T> extends ArrayList<T> {


	public CentauriList(int initialCapacity) {
		super(initialCapacity);
	}

	public CentauriList() {
	}

	public CentauriList(Collection<? extends T> c) {
		super(c);
	}

	@Override
	public void add(int index, T element) {
		throw new UnsupportedOperationException("Add Packets at the end");
	}

	@Override
	public T set(int index, T element) {
		throw new UnsupportedOperationException("Add Packets at the end");
	}

	@Override
	public boolean remove(Object o) {
		throw new UnsupportedOperationException("Add Packets at the end");
	}

	@Override
	public T remove(int index) {
		throw new UnsupportedOperationException("Add Packets at the end");
	}
}
