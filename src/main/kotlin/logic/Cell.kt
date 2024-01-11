package org.example.logic

class Cell(val x: Int, val y: Int, private var mark: Boolean = false) {
    fun getMarked(): Boolean {
        return mark
    }

    fun marked() {
        this.mark = true;
    }

    override fun equals(other: Any?): Boolean = when (other) {
        !is Cell -> false
        else -> x == other.x && y == other.y
    }
}
