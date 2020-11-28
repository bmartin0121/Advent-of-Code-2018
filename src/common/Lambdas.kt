package common

fun <T> List<T>.mutate(block: (_: MutableList<T>) -> Unit): List<T> {
    val copiedList = this.toMutableList()
    block(copiedList)
    return copiedList
}
