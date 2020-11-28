package day16

import common.readLines

val BEFORE_REGEX = """Before: \[(\d+), (\d+), (\d+), (\d+)]""".toRegex()
val INPUT_REGEX = """(\d+) (\d+) (\d+) (\d+)""".toRegex()
val AFTER_REGEX = """After:  \[(\d+), (\d+), (\d+), (\d+)]""".toRegex()

fun main(args: Array<String>) {
    val lines = readLines("day16_1.txt")
    val operations = Operation::class.sealedSubclasses.map {  it.constructors.first().call() }
    var matchedAtLeast3 = 0
    for (i in lines.indices step 4) {
        val before = BEFORE_REGEX.find(lines[i])!!.destructured
                .toList()
                .map { it.toInt() }
                .toMutableList()
        val (opt, a, b, c) = INPUT_REGEX.find(lines[i+1])!!.destructured
        val after = AFTER_REGEX.find(lines[i+2])!!.destructured
                .toList()
                .map { it.toInt() }
        var matched = 0
        for (operation in operations) {
            var actualAfter = operation.execute(a.toInt(), b.toInt(), c.toInt(), before)
            if (actualAfter == after) {
                ++matched
            }
        }
        if (matched >= 3) {
            ++matchedAtLeast3
        }
    }
    println(matchedAtLeast3)
}
