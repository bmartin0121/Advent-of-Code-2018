package day3

import common.readLines

/*
 * Second part
*/

fun main(args: Array<String>) {
	val claims = readLines("day3.txt")
		.map { claimFromFormattedString(it) }

	val notOverlappingClaims = claims.filter { claim ->
		claims
			.filter { !it.equals(claim) }
			.all { !it.overlapsWith(claim) }
	}

	println(notOverlappingClaims)
}
