package org.example.logic

import kotlin.math.min
import kotlin.math.max

class Ship(private var ship: List<Cell>, private var area: List<Cell>, private var mark: Int = 0) {
    fun isAlive(): Boolean {
        return getLen() != mark
    }

    fun getLen(): Int {
        return ship.size
    }

    fun getMarkedShipCnt(): Int {
        return mark
    }

    fun getMarkedShip(): List<Cell> {
        val res: List<Cell> = emptyList()
        for (t in ship)
            if (t.getMarked())
                res.addLast(t)
        return res
    }

    fun getMarkedArea(): List<Cell> {
        val res: List<Cell> = emptyList()
        for (t in area)
            if (t.getMarked())
                res.addLast(t)
        return res
    }

    fun contains(x: Int, y: Int): Boolean {
        val cell = Cell(x, y)
        for (t in ship)
            if (t == cell)
                return true
        return false
    }

    infix fun contains(cell: Cell): Boolean {
        for (t in ship)
            if (t == cell)
                return true
        return false
    }

    infix fun conflicts(v: Ship): Boolean {
        for (t in ship)
            if (v contains t)
                return true
        for (t in area)
            if (v contains t)
                return true
        return false
    }

    fun marked(cell: Cell) {
        for (t in ship) {
            if (t == cell) {
                t.marked()
                mark += 1
                if (!isAlive())
                    for (t in area)
                        t.marked()
            }
        }
        for (t in area) {
            if (t == cell)
                t.marked()
        }
    }
}