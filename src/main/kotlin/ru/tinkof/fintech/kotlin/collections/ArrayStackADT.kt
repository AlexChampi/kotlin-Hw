package ru.tinkof.fintech.kotlin.collections

import java.util.NoSuchElementException

class ArrayStackADT {


    private var elements = Array<Any>(2) { 0 }
    private var size = 0

    fun create(): ArrayStackADT {
        val stack = ArrayStackADT()
        this.elements = Array<Any>(2) { 0 }
        return stack
    }

    fun push(element: Any?) {
        require(element != null) { "element must be not null" }
        this.ensureCapacity(this.size + 1)
        this.elements[this.size++] = element
    }

    private fun ensureCapacity(size: Int) {
        if (this.elements.size < size) {
            val tmp = this.elements.copyOf()
            this.elements = Array<Any>(size * 2) { 0 }
            for (i in 0..tmp.size - 1) {
                this.elements[i] = tmp[i]
            }
        }
    }


    fun pop(): Any {
        require(this.size >= 1) { throw NoSuchElementException() }
        return this.elements[--this.size]
    }

    fun peek(): Any {
        require(this.size >= 1) { throw NullPointerException() }
        return this.elements[this.size - 1]
    }

    fun size(): Int {
        return this.size
    }

    fun isEmpty(): Boolean {
        return this.size == 0
    }

}

