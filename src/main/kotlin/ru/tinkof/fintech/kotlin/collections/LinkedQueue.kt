package ru.tinkof.fintech.kotlin.collections

import java.util.NoSuchElementException

class LinkedQueue {
    class Node(var element: Any?, var prev: Node?)

    private var head = Node(null, null)
    private var tail = head
    private var size = 0

    fun enqueue(element: Any?) {
        require(element != null) { "element must be not null" }
        tail.element = element
        tail.prev = Node(null, null)
        tail = tail.prev!!
        size++
    }

    fun dequeue() {
        assert(size >= 1) { throw NoSuchElementException() }
        head = head.prev!!
        size--
    }

    fun getHead(): Any {
        assert(size >= 1) { throw NoSuchElementException() }
        return head.element!!
    }

    fun clear() {
        size = 0
        this.head = Node(null, null)
        this.tail = head
    }

    fun create(): LinkedQueue {
        return LinkedQueue()
    }

    fun isEmpty(): Boolean {
        return size == 0
    }

    fun size(): Int {
        return this.size
    }
}