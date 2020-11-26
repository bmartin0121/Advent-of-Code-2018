package day15

class PathFinder {

    fun calculateShortestPathLengthBetween(start: Pair<Int, Int>, target: Pair<Int, Int>, units: Map<Pair<Int, Int>, CaveUnit>): Int {
        val touched = mutableListOf(Node(start, 0))
        val processed = mutableSetOf<Node>()
        while (!touched.containsWithPosition(target) && touched.isNotEmpty()) {
            val current = touched.minBy { it.level }!!
            touched.remove(current)
            touched.addAll(current.position.surroundings()
                    .filter { units[it] is Floor }
                    .filter { !processed.containsWithPosition(it) && !touched.containsWithPosition(it) }
                    .map { Node(it, current.level + 1) }
            )
            processed.add(current)
        }
        val length = touched.find { it.position == target }?.level ?: 99999
        // println("Lenght between $start and $target = $length")
        return length
    }

    data class Node(val position: Pair<Int, Int>, val level: Int)

}

fun Collection<PathFinder.Node>.containsWithPosition(position: Pair<Int, Int>) = this.map { it.position }.any { it == position }
