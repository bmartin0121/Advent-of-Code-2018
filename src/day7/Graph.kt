package day7

import java.util.concurrent.atomic.AtomicInteger

class Graph(val nodes: Set<Node>) {

    fun getNodeWithId(id: Char): Node? = nodes.find { it.id == id }

    fun getRootNodes(): List<Node> = nodes.filter { !it.connectedToAny(nodes) }.sortedBy { it.id }

    fun combinedBreathFirstSearch(): List<Node> {
        nodes.forEach { it.color = Node.Color.WHITE }
        var queue = getRootNodes().toMutableList()
        val start = queue.first()
        start.color = Node.Color.GREY
        var result = mutableListOf<Node>()
        while (!queue.isEmpty()) {
            val node = queue.sortedBy { it.id }.first()
            queue.remove(node)
            node.color = Node.Color.BLACK
            result.add(node)
            for (adjNode in node.adjacents) {
                if (adjNode.color == Node.Color.WHITE && adjNode.onlyBlackAdjacents(nodes)) {
                    adjNode.color = Node.Color.GREY
                    queue.add(adjNode)
                }
            }
        }
        return result
    }

    // First I misunderstood the task but it seems to be a working topological order implementation
    // so I kept this here :)
    fun topologicalOrder(): List<Node> {
        nodes.forEach { it.color = Node.Color.WHITE }
        val ordered = mutableListOf<Node>()
        val startNodes = nodes.filter { !it.connectedToAny(nodes) }
            .sortedByDescending { it.id }
        for (node in startNodes) {
            if (node.color == Node.Color.WHITE) {
                visit(node, ordered)
            }
        }
        return ordered.reversed()
    }

    private fun visit(node: Node, ordered: MutableList<Node>) {
        node.color = Node.Color.GREY
        for (adjNode in node.adjacents.sortedByDescending { it.id }) {
            if (adjNode.color == Node.Color.WHITE) {
                visit(adjNode, ordered)
            }
        }
        node.color = Node.Color.BLACK
        ordered.add(node)
    }

    override fun toString(): String = "Graph [nodes=$nodes]"

    class Node(val id: Char) {

        var adjacents = HashSet<Node>()
            private set

        var color = Color.WHITE

        fun connect(other: Node) {
            adjacents.add(other)
        }

        fun connectedToAny(nodes: Set<Node>): Boolean = nodes.any { it.adjacents.contains(this) }

        fun onlyBlackAdjacents(nodes: Set<Node>): Boolean = nodes.filter { it.adjacents.contains(this) }.all { it.color == Color.BLACK }

        override fun equals(other: Any?): Boolean = other is Node && this.id == other.id

        override fun hashCode(): Int = id.hashCode()

        override fun toString(): String = "Node [$id, $color]"

        enum class Color {
            WHITE, GREY, BLACK
        }
    }
}
