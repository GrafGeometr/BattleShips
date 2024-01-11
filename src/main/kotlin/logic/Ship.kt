package org.example.logic

class Ship(
    private var ship: List<Cell> = emptyList(),
    private var area: List<Cell> = emptyList(),
    private var mark: Int = 0,
) {
    constructor(x1: Int, y1: Int, x2: Int, y2: Int) : this() {
        for (x in x1..x2) for (y in y1..y2) ship += Cell(x, y)
        listOf(x1 - 1, x2 + 1).forEach { x ->
            (y1 - 1..y2 + 1).forEach { y -> area += Cell(x, y) }
        }
        listOf(y1 - 1, y2 + 1).forEach { y ->
            (x1 - 1..x2 + 1).forEach { x -> area += Cell(x, y) }
        }
    }


    private fun allCells(): List<Cell> = ship + area


    fun isAlive(): Boolean {
        return getSize() != mark
    }

    fun getSize(): Int = ship.size

    fun getMarkedCellsCount(): Int = mark

    fun getMarkedCells(): List<Cell> = ship.filter { cell -> cell.isMarked() }

    fun getMarkedArea(): List<Cell> = area.filter { cell -> cell.isMarked() }

    fun contains(x: Int, y: Int): Boolean = contains(Cell(x, y))

    infix fun contains(cell: Cell): Boolean = ship.contains(cell)

    infix fun conflicts(v: Ship): Boolean =
        allCells().any { cell -> v contains cell }

    fun shot(cell: Cell) {
        for (t in ship) {
            if (t == cell) {
                t.shot()
                mark += 1
                if (!isAlive()) for (c in area) c.shot()
                return
            }
        }

        for (t in area) {
            if (t == cell) t.shot()
            return
        }
    }
}