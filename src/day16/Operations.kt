package day16

import common.mutate

sealed class Operation {
    abstract fun execute(a: Int, b: Int, c: Int, register: List<Int>): List<Int>
    override fun toString() = this.javaClass.simpleName
}

class Addr : Operation() {
    override fun execute(a: Int, b: Int, c: Int, register: List<Int>): List<Int> {
        return register.mutate {
            it[c] = register[a] + register[c]
        }
    }
}

class Addi : Operation() {
    override fun execute(a: Int, b: Int, c: Int, register: List<Int>): List<Int> {
        return register.mutate {
            it[c] = register[a] + b
        }
    }
}

class Mulr : Operation() {
    override fun execute(a: Int, b: Int, c: Int, register: List<Int>): List<Int> {
        return register.mutate {
            it[c] = register[a] * register[b]
        }
    }
}

class Muli : Operation() {
    override fun execute(a: Int, b: Int, c: Int, register: List<Int>): List<Int> {
        return register.mutate {
            it[c] = register[a] * b
        }
    }
}

class Banr : Operation() {
    override fun execute(a: Int, b: Int, c: Int, register: List<Int>): List<Int> {
        return register.mutate {
            it[c] = register[a] and register[b]
        }
    }
}

class Bani : Operation() {
    override fun execute(a: Int, b: Int, c: Int, register: List<Int>): List<Int> {
        return register.mutate {
            it[c] = register[a] and b
        }
    }
}

class Borr : Operation() {
    override fun execute(a: Int, b: Int, c: Int, register: List<Int>): List<Int> {
        return register.mutate {
            it[c] = register[a] or register[b]
        }
    }
}

class Bori : Operation() {
    override fun execute(a: Int, b: Int, c: Int, register: List<Int>): List<Int> {
        return register.mutate {
            it[c] = register[a] or b
        }
    }
}

class Setr : Operation() {
    override fun execute(a: Int, b: Int, c: Int, register: List<Int>): List<Int> {
        return register.mutate {
            it[c] = register[a]
        }
    }
}

class Seti : Operation() {
    override fun execute(a: Int, b: Int, c: Int, register: List<Int>): List<Int> {
        return register.mutate {
            it[c] = a
        }
    }
}

class Gtir : Operation() {
    override fun execute(a: Int, b: Int, c: Int, register: List<Int>): List<Int> {
        return register.mutate {
            it[c] = if (a > register[b]) {
                1
            } else {
                0
            }
        }
    }
}

class Gtri : Operation() {
    override fun execute(a: Int, b: Int, c: Int, register: List<Int>): List<Int> {
        return register.mutate {
            it[c] = if (register[a] > b) {
                1
            } else {
                0
            }
        }
    }
}

class Gtrr : Operation() {
    override fun execute(a: Int, b: Int, c: Int, register: List<Int>): List<Int> {
        return register.mutate {
            it[c] = if (register[a] > register[b]) {
                1
            } else {
                0
            }
        }
    }
}

class Eqir : Operation() {
    override fun execute(a: Int, b: Int, c: Int, register: List<Int>): List<Int> {
        return register.mutate {
            it[c] = if (a == register[b]) {
                1
            } else {
                0
            }
        }
    }
}

class Eqri : Operation() {
    override fun execute(a: Int, b: Int, c: Int, register: List<Int>): List<Int> {
        return register.mutate {
            it[c] = if (register[a] == b) {
                1
            } else {
                0
            }
        }
    }
}

class Eqrr : Operation() {
    override fun execute(a: Int, b: Int, c: Int, register: List<Int>): List<Int> {
        return register.mutate {
            it[c] = if (register[a] == register[b]) {
                1
            } else {
                0
            }
        }
    }
}
