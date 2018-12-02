package day1

import common.readLines
import java.util.stream.Collectors

/*
 * Second part
*/

fun main(args: Array<String>) {
	val shifts = CircularList<Int>(readLines("day1.txt")
		.map { it.toInt() })
	println(findFirstDuplicate(shifts))
}

fun findFirstDuplicate(shifts: List<Int>): Int? {
	val touched = HashSet<Int>()
	var frequency = START
	for (shift in shifts) {
	    if (touched.contains(frequency)) {
	        return frequency
	    } else {
	        touched.add(frequency)
	        frequency += shift
		}
	}
	return null
}
