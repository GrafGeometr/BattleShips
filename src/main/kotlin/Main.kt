package org.example

import org.example.sockets.User


fun main() {
    /*
    HttpServer.create(InetSocketAddress(8080), 2).run {
    createContext("/") { exchange: HttpExchange ->
    exchange.sendResponseHeaders(200, 0)
    exchange.responseBody.write("Hello, world!".toByteArray())
    exchange.close()
    }
    start()
    }
    */
    User(1).apply {
        startServer(8080)
    }
    /*
    User(2).apply {
    startServer(8081)
    }
    */
}