package org.example.logic

import java.util.*

/**
 * @param ships
 * @param misses
 */
class Board(
    private var ships: MutableList<Ship> = mutableListOf(),
    private var misses: MutableList<Cell> = mutableListOf(),
) {
    private val size = 10
    private var aliveShips = 10

    /**
     * @param field
     * @return
     */
    fun locateShips(field: Array<Array<Boolean>>): Boolean {
        // TODO return false if cells are incorrect...
        (4 downTo 1).forEach { shipSize ->
            // vertical ship
            for (y in 0 ..< size) {
                for (x in 0 ..< size - shipSize) {
                    if ((0 ..< shipSize).all { field[x + it][y] }) {
                        ships += Ship(x, y, x + shipSize - 1, y)
                    }
                }
            }
            // horizontal ship
            for (x in 0 ..< size) {
                for (y in 0 ..< size - shipSize) {
                    if ((0 ..< shipSize).all { field[x][y + it] }) {
                        ships += Ship(x, y, x, y + shipSize - 1)
                    }
                }
            }
        }
        return true
    }

    /**
     * @return string representation of the board of other player
     */
    fun showString(): String {
        val array = Array(size) {
            Array(size) { "-" }
        }
        for (ship in ships) {
            for (cell in ship.getMarkedArea()) {
                array[cell.y][cell.x] = "*"
            }
            for (cell in ship.getMarkedCells()) {
                array[cell.y][cell.x] = "X"
            }
        }
        for (miss in misses) {
            array[miss.y][miss.x] = "*"
        }
        return array.joinToString("") {
            it.joinToString("")
        }
    }

    /**
     * @return string representation of the board of it's player
     */
    fun showStringDetailed(): String {
        val array = Array(size) {
            Array(size) { "-" }
        }
        for (ship in ships) {
            // TODO display dead ships differently
            for (cell in ship.getCells()) {
                array[cell.y][cell.x] = "S"
            }
            for (cell in ship.getMarkedArea()) {
                array[cell.y][cell.x] = "*"
            }
            for (cell in ship.getMarkedCells()) {
                array[cell.y][cell.x] = "X"
            }
        }
        for (miss in misses) {
            array[miss.y][miss.x] = "*"
        }
        return array.joinToString("") {
            it.joinToString("")
        }
    }

    /**
     * @return true if ships are not colliding and false otherwise
     */
    private fun hasNoShipsCollisions(): Boolean {
        for (ship1 in ships) {
            for (ship2 in ships) {
                if (ship1 == ship2) {
                    continue
                }
                if (ship1 conflicts ship2) {
                    return false
                }
            }
        }
        return true
    }

    /**
     * Places ships randomly so they don't collide
     *
     * @return resulting board
     */
    fun locateShipsRandomly(): Board {
        ships = mutableListOf()

        val shipSizes = intArrayOf(4, 3, 3, 2, 2, 2, 1, 1, 1, 1)
        var shipIndex = 0

        val random = Random()
        while (shipIndex < shipSizes.size) {
            val shipSize = shipSizes[shipIndex]
            val ship = if (random.nextBoolean()) {
                placeShipHorizontally(shipSize)
            } else {
                placeShipVertically(shipSize)
            }

            if (hasNoShipsCollisions()) {
                ships += ship
                shipIndex++
            } else {
                ships.removeLast()
                if (random.nextBoolean()) {
                    ships.removeLast()
                    shipIndex--
                }
            }
        }
        return this
    }

    private fun placeShipVertically(shipSize: Int): Ship {
        val x = Random().nextInt(size)
        val y = Random().nextInt(size - shipSize)
        return Ship(x, y, x, y + shipSize - 1)
    }

    /**
     * @param shipSize size of the ship
     * @return random placed ship
     */
    private fun placeShipHorizontally(shipSize: Int): Ship {
        val x = Random().nextInt(size - shipSize)
        val y = Random().nextInt(size)
        return Ship(x, y, x + shipSize - 1, y)
    }

    /**
     * @return true if any ship is alive and false otherwise
     */
    fun isAlive(): Boolean = aliveShips != 0

    /**
     * @return true if all ships are destroyed and false otherwise
     */
    fun isGameLost(): Boolean = aliveShips == 0

    /**
     * @return number of alive ships
     */
    fun getAliveShipsCount(): Int = getAliveShips().size

    /**
     * @return list of all ships
     */
    fun getShips(): List<Ship> = ships

    /**
     * @return list of alive ships
     */
    fun getAliveShips(): List<Ship> = ships.filter { ship -> ship.isAlive() }

    /**
     * @return list of destroyed ships
     */
    fun getDestroyedShips(): List<Ship> =
        ships.filter { ship -> !ship.isAlive() }

    /**
     * @param cell
     */
    fun shot(cell: Cell) {
        var ok = false
        for (ship in ships) {
            if (ship.contains(cell) || ship.containsArea(cell)) {
                ok = true
                if (ship.shot(cell) && !ship.isAlive()) {
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
