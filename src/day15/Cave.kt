package day15

import java.lang.IllegalArgumentException
import java.lang.StringBuilder
import javax.swing.text.Position

class Cave {

    private val units: MutableMap<Pair<Int, Int>, CaveUnit>
    private val width: Int
    private val height: Int
    private var currentRound = 1

    constructor(units: MutableMap<Pair<Int, Int>, CaveUnit>) {
        this.units = units
        this.width = units.map { it.key.first }.max()!!
        this.height = units.map { it.key.second }.max()!!
    }

    fun moveTo(unit: CaveUnit, newPosition: Pair<Int, Int>) {
        if (units[newPosition] is Floor) {
            units[unit.position] = Floor(unit.position)
            units[newPosition] = unit
            unit.position = newPosition
        } else {
            throw IllegalArgumentException("Cannot move to position $newPosition, because it's occupied by ${units[newPosition]}")
        }
    }

    fun getEnemiesOf(unit: CombatCaveUnit) {
        units.values
                .filterIsInstance(CombatCaveUnit::class.java)
                .filter { it.isEnemyOf(unit) }
    }

    fun removeCorpse(position: Pair<Int, Int>) {
        if (units[position] is CombatCaveUnit) {
            units[position] = Floor(position)
        } else {
            throw IllegalArgumentException("Cannot remove corpse from $position, because it's not a CombatCaveUnit but ${units[position]}")
        }
    }

    fun nextPlayer() = units.values
            .sorted()
            .filterIsInstance(CombatCaveUnit::class.java)
            .first { it.roundsPlayed < currentRound }

    fun nextRound() {
        ++currentRound;
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
