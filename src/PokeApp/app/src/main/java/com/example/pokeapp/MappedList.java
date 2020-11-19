package com.example.pokeapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.function.Consumer;


/*
 * An ordered, unique item list/set with a
 * managed map for retrieving elements based
 * on a function passed in the constructor
 * as well as behaving like a normal list.
 *
 * T as the Type of elements in the list
 * K as the Type of the mapping function key
 *
 * Zachary Lefin 2020
 */
public class MappedList<T, K> implements List<T>{

    public interface Mapping<T, K> {
        K getKey(T o);
    }

    private final Mapping<T, K> mapping;
    private final List<T> values;
    private final Map<K, T> valMap;

    public MappedList(Mapping<T, K> mapping) {
        this.mapping = mapping;
        this.values = new ArrayList<>();
        this.valMap = new HashMap<>();
    }

    public MappedList(Mapping<T, K> mapping, List<T> values) {
        this.mapping = mapping;
        this.values = values;
        this.valMap = new HashMap<>();
    }

    public T get(K k) {
        return valMap.get(k);
    }

    @Override
    public T get(int index) {
        return values.get(index);
    }

    @Override
    public int size() {
        return values.size();
    }

    @Override
    public boolean isEmpty() {
        return values.isEmpty();
    }

    @Override
    public boolean contains(@Nullable Object o) {
        return values.contains(o);
    }

    @NonNull
    @Override
    public Iterator<T> iterator() {
        return values.iterator();
    }

    @NonNull
    @Override
    public Object[] toArray() {
        return values.toArray();
    }

    @NonNull
    @Override
    public <T1> T1[] toArray(@NonNull T1[] a) {
        // TODO implement
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(T t) {
        K k = mapping.getKey(t);
        if (valMap.containsKey(k)) {
            return false;
        } else {
            valMap.put(k, t);
            return values.add(t);
        }
    }

    @Override
    public boolean remove(@Nullable Object o) throws ClassCastException {
        T t = (T) o;
        K k = mapping.getKey(t);
        if (!valMap.containsKey(k)) {
            return false;
        } else {
            valMap.remove(k);
            return values.remove(t);
        }
    }

    @Override
    public T remove(int index) {
        T t = values.get(index);
        remove(t);
        return t;
    }

    @Override
    public boolean containsAll(@NonNull Collection<?> c) {
        return values.containsAll(c);
    }

    @Override
    public boolean addAll(@NonNull Collection<? extends T> c) {
        c.forEach(this::add);
        return true;
    }

    @Override
    public boolean addAll(int index, @NonNull Collection<? extends T> c) {
        // TODO implement
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(@NonNull Collection<?> c) {
        c.forEach(this::remove);
        return true;
    }

    @Override
    public boolean retainAll(@NonNull Collection<?> c) {
        ArrayList<T> diff = new ArrayList<>();
        values.forEach(v -> {
            if (!c.contains(v)) {
                diff.add(v);
            }
        });
        diff.forEach(this::remove);
        return true;
    }

    @Override
    public void clear() {
        values.clear();
        valMap.clear();
    }

    @Override
    public T set(int index, T element) {
        // TODO implement
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(int index, T element) {
        // TODO implement
        throw new UnsupportedOperationException();
    }

    @Override
    public int indexOf(@Nullable Object o) {
        return values.indexOf(o);
    }

    @Override
    public int lastIndexOf(@Nullable Object o) {
        return values.lastIndexOf(o);
    }

    @NonNull
    @Override
    public ListIterator<T> listIterator() {
        return values.listIterator();
    }

    @NonNull
    @Override
    public ListIterator<T> listIterator(int index) {
        return values.listIterator(index);
    }

    @NonNull
    @Override
    public MappedList<T, K> subList(int fromIndex, int toIndex) {
        return new MappedList<>(mapping, values.subList(fromIndex, toIndex));
    }

    @Override
    public void sort(@Nullable Comparator<? super T> c) {
        values.sort(c);
    }

    @Override
    public void forEach(@NonNull Consumer<? super T> action) {
        values.forEach(action);
    }
}
