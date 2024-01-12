package logic

import org.example.logic.Cell
import org.example.logic.Ship
import kotlin.test.Test

class ShipTest {
    @Test
    fun constructorFromCoordinatesTestCells1() {
        val ship = Ship(0, 0, 0, 0)
        assert(Cell(0, 0) in ship.getCells())
        assert(Cell(1, 1) !in ship.getCells())
        assert(Cell(1, 0) !in ship.getCells())
        assert(Cell(0, 1) !in ship.getCells())
    }

    @Test
    fun constructorFromCoordinatesTestArea1() {
        val ship = Ship(0, 0, 0, 0)
        assert(Cell(0, 0) !in ship.getArea())

        assert(Cell(1, 1) in ship.getArea())
        assert(Cell(-1, -1) in ship.getArea())
        assert(Cell(-1, 1) in ship.getArea())
        assert(Cell(1, -1) in ship.getArea())
        assert(Cell(-1, 0) in ship.getArea())
        assert(Cell(0, -1) in ship.getArea())
        assert(Cell(1, 0) in ship.getArea())
        assert(Cell(0, 1) in ship.getArea())

        assert(Cell(2, 2) !in ship.getArea())
    }

    @Test
    fun constructorFromCoordinatesTestCells2() {
        val ship = Ship(0, 0, 1, 0)  // (0, 0) (1, 0)

        assert(Cell(0, 0) in ship.getCells())
        assert(Cell(1, 0) in ship.getCells())
        assert(Cell(0, 1) !in ship.getCells())
        assert(Cell(1, 1) !in ship.getCells())
        assert(Cell(2, 0) !in ship.getCells())
        assert(Cell(2, 1) !in ship.getCells())
    }

    // TODO : Add more tests
}
