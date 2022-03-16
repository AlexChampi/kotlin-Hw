package ru.tinkof.fintech.kotlin.collections

interface Stack<T> {
    fun push(element: T)
    fun pop(): Any?
    fun peek(): Any?
    fun isEmpty(): Boolean
}