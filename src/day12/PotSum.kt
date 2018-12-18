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
    val generation20 = doGenerations(state, rules, 20)
    println(generation20.plants.sum())

    /**
     * Second part
     */
    val generationMany = fixedPointAwarePlantSummer(state, rules, 50_000_000_000)
    println(generationMany)
}

private fun doGenerations(initialState: Set<Int>, rules: Map<String, String>, numberOfGenerations: Long): Pots {
    var pots = Pots(initialState)
    var prev = 0
    var prevdiff = 0
    (1..numberOfGenerations).forEach {
        val plants = pots.platnsInOrder()
        val leftOffset = plants.first() - 2
        val newState = calculateNewState(leftOffset, plants, pots, rules)
        pots = Pots(stringRepToInt(newState, leftOffset))
    }
    return pots
}

private fun calculateNewState(leftOffset: Int, plants: List<Int>, pots: Pots, rules: Map<String, String>): String =
    (leftOffset..(plants.last() + 2))
        .map { pots.rangeCointains((it - 2)..(it + 2)) }
        .map { booleanRepToString(it) }
        .map { rules[it]!! }
        .joinToString(separator = "") { s -> s }

private fun stringRepToInt(plants: String, leftOffset: Int): Set<Int> =
    plants.foldIndexed(emptySet<Int>()) { index, acc, c -> if (c == PLANT) acc.plus(index + leftOffset) else acc }

private fun booleanRepToString(booleans: List<Boolean>): String =
    booleans.fold("") { acc, b -> acc + if (b) PLANT else EMPTY }

private fun fixedPointAwarePlantSummer(initialState: Set<Int>, rules: Map<String, String>, numberOfGenerations: Long): Long {
    var pots = Pots(initialState)
    var previousSum = 0L
    var previousDifference = 0L
    var differenceEquals = false
    var currentGeneration = 1L
    while (currentGeneration in 1..numberOfGenerations && !differenceEquals) {
        val plants = pots.platnsInOrder()
        val leftOffset = plants.first() - 2
        val newState = calculateNewState(leftOffset, plants, pots, rules)
        val newPlants = stringRepToInt(newState, leftOffset)
        pots = Pots(newPlants)
        differenceEquals = newPlants.sum() - previousSum == previousDifference
        previousDifference = newPlants.sum() - previousSum
        previousSum = newPlants.sum().toLong()
        ++currentGeneration
    }
    return previousDifference * (numberOfGenerations - currentGeneration + 1) + previousSum
}
