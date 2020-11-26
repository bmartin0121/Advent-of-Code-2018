package day15

class Cave {

    private val units: MutableMap<Pair<Int, Int>, CaveUnit>
    private val width: Int
    private val height: Int
    private val initialNumberOfElves: Int
    var currentRound = 1

    constructor(units: MutableMap<Pair<Int, Int>, CaveUnit>) {
        this.units = units
        this.width = units.map { it.key.first }.max()!!
        this.height = units.map { it.key.second }.max()!!
        this.initialNumberOfElves = units.values.count { it is Elf }
    }

    fun getPossibleTargetPositionsFor(unit: CombatCaveUnit) = getEnemiesOf(unit)
            .flatMap {
                it.position.surroundings()
                        .map { units[it] }
                        .filterIsInstance<Floor>()
            }
            .filter { PathFinder().calculateShortestPathLengthBetween(unit.position, it.position, units) < 99999 }

    fun calculateShortestPathLengthBetween(start: Pair<Int, Int>, target: Pair<Int, Int>): Int {
        return PathFinder().calculateShortestPathLengthBetween(start, target, units)
    }

    fun getNextStepTowards(start: Pair<Int, Int>, target: Pair<Int, Int>) = start.surroundings()
            .filter { units[it] is Floor }
            .sortedWith(ReadingDistance())
            .minBy { calculateShortestPathLengthBetween(it, target) }

    fun moveTo(unit: CaveUnit, newPosition: Pair<Int, Int>) {
        if (units[newPosition] is Floor) {
            units[unit.position] = Floor(unit.position)
            units[newPosition] = unit
            unit.position = newPosition
        } else {
            throw IllegalArgumentException("Cannot move to position $newPosition, because it's occupied by ${units[newPosition]}")
        }
    }

    fun identifyTargetFor(unit: CombatCaveUnit) = getEnemiesOf(unit)
            .sorted()
            .filter { it.position in unit.position.surroundings() }
            .minBy { it.health }


    fun getEnemiesOf(unit: CombatCaveUnit) = units.values
            .filterIsInstance<CombatCaveUnit>()
            .filter { it.isEnemyOf(unit) }

    fun removeCorpse(position: Pair<Int, Int>) {
        if (units[position] is CombatCaveUnit) {
            units[position] = Floor(position)
        } else {
            throw IllegalArgumentException("Cannot remove corpse from $position, because it's not a CombatCaveUnit but ${units[position]}")
        }
    }

    fun nextPlayer() = units.values
            .filterIsInstance<CombatCaveUnit>()
            .sorted()
            .firstOrNull { it.roundsPlayed < currentRound }

    fun nextRound() {
        print("After $currentRound rounds:\n $this")
        units.values.filterIsInstance<CombatCaveUnit>().sorted().forEach { println(it) }
        ++currentRound;
    }

    fun isGameOver() = units.values.all { it !is Elf } || units.values.all { it !is Goblin } || hasElfDied()

    fun hasElfDied() = initialNumberOfElves > units.values.count { it is Elf }

    fun outcome() {
        val hp = units.values
                .filterIsInstance<CombatCaveUnit>()
                .filter { it.isAlive() }
                .sumBy { it.health }
        println("Total rounds: ${currentRound - 1}")
        println("Total HP:     $hp")
        println("outcome:      ${(currentRound-1) * hp}")
    }

    override fun toString(): String {
        val stringBuilder = StringBuilder()
        for (col in 0..width) {
            for (row in 0..height) {
                stringBuilder.append(units[col to row]!!.mapSymbol)
            }
            stringBuilder.append("\n")
        }
        return stringBuilder.toString()
    }

}
