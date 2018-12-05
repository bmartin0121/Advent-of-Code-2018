package day4

class Guard(val id: Int) {

	var minutesAsleepCount = HashMap<Int, Int>()
			private set
	
	fun asleepBetween(range: IntRange) {
		for (minute in range) {
			minutesAsleepCount[minute] = minutesAsleepCount.getOrDefault(minute, 0) + 1
		}
	}

	fun getAsleepTime(): Int = minutesAsleepCount.values.sum()

	fun mostAsleepMinute(): Int = minutesAsleepCount.maxBy { it.value }!!.key

	override fun equals(other: Any?): Boolean {
		return other is Guard && other.id == id
	}

	override fun hashCode(): Int {
		return id
	}

	override fun toString(): String {
		return "Guard[id=$id]"
	}
}
