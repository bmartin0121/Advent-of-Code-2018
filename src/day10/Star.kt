package day10

class Star(var position: Pair<Int, Int>, val velocity: Pair<Int, Int>) {

    fun moveBy(vector: Pair<Int, Int>) {
        position = (position.first + vector.first) to (position.second + vector.second)
    }

    fun move() {
        moveBy(velocity)
    }

    override fun toString(): String = "Star[position=$position, velocity=$velocity]"

}