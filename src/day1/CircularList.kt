package day1

class CircularList<T> : List<T> {
	val seed: List<T>

	override val size: Int
		get() = seed.size

	constructor(seed: List<T>) {
		this.seed = seed;
	}

	override fun contains(element: T): Boolean = seed.contains(element)

	override fun containsAll(elements: Collection<T>): Boolean = seed.containsAll(elements)

	override fun get(index: Int): T = seed.get(index % size)

	override fun indexOf(element: T): Int = seed.indexOf(element)

	override fun isEmpty(): Boolean = seed.isEmpty()

	override fun iterator(): Iterator {
		return Iterator()
	}

	override fun lastIndexOf(element: T): Int = seed.lastIndexOf(element)

	override fun listIterator(): ListIterator<T> {
		TODO()
	}

	override fun listIterator(index: Int): ListIterator<T> {
		TODO()
	}

	override fun subList(fromIndex: Int, toIndex: Int): List<T> = seed.subList(fromIndex, toIndex)

	inner class Iterator : kotlin.collections.Iterator<T> {
		var index = 0;

		override fun next(): T = get(index++);

		override fun hasNext(): Boolean = true
	}
}