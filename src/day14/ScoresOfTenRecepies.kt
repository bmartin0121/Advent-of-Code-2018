package day14

const val AFTER_NUMBER_OF_RECEPIES = 430971
const val NUMBER_OF_RECEPIES = 10

/**
 * First part
 */
fun main(args: Array<String>) {

    val scores = mutableListOf(3, 7)
    var elf1 = 0
    var elf2 = 1

    while (scores.size < AFTER_NUMBER_OF_RECEPIES + NUMBER_OF_RECEPIES) {
        val newScore = (scores[elf1] + scores[elf2]).toString()
        newScore.forEach {
            scores.add(it.toString().toInt())
        }
        elf1 = calculateNextIndex(elf1, scores)
        elf2 = calculateNextIndex(elf2, scores)
    }

    println(
        scores
            .subList(AFTER_NUMBER_OF_RECEPIES, AFTER_NUMBER_OF_RECEPIES + NUMBER_OF_RECEPIES)
            .joinToString("")
    )
}

private fun calculateNextIndex(currentIndex: Int, scores: List<Int>) =
    (currentIndex + scores[currentIndex] + 1) % scores.size