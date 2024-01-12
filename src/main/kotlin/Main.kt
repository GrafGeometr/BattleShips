package org.example

import org.example.logic.Board
import org.example.logic.Cell
import org.example.logic.Game
import org.example.sockets.User
import org.example.sockets.getMessage
import org.example.sockets.hasMessage

import java.util.*

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun messageLoop() {
    val user1 = User(1)
    val user2 = User(2)

    runBlocking {
        launch { user1.connectToServer(8080) }.join()
        launch { user2.connectToServer(8081) }.join()
    }

    println("Event loop started")
    while (true) {
        if (user1.hasMessage()) {
            val message = user1.getMessage()
            message?.let { user2.sendMessage(it) }
        }
        if (user2.hasMessage()) {
            val message = user2.getMessage()
            message?.let { user1.sendMessage(it) }
        }
    }
}

fun onePersonTestLoop() {
    val user = User(1)

    runBlocking {
        launch { user.connectToServer(8080) }.join()
    }

    println("Event loop started")
    while (true) {
        if (user.hasMessage()) {
            val message = user.getMessage()
            message?.let {
                user.sendMessage("We got your message: $message")
            }
        }
    }
}

fun gameLoop() {
    val game = Game(
        Board().locateShipsRandomly(),
        Board().locateShipsRandomly(),
        Random().nextInt(0, 1),
    )

    val users = listOf(User(0), User(1))

    users[0].connectToServer(8080)
    users[1].connectToServer(8081)

    println("Sending boards to players")

    users[0].sendMessage(game.getBoard(users[0].id, true))
    users[1].sendMessage(game.getBoard(users[1].id, true))

    users[0].sendMessage(game.getBoard(1, false))
    users[1].sendMessage(game.getBoard(0, false))

    // TODO: write boards editing

    println("Game started")
    while (true) {
        var message: String? = null
        var user: Int = -1
        if (users[0].hasMessage()) {
            message = users[0].getMessage()
            user = 0
        } else if (users[1].hasMessage()) {
            message = users[1].getMessage()
            user = 1
        }
        if (message.isNullOrEmpty() || user == -1) {
            continue
        }
        val words = message.split(" ")
        if (game.getCurrentPlayer() != user || words.size != 3) {
            users[user].sendMessage("INCORRECT")
            continue
        }
        if (words[0] == "SHOT") {
            val x = words[1].toInt()
            val y = words[2].toInt()
            game.makeShot(Cell(x, y), user)
            users[0].sendMessage(game.getBoard(0, true))
            users[1].sendMessage(game.getBoard(1, true))

            users[0].sendMessage(game.getBoard(1, false))
            users[1].sendMessage(game.getBoard(0, false))
        } else {
            users[user].sendMessage("INCORRECT")
            continue
        }

        if (!game.isGameRunning()) {
            users[0].sendMessage(if (game.isUserWin(0)) "WIN" else "LOSE")
            users[1].sendMessage(if (game.isUserWin(1)) "WIN" else "LOSE")
            break
        } else {
            users[0].sendMessage("RUN")
            users[1].sendMessage("RUN")
        }
    }
    println("Game over")
}

fun main() {
    onePersonTestLoop()
}
