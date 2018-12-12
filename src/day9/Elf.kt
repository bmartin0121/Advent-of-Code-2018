package day9

class Elf {

    var score: Int = 0
        private set

    fun addScore(add: Int) {
        score += add
    }

    override fun toString(): String = "Elf[score=$score]"
}
