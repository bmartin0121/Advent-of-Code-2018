package day11

const val GRID_SIZE = 300
const val GRID_SERIAL_NUMBER = 1955

fun main(args: Array<String>) {
    /**
     * First part
     */
    val max3x3 = generateGrid(1..(GRID_SIZE - 2), 1..(GRID_SIZE - 2))
        .maxBy{ calculateNxNPower(it.first, it.second, 3) }
    println(max3x3)


    /**
     * Second part
     */
    val max = (1..300)
        .map  { generateGrid(1..(GRID_SIZE - it + 1), 1..(GRID_SIZE - it + 1)) to it}
        .parallelStream()
        .map { pair -> pair.first.maxBy { coord -> (calculateNxNPower(coord.first, coord.second, pair.second - 1)) } to pair.second }
        .max { o1, o2 ->  calculateNxNPower(o1.first!!.first, o1.first!!.second, o1.second - 1) - calculateNxNPower(o2.first!!.first, o2.first!!.second, o2.second - 1)}
    println(max)
}

private fun calculateNxNPower(x: Int, y: Int, n: Int): Int =
    generateGrid(0..n, 0..n)
        .map { FuelCell(x + it.first, y + it.second) }
        .sumBy { it.powerLevel(GRID_SERIAL_NUMBER) }

private fun generateGrid(widthRange: IntRange, heightRange: IntRange): List<Pair<Int, Int>> =
    widthRange.map { x -> heightRange.map { y -> x to y } }.flatten()
