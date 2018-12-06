package day6

class Edges(val leftRight: Pair<Int, Int>, val topDown: Pair<Int, Int>) {

    fun on(point: Pair<Int, Int>): Boolean =
        leftRight.first == point.first || leftRight.second == point.first
                || topDown.first == point.second || topDown.second == point.second

    override fun toString(): String = "Edges[topDown=$topDown, leftRight=$leftRight]"

}