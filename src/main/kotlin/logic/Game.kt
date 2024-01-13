package org.example.logic

import org.example.sockets.User
import org.example.sockets.getMessage
import org.example.sockets.hasMessage

/**
 * @param player1Board
 * @param player2Board
 * @param currentPlayer
 * @param playerBoards
 */
class Game(
    player1Board: Board = Board(),
    player2Board: Board = Board(),
    private var currentPlayer: Int = 0,
    private var playerBoards: MutableList<Board> = mutableListOf(
        player1Board, player2Board
    ),
) {
    private var run = false

    /**
     * @return current player (0 or 1)
     */
    private fun getCurrentPlayer(): Int = currentPlayer

    /**
     * @return true if game is running and false otherwise
     */
    private fun isGameRunning(): Boolean = run

    /**
     * @param user Int (0 or 1)
     * @return true if user win and false otherwise
     */
    private fun isUserWin(user: Int): Boolean =
        playerBoards[user xor 1].isGameLost()

    /**
     * @param user Int (0 or 1)
     * @return true if user lose and false otherwise
     */
    private fun isUserLose(user: Int): Boolean = playerBoards[user].isGameLost()

    /**
     * @param cell
     * @param player
     */
    // Это значит, что текущий игрок стреляет в ячейку cell, которая, конечно же, попадает по полю противника
    private fun makeShot(cell: Cell, player: Int) {
        if (player != currentPlayer) {
            return
        }
        playerBoards[player xor 1].shot(cell)
        if (isUserLose(player)) {
            run = false
        }
        currentPlayer = currentPlayer xor 1
    }

    /**
     * @param user Int (0 or 1)
     * @param detail Boolean (show unmarked cells in ships)
     * @return string representation of the board
     */
    private fun getBoard(user: Int, detail: Boolean = false): String = when {
        detail -> playerBoards[user].showStringDetailed()
        else -> playerBoards[user].showString()
    }

    private fun locateShipsRandomly() {
        playerBoards[0].locateShipsRandomly()
        playerBoards[1].locateShipsRandomly()
    }

    private fun createUsers(): List<User> {
        val users = listOf(User(0), User(1))
        return users
    }

    private fun connectUsers(users: List<User>) {
        users[0].connectToServer(8080)
        users[1].connectToServer(8081)
    }

    private fun sendBoards(users: List<User>) {
        users[0].sendMessage(
            "BOARDS ${playerBoards[0].showStringDetailed()} ${playerBoards[1].showString()}"
        )
        users[1].sendMessage(
            "BOARDS ${playerBoards[1].showStringDetailed()} ${playerBoards[0].showString()}"
        )
    }

    /**
     * Runs the game
     */
    fun start() {
        run = true

        locateShipsRandomly()

        val users = createUsers()

        connectUsers(users)

        println("Sending boards to players")

        sendBoards(users)

        // TODO: write boards editing

        println("Game started")
        gameLoop(users)
        println("Game over")
    }

    /**
     * @param users
     */
    private fun gameLoop(users: List<User>) {
        while (run) {
            gameLoopIteration(users)
        }
    }

    private fun getMessage(users: List<User>): Pair<String?, Int> = when {
        users[0].hasMessage() -> users[0].getMessage() to 0

        users[1].hasMessage() -> users[1].getMessage() to 1

        else -> null to -1
    }

    /**
     * @param users
     */
    private fun gameLoopIteration(users: List<User>) {
        val (message, user) = getMessage(users)
        message?.let {
            println(message)
        }
        if (message.isNullOrEmpty() || user == -1) {
            return
        }
        val words = message.split("$")
        if (getCurrentPlayer() != user || words.size != 3) {
            users[user].sendMessage("INCORRECT")
            return
        }
        if (words[0] == "SHOT") {
            val x = words[1].toInt()
            val y = words[2].toInt()
            makeShot(Cell(x, y), user)
            sendBoards(users)
        } else {
            users[user].sendMessage("INCORRECT")
            return
        }

        if (!isGameRunning()) {
            users[0].sendMessage(if (isUserWin(0)) "WIN" else "LOSE")
            users[1].sendMessage(if (isUserWin(1)) "WIN" else "LOSE")
            run = false
        } else {
            users[0].sendMessage("RUN")
            users[1].sendMessage("RUN")
        }
    }
}
