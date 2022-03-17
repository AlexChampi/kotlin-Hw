package ru.tinkoff.fintech.kotlin.collections

class ArrayStack<T : Any> : Stack<T> {
    private var elements = arrayOfNulls<Any>(10)
    var size: Int = 0
        private set

    override fun push(element: T) {
        ensureCapacity()
        elements[size++] = element
    }

    private fun ensureCapacity() {
        if (elements.size <= size) {
            elements = elements.copyOf(2 * size)
        }
    }


    override fun pop(): T {
        if (isEmpty()) {
            throw NoSuchElementException()
        }
        return elements[--size] as T
    }

    override fun peek(): T {
        return elements[size - 1] as T
    }

    override fun isEmpty(): Boolean {
        return size == 0
    }

}

