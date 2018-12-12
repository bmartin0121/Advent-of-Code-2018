package common

class CircularList<T>(val seed: MutableList<T>) : MutableList<T> {

    override fun add(element: T): Boolean = seed.add(element)

    override fun add(index: Int, element: T) = seed.add(index, element)

    override fun addAll(index: Int, elements: Collection<T>): Boolean = seed.addAll(index, elements)

    override fun addAll(elements: Collection<T>): Boolean = addAll(elements)

    override fun clear() {
        seed.clear()
    }

    override fun remove(element: T): Boolean = seed.remove(element)

    override fun removeAll(elements: Collection<T>): Boolean = seed.removeAll(elements)

    override fun removeAt(index: Int): T = seed.removeAt(index)

    override fun retainAll(elements: Collection<T>): Boolean = seed.retainAll(elements)

    override fun set(index: Int, element: T): T = seed.set(index, element)

    override val size: Int
        get() = seed.size

    override fun contains(element: T): Boolean = seed.contains(element)

    override fun containsAll(elements: Collection<T>): Boolean = seed.containsAll(elements)

    override fun get(index: Int): T = seed.get(index % size)

    override fun indexOf(element: T): Int = seed.indexOf(element)

    override fun isEmpty(): Boolean = seed.isEmpty()

    override fun iterator(): Iterator {
        return Iterator()
    }

    override fun lastIndexOf(element: T): Int = seed.lastIndexOf(element)

    override fun listIterator(): MutableListIterator<T> {
        TODO()
    }

    override fun listIterator(index: Int): MutableListIterator<T> {
        TODO()
    }

    override fun subList(fromIndex: Int, toIndex: Int): MutableList<T> = seed.subList(fromIndex, toIndex)

    inner class Iterator : MutableIterator<T> {
        override fun remove() {
            removeAt(index)
        }

        var index = 0;

        override fun next(): T = get(index++);

        override fun hasNext(): Boolean = true
    }
}