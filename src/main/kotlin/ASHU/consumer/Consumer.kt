package ASHU.consumer

import ASHU.controller.indexPost
import ASHU.models.Post
import com.fasterxml.jackson.databind.ObjectMapper
import com.rabbitmq.client.CancelCallback
import com.rabbitmq.client.ConnectionFactory
import com.rabbitmq.client.DeliverCallback
import com.rabbitmq.client.Delivery
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.nio.charset.StandardCharsets

fun runConsumer(){
    val factory = ConnectionFactory()
//    val host = "172.17.0.3"
//    val queueName = "indexer"

    val host = System.getenv("RABBITMQ_HOST")!!
    val queueName = System.getenv("RABBITMQ_QUEUE_NAME")!!

    factory.host = host
    val connection = factory.newConnection()
    val channel = connection.createChannel()

    channel.queueDeclare(queueName, false, false, false, null)

    println("[MQ] the browser is already listening for indexing packages")

    channel.basicConsume(queueName, true, deliverCallback, cancelCallback)


}


val deliverCallback = DeliverCallback { consumerTag: String?, delivery: Delivery ->
    val message = String(delivery.body, StandardCharsets.UTF_8)
    val body = ObjectMapper().readValue(message, Post::class.java)

    GlobalScope.launch {
        indexPost(body)
    }

    println("[$consumerTag] Received message: '${body}'")
}

val cancelCallback = CancelCallback { consumerTag: String? ->
    println("[$consumerTag] was canceled")
}