package day2

import common.readLines

/**
 * Second part
 */

fun main(args: Array<String>) {
	var boxIds = readLines("day2.txt").map { BoxId(it) }
	val idAndDifferentIndex = boxIds.mapIndexed { index, box ->
		boxIds.drop(index + 1)
			.map { box to it.differentIndeces(box) }
			.find { it.second.size == 1 }
	}.find { it != null }
	if (idAndDifferentIndex != null) {
		val id = idAndDifferentIndex.first.id
		val index = idAndDifferentIndex.second[0]
		val commonLetters = id.removeRange(index, index + 1)
		println(commonLetters)
	} else {
		println("There are no box ids that differ in only one character!")
	}
}