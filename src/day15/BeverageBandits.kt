package day15

import common.readLines
import java.lang.IllegalArgumentException

fun main(args: Array<String>) {
    val caveMap = mutableMapOf<Pair<Int, Int>, CaveUnit>()
    readLines("day15.txt")
            .forEachIndexed { rowIndex, line ->
                line.forEachIndexed { colIndex, block ->
                    val coordinate = rowIndex to colIndex
                    caveMap[coordinate] = when(block) {
                        '#' -> Wall(coordinate)
                        '.' -> Floor(coordinate)
                        'G' -> Goblin(coordinate)
                        'E' -> Elf(coordinate)
                        else -> throw IllegalArgumentException("Unknown block: $block")
                    }
                }
            }
    val cave = Cave(caveMap)
    var possibleEnd = false
    var stillGoing = true
    while(stillGoing) {
        val currentPlayer = cave.nextPlayer()
        if (currentPlayer != null) {
            currentPlayer.endTurn()
        } else if (!possibleEnd) {
            cave.nextRound()
            possibleEnd = true
        } else if (possibleEnd) {
            stillGoing = false
        }
        print(cave)
    }

}
