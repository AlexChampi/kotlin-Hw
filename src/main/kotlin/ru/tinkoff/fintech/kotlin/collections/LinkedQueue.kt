package ru.tinkoff.fintech.kotlin.collections

class LinkedQueue<T : Any> : Queue<T> {
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
        if (isEmpty()) throw NoSuchElementException()

        val result = head.element!!
        head = head.prev!!
        size--
        return result
    }

    override fun peek(): T? {
        return head.element
    }

    fun clear() {
        size = 0
        head = Node()
        tail = head
    }

    override fun elemnent(): T = peek() ?: throw NoSuchElementException()

    override fun poll(): T? {
        val result = head.element
        head = head.prev!!
        size--
        return result

    }

    override fun isEmpty(): Boolean {
        return size == 0
    }

}