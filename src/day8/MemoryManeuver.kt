package day8

import common.readSingle
import java.util.concurrent.atomic.AtomicReference

fun main(args: Array<String>) {

    val input = readSingle("day8.txt")
        .split(" ")
        .map { it.toInt() }
        .toMutableList()
    val root = buildTree(input)

    /**
     * First part
     */
    println(root.sumOfAllMetadata())

    /**
     * Second part
     */
    println(root.getValue())
}

private fun buildTree(input: MutableList<Int>): Node {
    val result = Node()
    val children = input[0]
    val metadata = input[1]
    input.removeAt(0)
    input.removeAt(0)
    (1..children)
        .map { buildTree(input) }
        .forEach { result.addChild(it) }
    (1..metadata)
        .forEach {
            result.addMetadata(input[0])
            input.removeAt(0)
        }
    return result
}
