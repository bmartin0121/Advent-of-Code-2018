package day2

class BoxId {

	val id: String
	val charCount: Map<Char, Int>

	constructor(id: String) {
		this.id = id
		this.charCount = id.asIterable()
			.distinct()
			.map { char -> char to id.asIterable().count { char == it } }
			.toMap()
	}
	
	override fun toString(): String = id

	fun containsALetterExactTimes(times: Int): Boolean = charCount.containsValue(times)

	fun differentIndeces(other: BoxId): List<Int> {
		val result = ArrayList<Int>()
		id.forEachIndexed { index, char ->
			if (other.id[index] != char) result.add(index)
		}
		return result
	}

}