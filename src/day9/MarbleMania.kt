package day9

import common.readSingle

val INPUT_REGEX = """(\d+) players; last marble is worth (\d+) points""".toRegex()

fun main(args: Array<String>) {
    val (players, lastPoint) = INPUT_REGEX.find(readSingle("day9.txt"))!!.destructured
    val elves = (1..players.toInt()).map { Elf() }
    val marblesLeft = (1..lastPoint.toInt()).toMutableSet()

    /**
     * First part
     */
    println(winnerElf(elves, lastPoint.toInt()).score)
}

private fun winnerElf(elves: List<Elf>, largestMarble: Int): Elf {
    val marbles = mutableListOf(0)
    var marbleIndex = 0;
    var elfIndex = 0;
    (1..largestMarble).forEach {
        val elf = elves[elfIndex]
        if (it % 23 == 0) {
            elf.addScore(it)
            marbleIndex = (marbleIndex - 7 + marbles.size) % marbles.size
            val removed = marbles[marbleIndex]
            marbles.removeAt(marbleIndex)
            elf.addScore(removed)
        } else {
            marbleIndex = (marbleIndex + 2) % marbles.size
            marbles.add(marbleIndex, it)
        }
        elfIndex = (elfIndex + 1) % elves.size
    }
    return elves.maxBy{ it.score }!!
}