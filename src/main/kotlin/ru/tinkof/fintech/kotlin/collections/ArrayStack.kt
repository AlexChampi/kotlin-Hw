package ru.tinkof.fintech.kotlin.collections

import java.util.NoSuchElementException

class ArrayStack<T> : Stack<T> {
    private var elements = Array<Any?>(10) { null }
    public var size: Int = 0
        private set

    override fun push(element: T) {
        ensureCapacity(size + 1)
        elements[size++] = element
    }

    private fun ensureCapacity(size: Int) {
        if (elements.size < size) {
            elements = elements.copyOf(2 * size)
        }
    }


    override fun pop(): Any? {
        require(size >= 1) { "stack is free!" }
        return elements[--size]
    }

    override fun peek(): Any? {
        require(size >= 1) { "stack is free!" }
        return elements[size - 1]
    }

    override fun isEmpty(): Boolean {
        return size == 0
    }

}

