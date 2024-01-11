package org.example.sockets

import java.net.ServerSocket
import java.util.*


class User(private val id: Int) {

    fun startServer(port: Int = 8080) {
        ServerSocket(port).run(socketLogic())
    }

    fun socketLogic(): (ServerSocket) -> Unit {
        return { it ->
            println("Server started on port ${it.localPort}")
            val client = it.accept()
            println("Client connected : ${client.inetAddress.hostAddress}")
            val scanner = Scanner(client.getInputStream())
            while (scanner.hasNextLine()) {
                val line = scanner.nextLine()
                println(id)
                println(line) // run some logic on user with id=id
            }
            client.close()
        }
    }
}