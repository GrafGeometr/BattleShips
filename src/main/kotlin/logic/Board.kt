package org.example.logic

class Board(
    private var ships: List<Ship> = emptyList(),
    var misses: List<Cell> = emptyList(),
) {
    private val size = 10

    constructor(field: Array<Array<Boolean>>) : this() {

        (4 downTo 1).forEach { shipSize ->
            // horizontal ship
            for (y in 0..<size) for (x in 0..<size - shipSize)
                if ((0..<shipSize).all { field[x + it][y] }) {
                    ships += Ship(x, y, x + shipSize - 1, y)
                }
            // vertical ship
            for (x in 0..<size) for (y in 0..<size - shipSize)
                if ((0..<shipSize).all { field[x][y + it] }) {
                    ships += Ship(x, y, x, y + shipSize - 1)
                }
        }
    }
}