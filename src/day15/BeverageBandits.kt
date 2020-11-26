package day15

import common.readLines
import java.lang.IllegalArgumentException

fun main(args: Array<String>) {
    val caveMap = mutableMapOf<Pair<Int, Int>, CaveUnit>()
    readLines("day15.txt")
            .forEachIndexed { rowIndex, line ->
                line.forEachIndexed { colIndex, block ->
                    val coordinate = rowIndex to colIndex
                    caveMap[coordinate] = when (block) {
                        '#' -> Wall(coordinate)
                        '.' -> Floor(coordinate)
                        'G' -> Goblin(coordinate)
                        'E' -> Elf(coordinate)
                        else -> throw IllegalArgumentException("Unknown block: $block")
                    }
                }
            }
    val cave = Cave(caveMap)
    while (!cave.isGameOver()) {
        val currentPlayer = cave.nextPlayer()
        if (currentPlayer != null) {
            val target = cave.identifyTargetFor(currentPlayer)
            if (target != null) {
                currentPlayer.attack(target, cave)
            } else {
                currentPlayer.move(cave)
                val target2 = cave.identifyTargetFor(currentPlayer)
                if (target2 != null) {
                    currentPlayer.attack(target2, cave)
                }
            }
            currentPlayer.endTurn()
        } else {
            cave.nextRound()
        }
    }
    cave.outcome()

}

fun Pair<Int, Int>.surroundings() = setOf(
        first + 1 to second,
        first to second + 1,
        first - 1 to second,
        first to second - 1
)