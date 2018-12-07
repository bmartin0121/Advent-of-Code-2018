package day7

import java.util.concurrent.atomic.AtomicInteger

class Graph(val nodes: HashSet<Node>) {

    fun combinedBreathFirstSearch(): List<Node> {
        nodes.forEach { it.color = Node.Color.WHITE }
        var queue = nodes.filter { !it.connectedToAny(nodes) }.sortedBy { it.id }
        val start = queue.first()
        start.color = Node.Color.GREY
        var result = listOf<Node>()
        while (!queue.isEmpty()) {
            val node = queue.sortedBy { it.id }.first()
            queue = queue.minus(node)
            node.color = Node.Color.BLACK
            result = result.plus(node)
            for (adjNode in node.adjacents) {
                if (adjNode.color == Node.Color.WHITE && adjNode.onlyBlackAdjacents(nodes)) {
                    adjNode.color = Node.Color.GREY
                    queue = queue.plus(adjNode)
                }
            }
        }
        return result
    }

    // First I misunderstood the task but it seems to be a working topological order implementation
    // so I kept this here :)
    fun topologicalOrder(): List<Node> {
        nodes.forEach { it.color = Node.Color.WHITE }
        val ordered = ArrayList<Node>()
        val startNodes = nodes.filter { !it.connectedToAny(nodes) }
            .sortedByDescending { it.id }
        for (node in startNodes) {
            if (node.color == Node.Color.WHITE) {
                visit(node, ordered)
            }
        }
        return ordered.reversed()
    }

    private fun visit(node: Node, ordered: ArrayList<Node>) {
        node.color = Node.Color.GREY
        for (adjNode in node.adjacents.sortedByDescending { it.id }) {
            if (adjNode.color == Node.Color.WHITE) {
                visit(adjNode, ordered)
            }
        }
        node.color = Node.Color.BLACK
        ordered.add(node)
    }

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

        override fun toString(): String = id.toString()

        enum class Color {
            WHITE, GREY, BLACK
        }
    }
}
