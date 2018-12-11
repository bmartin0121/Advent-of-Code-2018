package day8

import common.readSingle
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicReference

fun main(args: Array<String>) {

    /**
     * First part
     */
    val input = readSingle("day8.txt")
        .split(" ")
        .map { it.toInt() }
    val sum = AtomicInteger(0)
    addMetadata(AtomicReference(input), sum)
    println(sum)
}

private fun addMetadata(input: AtomicReference<List<Int>>, sum: AtomicInteger) {
    val children = input.get()[0]
    val metadata = input.get()[1]
    input.set(input.get().drop(2))
    (1..children).forEach {addMetadata(input, sum) }
    (1..metadata).forEach { sum.addAndGet(input.get()[it - 1]) }
    input.set(input.get().drop(metadata))
}
