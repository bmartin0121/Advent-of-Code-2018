package day8

import common.readSingle
import java.util.concurrent.atomic.AtomicReference

fun main(args: Array<String>) {

    val input = readSingle("day8.txt")
        .split(" ")
        .map { it.toInt() }
    val root = buildTree(AtomicReference(input))

    /**
     * First part
     */
    println(root.sumOfAllMetadata())

    /**
     * Second part
     */
    println(root.getValue())
}

private fun buildTree(input: AtomicReference<List<Int>>): Node {
    val result = Node()
    val children = input.get()[0]
    val metadata = input.get()[1]
    input.set(input.get().drop(2))
    (1..children)
        .map { buildTree(input) }
        .forEach { result.addChild(it) }
    (1..metadata)
        .forEach { result.addMetadata(input.get()[it - 1]) }
    input.set(input.get().drop(metadata))
    return result
}
