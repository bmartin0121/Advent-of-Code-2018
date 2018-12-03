package day3

val INPUT_FORMAT_REGEX = """#(\d+) @ (\d+),(\d+): (\d+)x(\d+)""".toRegex()

class Claim(val id: String, val leftUpCorner: Pair<Int, Int>, val width: Int, val height: Int) {

	val rightUpCorner = leftUpCorner.first + width - 1 `to` leftUpCorner.second
	val rightDownCorner = leftUpCorner.first + width - 1 `to` leftUpCorner.second + height - 1
	val leftDownCorner = leftUpCorner.first `to` leftUpCorner.second + height - 1
	
	fun containsPoint(point: Pair<Int, Int>): Boolean {
		return leftUpCorner.first <= point.first &&
				leftUpCorner.second <= point.second &&
				leftUpCorner.first + height >= point.first &&
				leftUpCorner.second + width >= point.second
	}
	
	override fun toString(): String = "Claim[id=${id}, leftCorner=${leftUpCorner}, width=${width}, height=${height}]"
	
}

fun claimFromFormattedString(input: String): Claim {
	val matcherResult = INPUT_FORMAT_REGEX.find(input)
	val (id, leftCornerX, leftCornerY, width, height) = matcherResult!!.destructured
	return Claim(id, leftCornerX.toInt() `to` leftCornerY.toInt(), width.toInt(), height.toInt())
}
