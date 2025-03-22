package org.example;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;

public class MyStack<E extends Integer> {
    private List<E> minElements;
    private List<E> elements;

    public MyStack() {
        this.minElements = new ArrayList<>();
        this.elements = new ArrayList<>();
    }

    public E push(E item) {
        checkElementOnMin(item);
        elements.add(item);
        return item;
    }

    public E pop() {
        E e = elements.get(elements.size() - 1);
        if (e == null) {
            throw new EmptyStackException();
        }

        elements.remove(elements.size() - 1);
        minElements.remove(e);
        return e;
    }

    /**
     * Looks at the object at the top of this stack without removing it
     * from the stack.
     *
     * @return the object at the top of this stack (the last item
     * of the {@code Vector} object).
     * @throws EmptyStackException if this stack is empty.
     */
    public E peek() {
        E e = elements.get(elements.size() - 1);
        if (e == null) {
            throw new EmptyStackException();
        }

        return e;
    }

    /**
     * Tests if this stack is empty.
     *
     * @return {@code true} if and only if this stack contains
     * no items; {@code false} otherwise.
     */
    public boolean empty() {
        return elements.size() == 0;
    }

    E min() {
        if (minElements.isEmpty()) {
            throw new EmptyStackException();
        }

        return minElements.get(minElements.size() - 1);
    }

    private void checkElementOnMin(E element) {
        if (minElements.isEmpty()) {
            minElements.add(element);
        }
        E e = minElements.get(minElements.size() - 1);
        if (e != null && element.intValue() < e.intValue()) {
            minElements.add(element);
        }
    }
}
