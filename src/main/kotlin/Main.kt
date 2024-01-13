package org.example

import org.example.logic.Game

fun main() {
    Game(currentPlayer = 0).run {
        start()
    }
}
