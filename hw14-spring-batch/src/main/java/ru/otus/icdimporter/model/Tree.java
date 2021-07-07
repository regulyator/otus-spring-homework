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
public class Tree<T> implements Iterable<Tree<T>> {

    private T data;
    private List<Tree<T>> children;

    public Tree(T data) {
        this.data = data;
        this.children = new LinkedList<>();
    }

    public Tree<T> addChild(T child) {
        Tree<T> childNode = new Tree<>(child);
        this.children.add(childNode);
        return childNode;
    }

    @Override
    public Iterator<Tree<T>> iterator() {
        return children.listIterator();
    }

    @Override
    public void forEach(Consumer<? super Tree<T>> action) {
        Iterable.super.forEach(action);
    }

    @Override
    public Spliterator<Tree<T>> spliterator() {
        return Iterable.super.spliterator();
    }
}
