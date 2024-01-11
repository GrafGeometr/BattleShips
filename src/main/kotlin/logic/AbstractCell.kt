package org.example.logic

abstract class AbstractCell(val x: Int, val y: Int) {
    abstract override fun toString(): String

    abstract override fun equals(other: Any?): Boolean

    abstract fun getShip(): AbstractShip?

    abstract fun isEmpty(): Boolean

    abstract fun makeShot(): Unit

    abstract infix fun isInside(other: AbstractShip): Boolean
}


