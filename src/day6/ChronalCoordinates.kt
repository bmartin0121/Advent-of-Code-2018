package day6

import common.readLines

val COORDINATE_REGEX = """(\d+), (\d+)""".toRegex()

fun main(args: Array<String>) {
    val coordinates = readCoordinates()
    val edges = edgeCoordinates(coordinates.values)
    val closest = mapOfClosestCoordinate(edges, coordinates)
    val largestArea = coordinates.keys
        .filter { !it.hasClosestCoordinateOnEdge(edges, closest) }
        .map { name -> closest.count { it.value == name } }
        .max()
    println(largestArea)
}

private fun readCoordinates(): Map<Int, Pair<Int, Int>> = readLines("day6.txt")
        .map { COORDINATE_REGEX.find(it)!!.destructured }
        .map { it.component1().toInt() to it.component2().toInt() }
        .mapIndexed { index, coordinate -> index to coordinate }
        .toMap()

private fun mapOfClosestCoordinate(edges: Edges, coordinates: Map<Int, Pair<Int, Int>>): Map<Pair<Int, Int>, Int> {
    val closest = HashMap<Pair<Int, Int>, Int>()
    for (i in edges.leftRight.first..edges.leftRight.second) {
        for (j in edges.topDown.first..edges.topDown.second) {
            val current = i to j
            val distances = coordinates
                .map { it.key to (it.value manhattanDistance current) }
                .toMap()
            val minDistance = distances.values.min()
            closest[current] = if (distances.values.count { it == minDistance } > 1)
                -1
            else
                coordinates.keys.find { distances[it] == minDistance } ?: -1
        }
    }
    return closest
}

private fun Int.hasClosestCoordinateOnEdge(edges: Edges, closestMap: Map<Pair<Int, Int>, Int>): Boolean {
    return closestMap
        .filter { edges.on(it.key) }
        .filter { it.value == this }
        .any()
}

infix fun Pair<Int, Int>.manhattanDistance(other: Pair<Int, Int>): Int {
    return Math.abs(this.first - other.first) + Math.abs(this.second - other.second)
}

fun edgeCoordinates(coordinates: Collection<Pair<Int, Int>>): Edges {
    return Edges(coordinates.minBy { it.first }!!.first to coordinates.maxBy { it.first }!!.first,
        coordinates.minBy { it.second }!!.second to coordinates.maxBy { it.second }!!.second
    )
}
