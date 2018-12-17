package day11

const val GRID_SERIAL_NUMBER = 1955

fun main(args: Array<String>) {
    val maxCoord = generateGrid(1..298, 1..298)
        .maxBy { calculate3x3Power(it.first, it.second) }
    println(maxCoord)
}

private fun calculate3x3Power(x: Int, y: Int): Int =
    generateGrid(0..2, 0..2)
        .map { FuelCell(x + it.first, y + it.second) }
        .sumBy { it.powerLevel(GRID_SERIAL_NUMBER) }

private fun generateGrid(widthRange: IntRange, heightRange: IntRange): List<Pair<Int, Int>> =
    widthRange.map { x -> heightRange.map { y -> x to y } }.flatten()
