package day15

import common.readLines

fun main(args: Array<String>) {
    val caveMap = mutableMapOf<Pair<Int, Int>, Cave.Block>()
    val units = mutableListOf<Unit>()
    readLines("day15.txt")
            .forEachIndexed { rowIndex, line ->
                line.forEachIndexed { colIndex, blcok ->
                    val coordinate = rowIndex to colIndex
                    when(blcok) {
                        '#' -> caveMap[coordinate] = Cave.Block.WALL
                        '.' -> caveMap[coordinate] = Cave.Block.FLOOR
                        'G' -> units.add(Goblin(coordinate))
                        'E' -> units.add(Elf(coordinate))
                    }
                }
            }
    val cave = Cave(caveMap)
    units.sorted()
    print(units)
}
