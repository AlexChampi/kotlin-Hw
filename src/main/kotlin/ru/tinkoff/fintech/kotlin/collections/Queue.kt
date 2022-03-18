package ru.tinkoff.fintech.kotlin.collections

interface Queue<T> {
    fun offer(element: T): Boolean
    fun remove(): T
    fun peek(): T?
    fun elemnent(): T
    fun poll(): T?
    fun isEmpty(): Boolean
}