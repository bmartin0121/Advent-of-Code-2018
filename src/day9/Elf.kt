package day9

import java.math.BigInteger

class Elf {

    var score = 0L
        private set

    fun addScore(toAdd: Long) {
        score += toAdd
    }

    override fun toString(): String = "Elf[score=$score]"
}
