package day8

class Node {

    var children: List<Node> = emptyList()
        private set
    var metadata: List<Int> = emptyList()
        private set

    fun addChild(node: Node) {
        children = children.plus(node)
    }

    fun addMetadata(data: Int) {
        metadata = metadata.plus(data)
    }

    fun getValue(): Int = if (children.isEmpty()) {
        metadata.sum()
    } else {
        metadata
            .filter { it in (1..children.size) }
            .map { children[it - 1] }
            .sumBy { it.getValue() }
    }

    fun sumOfAllMetadata(): Int = metadata.sum() + children.sumBy { it.sumOfAllMetadata() }

    override fun toString(): String = "Node [metadata=$metadata, children=$children]"
}
