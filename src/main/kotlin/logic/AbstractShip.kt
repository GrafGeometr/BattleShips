package org.example.logic

abstract class AbstractShip(val size: Int, val cells: List<AbstractCell>) {
    abstract override fun equals(other: Any?): Boolean

    abstract infix fun contains(other: AbstractCell): Boolean
    abstract infix fun collides(other: AbstractShip): Boolean

    abstract fun getLength(): Int

    abstract fun isAlive(): Boolean
}