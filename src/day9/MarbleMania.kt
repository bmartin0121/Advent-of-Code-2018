package day9

import common.readSingle

val INPUT_REGEX = """(\d+) players; last marble is worth (\d+) points""".toRegex()

fun main(args: Array<String>) {
    val (elves, lastPoint) = INPUT_REGEX.find(readSingle("day9.txt"))!!.destructured

    /**
     * First part
     */
    println(winnerElf(elves.toInt(), lastPoint.toLong()).score)

    /**
     * Second part
     */
    println(winnerElf(elves.toInt(), lastPoint.toLong() * 100L).score)
}

private fun winnerElf(numberOfElves: Int, largestMarble: Long): Elf {
    val marbles = DoublyLinkedCircularList(0L)
    val elves = (1..numberOfElves).map { Elf() }
    var elfIndex = 0;
    (1..largestMarble).forEach {
        val elf = elves[elfIndex]
        if (it % 23L == 0L) {
            elf.addScore(it)
            marbles.step(-7)
            val removed = marbles.remove()
            elf.addScore(removed)
        } else {
            marbles.step(1)
            marbles.add(it)
            marbles.step(1)
        }
        elfIndex = (elfIndex + 1) % elves.size
    }
    return elves.maxBy { it.score }!!
}
