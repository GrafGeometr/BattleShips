package org.example.logic

import javax.print.attribute.standard.MediaSize.Other

class Cell(val x: Int, val y: Int, private var mark: Boolean = false) {

    fun getMarked(): Boolean {
        return mark
    }

    fun getX(): Int {
        return x
    }

    fun getY(): Int {
        return y
    }

    fun marked() {
        this.mark = true;
    }

    override fun equals(other: Any?): Boolean = when (other) {
        !is Cell -> false
        else -> x == other.x && y == other.y
    }
}
