package ASHU

import ASHU.consumer.runConsumer
import ASHU.plugins.configureRouting
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*

fun main(){
    runConsumer()
    configureServer()
}

fun configureServer() {
    embeddedServer(Netty, port = 8085, host = "0.0.0.0", module = Application::module)
            .start(wait = true)
}

fun Application.module() {
    install(ContentNegotiation) {
        json()
    }
    configureRouting()
}
