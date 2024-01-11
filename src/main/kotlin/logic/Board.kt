package org.example.logic

import java.util.*


class Board(
    private var ships: List<Ship> = emptyList(),
    private var misses: List<Cell> = emptyList(),
) {
    private val size = 10

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
        val array = Array(size) {
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
        val array = Array(size) {
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
        val sizes = intArrayOf(4, 3, 3, 2, 2, 2, 1, 1, 1, 1)
        var idx = 0
        while (true) {
            val shipSize = sizes[idx]
            ships += when {
                Random().nextBoolean() -> {
                    val x = Random().nextInt(size)
                    val y = Random().nextInt(size - shipSize)
                    Ship(x, y, x, y + shipSize - 1)
                }

                else -> {
                    val x = Random().nextInt(size - shipSize)
                    val y = Random().nextInt(size)
                    Ship(x, y, x + shipSize - 1, y)
                }
            }
            if (checkNoShipsCollisions()) {
                idx++
                if (idx == sizes.size) break
            } else {
                // ships has at least 2 elements
                ships.run {
                    removeLast()
                    if (Random().nextBoolean()) removeLast().also { idx-- }
                }
            }
        }
    }
}