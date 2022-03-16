package ru.tinkof.fintech.kotlin.collections

interface Queue<T> {
    fun enqueue(element: Any)
    fun dequeue()
    fun getTop(): Any
    fun isEmpty(): Boolean
}