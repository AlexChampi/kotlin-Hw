package ru.tinkoff.fintech.kotlin.collections

interface Queue<T> {
    fun offer(element: T): Boolean
    fun remove(): T
    fun top(): T?
    fun elemnent(): T
    fun isEmpty(): Boolean
}