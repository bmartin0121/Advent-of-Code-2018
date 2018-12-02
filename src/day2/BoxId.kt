package day2

class BoxId {

	val id: String
	val charCount: Map<Char, Int>
	
	constructor(id: String) {
		this.id = id
		this.charCount = id.asIterable()
			.distinct()
		    .map { char -> char `to` id.asIterable().count { char == it } }
		    .toMap()
	}
		
	fun containsALetterExactTimes(times: Int): Boolean = charCount.containsValue(times)
	
}