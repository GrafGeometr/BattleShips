package logic

import org.example.logic.Cell
import kotlin.test.Test

class CellTest {
    @Test
    fun markedAndShowTest() {
        val cell = Cell(1, 2)
        assert(!cell.isMarked())
        cell.shot()
        assert(cell.isMarked())
    }

    @Test
    fun testEquals() {
        assert(Cell(1, 2) == Cell(1, 2))
        assert(Cell(1, 2) != Cell(1, 3))
        assert(Cell(1, 2) != Cell(3, 2))
        assert(Cell(1, 2) != Cell(2, 1))
        assert(Cell(10, 10) == Cell(10, 10))
    }
}
