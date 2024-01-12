package org.example.logic

/**
 * @param mark Boolean
 * @property x Int
 * @property y Int
 */
class Cell(
    val x: Int,
    val y: Int,
    private var mark: Boolean = false,
) {
    /**
     * @return true if cell was shot and false otherwise
     */
    fun isMarked(): Boolean = mark

    /**
     * Shot the cell
     */
    fun shot() {
        mark = true
    }

    override fun equals(other: Any?): Boolean =
        when (other) {
            !is Cell -> false
            else -> x == other.x && y == other.y
        }

    override fun hashCode(): Int = x * 31 + y
}
