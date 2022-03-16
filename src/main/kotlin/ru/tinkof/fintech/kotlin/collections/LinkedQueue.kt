package ru.tinkof.fintech.kotlin.collections

import java.util.NoSuchElementException

class LinkedQueue<T> : Queue<T> {
    class Node(var element: Any? = null, var prev: Node? = null)

    private var head = Node()
    private var tail = head
    var size = 0
        private set

    override fun enqueue(element: Any) {
        tail.element = element
        tail.prev = Node()
        tail = tail.prev!!
        size++
    }

    override fun dequeue() {
        require(size >= 1) { "queue is empty!" }
        head = head.prev!!
        size--
    }

    override fun getTop(): Any {
        require(size >= 1) { "queue is empty!" }
        return head.element!!
    }

    fun clear() {
        size = 0
        this.head = Node()
        this.tail = head
    }

    override fun isEmpty(): Boolean {
        return size == 0
    }

}