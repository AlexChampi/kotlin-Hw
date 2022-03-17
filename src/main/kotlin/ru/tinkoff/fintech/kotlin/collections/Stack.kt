package ru.tinkoff.fintech.kotlin.collections

interface Stack<T> {
    fun push(element: T)
    fun pop(): Any
    fun top(): Any?
    fun isEmpty(): Boolean
}