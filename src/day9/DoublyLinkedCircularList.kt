package day9

import kotlin.math.abs

class DoublyLinkedCircularList<T> {

    private var current: Element

    constructor(startValue: T) {
        current = Element(startValue)
        current.next = current
        current.prev = current
    }

    fun getCurrent(): T = current.value

    fun step(steps: Int) {
        if (steps > 0) {
            (1..steps).forEach { current = current.next!! }
        } else {
            (1..abs(steps)).forEach { current = current.prev!! }
        }
    }

    fun remove(): T {
        val removed = getCurrent()
        current.prev!!.next = current.next
        current.next!!.prev = current.prev
        current = current.next!!
        return removed
    }

    fun add(value: T) {
        val toAdd = Element(value, current.next, current)
        current.next = toAdd
        toAdd.next!!.prev = toAdd
    }

    private inner class Element(val value: T, var next: Element? = null, var prev: Element? = null) {
    }

}