package org.example.sockets

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.ServerSocket
import java.util.*


class User(private val id: Int) {
    suspend fun listenLoop(port: Int) = withContext(Dispatchers.IO) {
        val server = ServerSocket(port)
        println("Server started on port ${server.localPort}")
        val client = server.accept()
        println(
            "Client connected to server on port ${server.localPort}: ${
                client.inetAddress.hostAddress
            }"
        )
        val scanner = Scanner(client.getInputStream())
        while (scanner.hasNextLine()) {
            val line = scanner.nextLine()
            println("We got a message from user $id: $line")
            client.getOutputStream().write("Hello, $line".toByteArray())
            client.getOutputStream().flush()
        }
        client.close()
    }
}


