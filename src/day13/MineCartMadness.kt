package day13

import common.readLines

fun main(args: Array<String>) {
    val carts = HashSet<Cart>()
    val components = HashMap<Pair<Int, Int>, Track.Component>()
    readLines("day13.txt")
        .forEachIndexed { row, line ->
            line.forEachIndexed { col, char ->
                val position = row to col
                components[position] = when (char) {
                    '-' -> Track.Component.HORIZONTAL
                    '|' -> Track.Component.VERTICAL
                    '/' -> Track.Component.RIGHT_TURN
                    '\\' -> Track.Component.LEFT_TURN
                    '+' -> Track.Component.INTERSECTION
                    '^' -> {
                        carts.add(Cart(position, Cart.Direction.UP))
                        Track.Component.VERTICAL
                    }
                    'v' -> {
                        carts.add(Cart(position, Cart.Direction.DOWN))
                        Track.Component.VERTICAL
                    }
                    '>' -> {
                        carts.add(Cart(position, Cart.Direction.RIGHT))
                        Track.Component.HORIZONTAL
                    }
                    '<' -> {
                        carts.add(Cart(position, Cart.Direction.LEFT))
                        Track.Component.HORIZONTAL
                    }
                    else -> Track.Component.EMPTY
                }
            }
        }
    val track = Track(components)

    /**
     * First part
     */
    val collisionPlace = getCollisionPlaces(carts, track)[0]
    println("${collisionPlace!!.second},${collisionPlace!!.first}")

    /**
     * Second part
     * NB! Cart objects are mutable, remove First part for actual result
     */
    val survivor = getLastSurvivor(carts, track)
    println("${survivor.position.second},${survivor.position.first}")
}

private fun getCollisionPlaces(carts: Set<Cart>, track: Track): List<Pair<Int, Int>> {
    var collisionPlaces = mutableListOf<Pair<Int, Int>>()
    while (collisionPlaces.isEmpty()) {
        carts.sortedBy { it.position.first }
            .forEach {
                track.move(it)
                val collision = it.collisionPartner(carts.minus(it))?.position
                if (collision != null) {
                    collisionPlaces.add(collision)
                }
            }
    }
    return collisionPlaces
}

private fun getLastSurvivor(carts: Set<Cart>, track: Track): Cart {
    val survivors = carts.toMutableSet()
    while (survivors.size > 1) {
        survivors.sortedBy { it.position.first }
            .forEach {
                track.move(it)
                val collision = it.collisionPartner(survivors.minus(it))
                if (collision != null) {
                    survivors.remove(it)
                    survivors.remove(collision)
                }
            }
    }
    return survivors.first()
}