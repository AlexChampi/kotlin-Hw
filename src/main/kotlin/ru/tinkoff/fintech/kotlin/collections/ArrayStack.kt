package ru.tinkoff.fintech.kotlin.collections

class ArrayStack<T> : Stack<T> {
    private var elements = arrayOfNulls<Any?>(10)
    var size: Int = 0
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
        if (isEmpty()) {
            throw(java.util.NoSuchElementException())
        }
        return elements[--size]
    }

    override fun peek(): Any? {
        if (isEmpty()) {
            throw(java.util.NoSuchElementException())
        }
        return elements[size - 1]
    }

    override fun isEmpty(): Boolean {
        return size == 0
    }

}

