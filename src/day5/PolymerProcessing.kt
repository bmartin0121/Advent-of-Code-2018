package day5

import common.readLines

fun main(args: Array<String>) {
    val input = readLines("day5.txt")[0]

    /*
     * First part.
     */
    println(reducePolymer(input).length)

    /*
     * Second part.
     */
    val unitTypes = input.map { it.toUpperCase() }.toSet()
    val shortestPossiblePolymer = unitTypes
        .map { excluded -> input.filter { it.toUpperCase() != excluded }  }
        .map { reducePolymer(it) }
        .minBy { it.length }
    println(shortestPossiblePolymer!!.length)
}

private fun reducePolymer(original: String): String {
    return original.fold("") { polymer, unit ->
        if (polymer.isNotEmpty() && unitsReact(polymer.last(), unit)) {
            polymer.dropLast(1)
        } else {
            polymer + unit
        }
    }
}

private fun unitsReact(unit1: Char, unit2: Char): Boolean {
    return unit1 != unit2 && unit1.toUpperCase() == unit2.toUpperCase()
}
