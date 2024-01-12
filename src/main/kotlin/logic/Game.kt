package org.example.logic

class Game(
    player1Board: Board = Board(),
    player2Board: Board = Board(),
    private var currentPlayer: Int = 0,
    private var playerBoards: MutableList<Board> = mutableListOf(
        player1Board, player2Board
    ),
) {
    private var run = false

    fun getCurrentPlayer(): Int = currentPlayer

    fun isGameRunning(): Boolean = run

    fun isUserWin(user: Int): Boolean = playerBoards[user xor 1].gameIsLost()

    fun isUserLose(user: Int): Boolean = playerBoards[user].gameIsLost()

    // Это значит, что текущий игрок стреляет в ячейку cell, которая, конечно же, попадает по полю противника
    fun makeShot(cell: Cell, player: Int) {
        if (player != currentPlayer) return
        playerBoards[player xor 1].shot(cell)
        if (isUserLose(player)) run = false
        currentPlayer = currentPlayer xor 1
    }

    fun getBoard(user: Int, detail: Boolean = false): String = when {
        detail -> playerBoards[user].showStringDetailed()
        else -> playerBoards[user].showString()
    }

    fun startGame(): Boolean = run
}