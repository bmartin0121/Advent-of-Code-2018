package day14

import java.lang.StringBuilder

const val SCORES_TO_FIND = "430971"

/**
 * Second part
 */
fun main(args: Array<String>) {

    val scores = StringBuilder("37")
    var elf1 = 0
    var elf2 = 1
    var tail = ""

    while (SCORES_TO_FIND != tail) {
        val newScore = (charToInt(scores[elf1]) + charToInt(scores[elf2])).toString()
        scores.append(newScore)
        elf1 = calculateNextIndex(elf1, scores)
        elf2 = calculateNextIndex(elf2, scores)
        if (scores.length >= SCORES_TO_FIND.length) {
            tail = scores.substring(scores.length - SCORES_TO_FIND.length)
        }
    }

    println(scores.indexOf(SCORES_TO_FIND))
}

private fun calculateNextIndex(currentIndex: Int, scores: StringBuilder) =
    (currentIndex + charToInt(scores[currentIndex]) + 1) % scores.length

private fun charToInt(char: Char) = char.toString().toInt()