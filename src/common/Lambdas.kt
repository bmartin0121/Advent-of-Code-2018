package common

fun runOnCopyList(list: List<Int>, block: (_: MutableList<Int>) -> Unit): List<Int> {
    val copiedList = list.toMutableList()
    block(copiedList)
    return copiedList
}
