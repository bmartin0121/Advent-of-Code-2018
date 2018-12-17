package day12

import common.readLines

const val PLANT = '#'
const val EMPTY = '.'

val INITIAL_STATE_REGEX = """initial state: ([$PLANT\.]+)""".toRegex()
val RULE_REGEX = """([$PLANT\.]+) => ([$PLANT\.])""".toRegex()


fun main(args: Array<String>) {
    val lines = readLines("day12.txt")
    val state = stringRepToInt(INITIAL_STATE_REGEX.find(lines[0])!!.destructured.component1(), 0)
    val rules = lines.drop(2)
        .map { RULE_REGEX.find(it)!!.destructured }
        .map { (left, right) -> left to right }
        .toMap()

    /**
     * First part
     */
    var pots = Pots(state)
    (1..20).forEach {
        val plants = pots.platnsInOrder()
        val leftOffset = plants.first() - 2
        val newState = (leftOffset..(plants.last() + 2))
            .map { pots.rangeCointains((it - 2)..(it + 2)) }
            .map { booleanRepToString(it) }
            .map { rules[it]!! }
            .joinToString(separator = "") { s -> s }
        pots = Pots(stringRepToInt(newState, leftOffset))
    }
    println(pots.plants.sum())
}

private fun stringRepToInt(plants: String, leftOffset: Int): Set<Int> =
    plants.foldIndexed(emptySet<Int>()) { index, acc, c -> if (c == PLANT) acc.plus(index + leftOffset) else acc }

private fun booleanRepToString(booleans: List<Boolean>): String = booleans.fold("") { acc, b -> acc + if (b) PLANT else EMPTY }