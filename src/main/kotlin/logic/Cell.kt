package org.example.logic

class Cell(val x: Int, val y: Int, private var mark: Boolean = false) {
    fun isMarked(): Boolean {
        return mark
    }

    fun shot() {
        mark = true;
    }

    override fun equals(other: Any?): Boolean = when (other) {
        !is Cell -> false
        else -> x == other.x && y == other.y
    }

    override fun hashCode(): Int = x * 31 + y
}
