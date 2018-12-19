package day13

class Cart(var position: Pair<Int, Int>, var facing: Direction) {

    var nextStepInIntersection = IntersectionCorssing.LEFT
        private set

    fun moveToPosition(position: Pair<Int, Int>) {
        this.position = position
    }

    fun changeDirection(direction: Direction) {
        facing = direction
    }

    fun crossedIntersection() {
        nextStepInIntersection = nextStepInIntersection.crossed()
    }

    fun collisionPartner(others: Set<Cart>): Cart? = others.find { this.collidesWith(it) }

    fun collidesWith(other: Cart): Boolean = other.position == position

    public enum class Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    public enum class IntersectionCorssing {
        LEFT {
            override fun crossed() = STRAIGHT
        },
        STRAIGHT {
            override fun crossed() = RIGHT
        },
        RIGHT {
            override fun crossed() = LEFT
        };

        abstract fun crossed(): IntersectionCorssing;
    }

}
