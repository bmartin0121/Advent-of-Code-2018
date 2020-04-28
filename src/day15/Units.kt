package day15

sealed class Unit(var position: Pair<Int, Int>) : Comparable<Unit> {
    override fun compareTo(other: Unit) =
            if (this.position.first == other.position.first) {
                this.position.second.compareTo(other.position.second)
            } else {
                this.position.first.compareTo(other.position.first)
            }

    override fun toString() = "${this.javaClass.simpleName}[position=$position]"
}

class Elf(position: Pair<Int, Int>) : Unit(position)

class Goblin(position: Pair<Int, Int>) : Unit(position)
