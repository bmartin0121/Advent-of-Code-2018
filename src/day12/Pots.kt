package day12

class Pots {

    val plants: MutableSet<Int>

    constructor(plants: Set<Int>) {
        this.plants = plants.toMutableSet()
    }

    fun contains(pot: Int): Boolean = plants.contains(pot)

    fun rangeCointains(range: IntRange): List<Boolean> = range.map { plants.contains(it) }

    fun platnsInOrder(): List<Int> = plants.toList().sorted()

}
