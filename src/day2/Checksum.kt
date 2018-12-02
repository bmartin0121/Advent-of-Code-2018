package day2

import common.readLines

/*
 * First part
*/

fun main(args: Array<String>) {
	var boxIds = readLines("day2.txt").map { BoxId(it) }
	val twoTimes = boxIds.count { it.containsALetterExactTimes(2) }
	val threeTimes = boxIds.count { it.containsALetterExactTimes(3) }
	println(twoTimes * threeTimes)
}