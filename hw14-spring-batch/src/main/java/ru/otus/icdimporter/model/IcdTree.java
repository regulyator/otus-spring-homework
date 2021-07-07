package ru.otus.icdimporter.model;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

@Data
@NoArgsConstructor
public class IcdTree<T> implements Iterable<IcdTree<T>> {

    private T data;
    private List<IcdTree<T>> children;

    public IcdTree(T data) {
        this.data = data;
        this.children = new LinkedList<>();
    }

    public IcdTree<T> addChild(T child) {
        IcdTree<T> childNode = new IcdTree<>(child);
        this.children.add(childNode);
        return childNode;
    }

    @Override
    public Iterator<IcdTree<T>> iterator() {
        return children.listIterator();
    }

    @Override
    public void forEach(Consumer<? super IcdTree<T>> action) {
        Iterable.super.forEach(action);
    }

    @Override
    public Spliterator<IcdTree<T>> spliterator() {
        return Iterable.super.spliterator();
    }
}
