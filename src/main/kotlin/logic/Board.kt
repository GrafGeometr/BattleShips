package org.example.logic

import java.util.Random

class Board(
    private var ships: List<Ship> = emptyList(),
    private var misses: List<Cell> = emptyList(),
) {
    private val size = 10
    private var aliveShips = 10

    constructor(field: Array<Array<Boolean>>) : this() {

        (4 downTo 1).forEach { shipSize ->
            // horizontal ship
            for (y in 0..<size) for (x in 0..<size - shipSize) {
                if ((0..<shipSize).all { field[x + it][y] }) {
                    ships += Ship(x, y, x + shipSize - 1, y)
                }
            }
            // vertical ship
            for (x in 0..<size) for (y in 0..<size - shipSize) {
                if ((0..<shipSize).all { field[x][y + it] }) {
                    ships += Ship(x, y, x, y + shipSize - 1)
                }
            }
        }
    }

    fun showString(): String {
        val array =
            Array(size) {
                Array(size) { "-" }
            }
        for (ship in ships) {
            for (cell in ship.getMarkedArea()) array[cell.y][cell.x] = "*"
            for (cell in ship.getMarkedCells()) array[cell.y][cell.x] = "X"
        }
        for (miss in misses) array[miss.y][miss.x] = "*"
        return array.joinToString("\n") {
            it.joinToString("")
        }
    }

    fun showStringDetailed(): String {
        val array =
            Array(size) {
                Array(size) { "-" }
            }
        for (ship in ships) {
            for (cell in ship.getCells()) array[cell.y][cell.x] = "S"
            for (cell in ship.getMarkedArea()) array[cell.y][cell.x] = "*"
            for (cell in ship.getMarkedCells()) array[cell.y][cell.x] = "X"
        }
        for (miss in misses) array[miss.y][miss.x] = "*"
        return array.joinToString("\n") {
            it.joinToString("")
        }
    }

    private fun checkNoShipsCollisions(): Boolean {
        for (ship1 in ships) for (ship2 in ships) {
            if (ship1 == ship2) continue
            if (ship1 conflicts ship2) return false
        }
        return true
    }

    fun locateShipsRandomly() {
        ships = emptyList()

        val shipSizes = intArrayOf(4, 3, 3, 2, 2, 2, 1, 1, 1, 1)
        var shipIndex = 0

        val random = Random()
        while (shipIndex < shipSizes.size) {
            val shipSize = shipSizes[shipIndex]
            val ship =
                if (random.nextBoolean()) {
                    placeShipHorizontally(shipSize)
                } else {
                    placeShipVertically(shipSize)
                }

            if (checkNoShipsCollisions()) {
                ships += ship
                shipIndex++
            } else {
                if (ships.size >= 2) {
                    ships.removeLast()
                    if (random.nextBoolean()) {
                        ships.removeLast()
                        shipIndex--
                    }
                }
            }
        }
    }

    private fun placeShipVertically(shipSize: Int): Ship {
        val x = Random().nextInt(size)
        val y = Random().nextInt(size - shipSize)
        return Ship(x, y, x, y + shipSize - 1)
    }

    private fun placeShipHorizontally(shipSize: Int): Ship {
        val x = Random().nextInt(size - shipSize)
        val y = Random().nextInt(size)
        return Ship(x, y, x + shipSize - 1, y)
    }

    fun isAlive(): Boolean = aliveShips != 0

    fun gameIsLost(): Boolean = aliveShips == 0

    fun getAliveShipsCount(): Int = getAliveShips().size

    fun getShips(): List<Ship> = ships

    fun getAliveShips(): List<Ship> = ships.filter { ship -> ship.isAlive() }

    fun getDestroyedShips(): List<Ship> = ships.filter { ship -> !ship.isAlive() }

    fun shot(cell: Cell) {
        var ok = false
        for (t in ships) {
            if (t.contains(cell) || t.containsArea(cell)) {
                ok = true
                if (t.shot(cell) && !t.isAlive()) {
                    aliveShips -= 1
                }
            }
        }
        if (ok) {
            return
        }
        misses.addLast(cell)
    }
}
