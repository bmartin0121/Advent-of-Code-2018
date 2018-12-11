package day7

import common.readLines
import day7.Graph.Node

val ADJACENCY_REGEX = """Step ([A-Z]) must be finished before step ([A-Z]) can begin.""".toRegex()
const val NUMBER_OF_WORKERS = 5

fun main(args: Array<String>) {
    /**
     * First part
     */
    val nodes = HashSet<Node>()
    readLines("day7.txt")
        .map { ADJACENCY_REGEX.find(it)!!.destructured }
        .map { it.component1()[0] to it.component2()[0] }
        .forEach { addArc(it, nodes) }
    val graph = Graph(nodes)
    val orderOfSteps = graph.combinedBreathFirstSearch().fold("") { acc, node -> acc + node.id }
    println(orderOfSteps)

    /**
     * Second part
     */
    val workers = (1..NUMBER_OF_WORKERS).map { Worker(it) }
    var assignable = graph.getRootNodes()
    var time = 0
    graph.nodes.forEach { it.color = Node.Color.WHITE }
    while (!assignable.isEmpty() || workers.any { it.isBusy() }) {
        var availableWorkers = workers.filter { !it.isBusy() }
        while (!availableWorkers.isEmpty() && !assignable.isEmpty()) {
            availableWorkers[0].assignWork(assignable[0])
            availableWorkers = availableWorkers.drop(1)
            assignable = assignable.drop(1)
        }

        workers.forEach { it.tick() }

        orderOfSteps
            .map { graph.getNodeWithId(it)!! }
            .filter { it.color == Graph.Node.Color.WHITE }
            .filter { it.onlyBlackAdjacents(graph.nodes) }
            .forEach { assignable = assignable.plus(it) }

        ++time
    }
    println(time)
}

private fun addArc(pair: Pair<Char, Char>, nodes: HashSet<Node>) {
    val from = nodes.find { it.id == pair.first } ?: Node(pair.first)
    val to = nodes.find { it.id == pair.second } ?: Node(pair.second)
    from.connect(to)
    nodes.remove(from)
    nodes.add(from)
    nodes.remove(to)
    nodes.add(to)
}
