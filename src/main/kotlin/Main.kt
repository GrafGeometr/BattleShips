package org.example

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.example.sockets.User
import org.example.sockets.getMessage
import org.example.sockets.hasMessage

fun messageLoop() {
    val user1 = User(1)
    val user2 = User(2)

    runBlocking {
        launch { user1.startListening(8080) }.join()
        launch { user2.startListening(8081) }.join()
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
        launch { user.startListening(8080) }.join()
    }

    println("Event loop started")
    while (true) {
        if (user.hasMessage()) {
            val message = user.getMessage()
            if (message != null) user.sendMessage("We got your message: $message")
        }
    }
}

fun main() {
    onePersonTestLoop()
}
