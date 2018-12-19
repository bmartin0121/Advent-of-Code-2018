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
    var collisionPlace = getCollisionPlaces(carts, track)[0]
    print("${collisionPlace!!.second},${collisionPlace!!.first}")
}

private fun getCollisionPlaces(carts: Set<Cart>, track: Track): List<Pair<Int, Int>> {
    var collisionPlaces = mutableListOf<Pair<Int, Int>>()
    while (collisionPlaces.isEmpty()) {
        carts.sortedBy { it.position.first }
            .forEach {
                track.move(it)
                val collision = carts.find { it.collidesWithAny(carts.minus(it)) }?.position
                if (collision != null) {
                    collisionPlaces.add(collision)
                }
            }
    }
    return collisionPlaces
}
