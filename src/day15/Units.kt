package day15

sealed class CaveUnit(var position: Pair<Int, Int>) : Comparable<CaveUnit> {
    abstract val mapSymbol: Char

    override fun compareTo(other: CaveUnit) =
            if (this.position.first == other.position.first) {
                this.position.second.compareTo(other.position.second)
            } else {
                this.position.first.compareTo(other.position.first)
            }

    override fun toString() = "${this.javaClass.simpleName}[position=$position]"
}

abstract class CombatCaveUnit(position: Pair<Int, Int>) : CaveUnit(position) {
    private val attackPower = 3
    private var health = 200
    var roundsPlayed = 0

    fun isAlive() = health > 0

    fun move(cave: Cave) {

    }

    fun attack(enemy: CombatCaveUnit, cave: Cave) {
        enemy.health -= this.attackPower
        if (!enemy.isAlive()) {
            cave.removeCorpse(enemy.position)
        }
    }

    fun endTurn() {
        ++roundsPlayed
    }

    abstract fun isEnemyOf(unit: CombatCaveUnit): Boolean
}

class Elf(position: Pair<Int, Int>) : CombatCaveUnit(position) {
    override val mapSymbol = 'E'
    override fun isEnemyOf(unit: CombatCaveUnit) = unit is Goblin
}

class Goblin(position: Pair<Int, Int>) : CombatCaveUnit(position) {
    override val mapSymbol = 'G'
    override fun isEnemyOf(unit: CombatCaveUnit) = unit is Elf
}



abstract class PrimitiveCaveUnit(position: Pair<Int, Int>) : CaveUnit(position)

class Wall(position: Pair<Int, Int>) : PrimitiveCaveUnit(position) {
    override val mapSymbol = '#'
}

class Floor(position: Pair<Int, Int>) : PrimitiveCaveUnit(position) {
    override val mapSymbol = '.'
}
