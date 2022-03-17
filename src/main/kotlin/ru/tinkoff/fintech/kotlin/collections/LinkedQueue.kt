package ru.tinkoff.fintech.kotlin.collections

class LinkedQueue<T> : Queue<T> {
    class Node<T>(var element: T? = null, var prev: Node<T>? = null)

    private var head = Node<T>()
    private var tail = head
    var size = 0
        private set

    override fun offer(element: T): Boolean {
        try {
            tail.element = element
            tail.prev = Node()
            tail = tail.prev!!
            size++
            return true
        } catch (e: Exception) {
            return false
        }
    }

    override fun remove(): T {
        if (isEmpty()) {
            throw(java.util.NoSuchElementException())
        }
        val result = head.element
        head = head.prev!!
        size--
        return result!!
    }

    override fun top(): T? {
        return head.element
    }

    fun clear() {
        size = 0
        this.head = Node()
        this.tail = head
    }

    override fun elemnent(): T {
        if (head.element == null) {
            throw NoSuchElementException()
        } else {
            return head.element!!
        }
    }

    override fun isEmpty(): Boolean {
        return size == 0
    }

}