package day3

import common.readLines

/*
 * First part
*/

fun main(args: Array<String>) {
	val fabric = HashMap<Pair<Int, Int>, Int>();
	readLines("day3.txt")
	    .map { claimFromFormattedString(it) }
	    .forEach { putCoverage(it, fabric) }
	val result = fabric.values.count{ it >= 2 }
	println(result)
}

fun putCoverage(claim: Claim, fabric: HashMap<Pair<Int, Int>, Int>) {
	for (i in claim.leftUpCorner.first..claim.rightUpCorner.first) {
		for (j in claim.leftUpCorner.second..claim.rightDownCorner.second) {
			val coverage = fabric.getOrDefault(i to j, 0) + 1
			fabric.put(i to j, coverage)
		}
	}
}
