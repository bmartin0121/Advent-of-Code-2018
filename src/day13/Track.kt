package day13

import java.lang.IllegalArgumentException

class Track(val components: Map<Pair<Int, Int>, Component>) {

    fun move(cart: Cart) {
        cart.moveToPosition(components[cart.position]!!.nextPosition(cart))
    }

    public enum class Component {
        HORIZONTAL {
            override fun nextPosition(cart: Cart) =
                when (cart.facing) {
                    Cart.Direction.LEFT -> cart.position.first to cart.position.second - 1
                    Cart.Direction.RIGHT -> cart.position.first to cart.position.second + 1
                    else -> throw IllegalArgumentException("It's impossible to go ${cart.facing} on a $this component at ${cart.position}")
                }
        },
        VERTICAL {
            override fun nextPosition(cart: Cart) =
                when (cart.facing) {
                    Cart.Direction.UP -> cart.position.first - 1 to cart.position.second
                    Cart.Direction.DOWN -> cart.position.first + 1 to cart.position.second
                    else -> throw IllegalArgumentException("It's impossible to go ${cart.facing} on a $this component at ${cart.position}")
                }
        },
        LEFT_TURN {
            override fun nextPosition(cart: Cart) =
                when (cart.facing) {
                    Cart.Direction.UP -> {
                        cart.changeDirection(Cart.Direction.LEFT)
                        HORIZONTAL.nextPosition(cart)
                    }
                    Cart.Direction.LEFT -> {
                        cart.changeDirection(Cart.Direction.UP)
                        VERTICAL.nextPosition(cart)
                    }
                    Cart.Direction.DOWN -> {
                        cart.changeDirection(Cart.Direction.RIGHT)
                        HORIZONTAL.nextPosition(cart)
                    }
                    Cart.Direction.RIGHT -> {
                        cart.changeDirection(Cart.Direction.DOWN)
                        VERTICAL.nextPosition(cart)
                    }
                }
        },
        RIGHT_TURN {
            override fun nextPosition(cart: Cart) =
                when (cart.facing) {
                    Cart.Direction.UP -> {
                        cart.changeDirection(Cart.Direction.RIGHT)
                        HORIZONTAL.nextPosition(cart)
                    }
                    Cart.Direction.LEFT -> {
                        cart.changeDirection(Cart.Direction.DOWN)
                        VERTICAL.nextPosition(cart)
                    }
                    Cart.Direction.DOWN -> {
                        cart.changeDirection(Cart.Direction.LEFT)
                        HORIZONTAL.nextPosition(cart)
                    }
                    Cart.Direction.RIGHT -> {
                        cart.changeDirection(Cart.Direction.UP)
                        VERTICAL.nextPosition(cart)
                    }
                }
        },
        INTERSECTION {
            override fun nextPosition(cart: Cart): Pair<Int, Int> {
                val nextPos = when (cart.nextStepInIntersection) {
                    Cart.IntersectionCorssing.STRAIGHT -> when (cart.facing) {
                        Cart.Direction.UP, Cart.Direction.DOWN -> VERTICAL.nextPosition(cart)
                        Cart.Direction.LEFT, Cart.Direction.RIGHT -> HORIZONTAL.nextPosition(cart)
                    }
                    Cart.IntersectionCorssing.LEFT -> when (cart.facing) {
                        Cart.Direction.UP, Cart.Direction.DOWN -> LEFT_TURN.nextPosition(cart)
                        Cart.Direction.RIGHT, Cart.Direction.LEFT -> RIGHT_TURN.nextPosition(cart)
                    }
                    Cart.IntersectionCorssing.RIGHT -> when (cart.facing) {
                        Cart.Direction.UP, Cart.Direction.DOWN -> RIGHT_TURN.nextPosition(cart)
                        Cart.Direction.RIGHT, Cart.Direction.LEFT -> LEFT_TURN.nextPosition(cart)
                    }
                }
                cart.crossedIntersection()
                return nextPos
            }
        },
        EMPTY {
            override fun nextPosition(cart: Cart): Pair<Int, Int> {
                throw IllegalArgumentException("A cart has been derailed at position ${cart.position}")
            }
        };

        abstract fun nextPosition(cart: Cart): Pair<Int, Int>;
    }

}