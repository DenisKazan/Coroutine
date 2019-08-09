package ru.abdt.coroutine.dagger

interface ComponentProvider<E> {
    val component: E
}
