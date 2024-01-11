package org.example

import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpServer
import org.example.sockets.User
import java.net.InetSocketAddress


fun main() {
    HttpServer.create(InetSocketAddress(8080), 2).run {
        createContext("/") { exchange: HttpExchange ->
            exchange.sendResponseHeaders(200, 0)
            exchange.responseBody.write("Hello, world!".toByteArray())
            exchange.close()
        }
        start()
    }
    User(1).startServer(8080)
    User(2).startServer(5555)
}