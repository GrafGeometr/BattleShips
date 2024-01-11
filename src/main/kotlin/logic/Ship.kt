package org.example.logic

import kotlin.math.min
import kotlin.math.max

class Ship(private var ship: List<Cell>, private var area: List<Cell>) {
    fun isAlive(): Boolean {
        return getMarkedShipCnt() != getLen()
    }

    fun getLen(): Int {
        return ship.size
    }

    private fun getMarkedShipCnt(): Int {
        var res = 0
        for (t in ship)
            if (t.getMarked())
                res += 1
        return res
    }

    fun getMarkedShip(): List<Cell> {
        val res: List<Cell> = emptyList()
        for (t in ship)
            if (t.getMarked())
                res.addLast(t)
        return res
    }

    fun getMarkedArea(): List<Cell> {
        if (!isAlive()) {
            for (t in area)
                t.marked()
            return area
        }
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
}