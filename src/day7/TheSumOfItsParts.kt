package day7

import common.readLines
import day7.Graph.Node

val ADJACENCY_REGEX = """Step ([A-Z]) must be finished before step ([A-Z]) can begin.""".toRegex()

fun main(args: Array<String>) {
    val nodes = HashSet<Node>()
    readLines("day7.txt")
        .map { ADJACENCY_REGEX.find(it)!!.destructured }
        .map { it.component1()[0] to it.component2()[0] }
        .forEach { addArc(it, nodes) }
    println(Graph(nodes).combinedBreathFirstSearch().fold("") { acc, node -> acc + node.id })
}

fun addArc(pair: Pair<Char, Char>, nodes: HashSet<Node>) {
    val from = nodes.find { it.id == pair.first } ?: Node(pair.first)
    val to = nodes.find { it.id == pair.second } ?: Node(pair.second)
    from.connect(to)
    nodes.remove(from)
    nodes.add(from)
    nodes.remove(to)
    nodes.add(to)
}
