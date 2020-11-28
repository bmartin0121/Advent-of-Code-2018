package day16

import common.readLines

fun main(args: Array<String>) {
    val opcodeToOperation = getOpcodesToOperationMap()
    println(opcodeToOperation)
    var register = listOf(0, 0, 0, 0)
    readLines("day16_2.txt").forEach {
        val (opt, a, b, c) = INPUT_REGEX.find(it)!!.destructured
        register = opcodeToOperation[opt]!!.execute(a.toInt(), b.toInt(), c.toInt(), register)
    }
    println(register[0])
}


fun getOpcodesToOperationMap(): Map<String, Operation> {
    val lines = readLines("day16_1.txt")
    val operations = Operation::class.sealedSubclasses.map { it.constructors.first().call() }
    val opcodeToOperation = mutableMapOf<String, MutableSet<Operation>>()
    for (i in lines.indices step 4) {
        val before = BEFORE_REGEX.find(lines[i])!!.destructured
                .toList()
                .map { it.toInt() }
                .toMutableList()
        val (opt, a, b, c) = INPUT_REGEX.find(lines[i + 1])!!.destructured
        val after = AFTER_REGEX.find(lines[i + 2])!!.destructured
                .toList()
                .map { it.toInt() }

        val matchedOps = mutableSetOf<Operation>()
        for (operation in operations) {
            var actualAfter = operation.execute(a.toInt(), b.toInt(), c.toInt(), before)
            if (actualAfter == after) {
                matchedOps += operation
            }
        }
        opcodeToOperation[opt] = if (opcodeToOperation.containsKey(opt)) {
            opcodeToOperation[opt]!!.filter { it in matchedOps }.toMutableSet()
        } else {
            matchedOps
        }
    }
    val alreadyDone = mutableSetOf<String>()
    while (opcodeToOperation.values.any { it.size > 1 }) {
        val sureOperation = opcodeToOperation.entries.find { it.value.size == 1 && it.key !in alreadyDone }!!
        alreadyDone += sureOperation.key
        opcodeToOperation.values.filter { it.size > 1 }.forEach { it.remove(sureOperation.value.first()!!) }
    }
    return opcodeToOperation.map { it.key to it.value.first()!! }.toMap()
}
