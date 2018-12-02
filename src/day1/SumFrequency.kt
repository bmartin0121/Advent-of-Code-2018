package day1

import common.readLines

/*
 * First part
*/

const val START = 0

fun main(args: Array<String>) {
	val shift = readLines("day1.txt")
		.map { it.toInt() }
		.sum()
	println(START + shift)
}
