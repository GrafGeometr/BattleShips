package org.example.logic

/**
 * @param ship
 * @param area
 * @param mark
 */
class Ship(
    private var ship: List<Cell> = emptyList(),
    private var area: List<Cell> = emptyList(),
    private var mark: Int = 0,
) {
    constructor(
        left: Int,
        top: Int,
        right: Int,
        down: Int,
    ) : this() {
        for (x in left..right) {
            for (y in top..down) {
                ship += Cell(x, y)
            }
        }
        listOf(left - 1, right + 1).forEach { x ->
            (top - 1..down + 1).forEach { y -> area += Cell(x, y) }
        }
        listOf(top - 1, down + 1).forEach { y ->
            (left - 1..right + 1).forEach { x -> area += Cell(x, y) }
        }
    }

    /**
     * @return all cells of the ship
     */
    private fun allCells(): List<Cell> = ship + area

    /**
     * @return true if ship is alive and false otherwise
     */
    fun isAlive(): Boolean = getSize() != mark

    /**
     * @return true if ship is not alive and false otherwise
     */
    fun isNotAlive(): Boolean = !isAlive()

    /**
     * @return size of the ship
     */
    fun getSize(): Int = ship.size

    /**
     * @return number of shouted cells
     */
    fun getMarkedCellsCount(): Int = mark

    /**
     * @return all cells of the ship
     */
    fun getCells(): List<Cell> = ship

    /**
     * @return all marked cells of the ship
     */
    fun getMarkedCells(): List<Cell> = ship.filter { cell -> cell.isMarked() }

    /**
     * @return all marked cells of the ship area
     */
    fun getMarkedArea(): List<Cell> = area.filter { cell -> cell.isMarked() }

    /**
     * @param x Int
     * @param y Int
     * @return true if the Cell(x, y) is in the ship and false otherwise
     */
    fun contains(
        x: Int,
        y: Int,
    ): Boolean = contains(Cell(x, y))

    /**
     * @param x Int
     * @param y Int
     * @return true if the Cell(x, y) is in the ship area and false otherwise
     */
    fun containsArea(
        x: Int,
        y: Int,
    ): Boolean = area.contains(Cell(x, y))

    /**
     * @param cell Cell
     * @return true if the Cell is in the ship and false otherwise
     */
    infix fun contains(cell: Cell): Boolean = ship.contains(cell)

    /**
     * @param cell Cell
     * @return true if the Cell is in the ship area and false otherwise
     */
    infix fun containsArea(cell: Cell): Boolean = area.contains(cell)

    /**
     * @param other
     * @return true if the ship conflicts with the other ship and false otherwise
     */
    infix fun conflicts(other: Ship): Boolean =
        allCells().any { cell -> other contains cell }

    /**
     * @param cell Cell
     * Makes a shot in the given cell and marks it if necessary
     * @return true if the shot is successful and false otherwise
     */
    fun shot(cell: Cell): Boolean {
        var success = false
        for (shipCell in ship) {
            if (shipCell == cell && !shipCell.isMarked()) {
                shipCell.shot()
                mark += 1
                success = true
                break
            }
        }

        if (!isAlive()) {
            for (areaCell in area) {
                areaCell.shot()
            }
        }
        if (success) {
            return true
        }

        for (areaCell in area) {
            if (areaCell == cell) {
                areaCell.shot()
            }
        }
        return false
    }

    /**
     * @return cells of the ship area
     */
    fun getArea(): List<Cell> = area
}
