package com.mystery.chat.utils;

import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Consumer;

/**
 * @author shouchen
 * @date 2023/1/1
 */
public class RWList<T> extends LinkedList<T> {
    private final ReadWriteLock readWriteLock;

    public RWList() {
        super();
        readWriteLock = new ReentrantReadWriteLock();
    }

    @Override
    public T getFirst() {
        return super.getFirst();
    }

    @Override
    public T getLast() {
        return super.getLast();
    }

    @Override
    public T removeFirst() {
        return super.removeFirst();
    }

    @Override
    public T removeLast() {
        return super.removeLast();
    }

    @Override
    public void addFirst(T t) {
        super.addFirst(t);
    }

    @Override
    public void addLast(T t) {
        super.addLast(t);
    }

    @Override
    public boolean contains(Object o) {
        return super.contains(o);
    }

    @Override
    public boolean add(T t) {
        readWriteLock.writeLock().lock();
        try {
            return super.add(t);
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    @Override
    public boolean remove(Object o) {
        readWriteLock.writeLock().lock();
        try {
            return super.remove(o);
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return super.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        return super.addAll(index, c);
    }

    @Override
    public void clear() {
        super.clear();
    }

    @Override
    public T get(int index) {
        return super.get(index);
    }

    @Override
    public T set(int index, T element) {
        return super.set(index, element);
    }

    @Override
    public void add(int index, T element) {
        super.add(index, element);
    }

    @Override
    public T remove(int index) {
        return super.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return super.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return super.lastIndexOf(o);
    }

    @Override
    public T peek() {
        return super.peek();
    }

    @Override
    public T element() {
        return super.element();
    }

    @Override
    public T poll() {
        return super.poll();
    }

    @Override
    public T remove() {
        return super.remove();
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        readWriteLock.readLock().lock();
        try {
            super.forEach(action);
        } finally {
            readWriteLock.readLock().unlock();
        }
    }
}
