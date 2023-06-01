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
    var host = "amqps://jkbhmtrf:jIE25F2lHM28d0R12uCRyBVJsd_lCTPF@gull.rmq.cloudamqp.com/jkbhmtrf"
    var queueName = "indexer"
    var port = 5672
    var user = "jkbhmtrf"
    var pass = "jIE25F2lHM28d0R12uCRyBVJsd_lCTPF"

    if(System.getenv("RABBITMQ_HOST") != null) host = System.getenv("RABBITMQ_HOST")
    if(System.getenv("RABBITMQ_PORT") != null) port = Integer.parseInt(System.getenv("RABBITMQ_PORT"))
    if(System.getenv("RABBITMQ_USER") != null) user = System.getenv("RABBITMQ_USER")
    if(System.getenv("RABBITMQ_PASS") != null) pass = System.getenv("RABBITMQ_PASS")
    if(System.getenv("RABBITMQ_QUEUE_NAME") != null) queueName = System.getenv("RABBITMQ_QUEUE_NAME")

    factory.setUri(host)
    val connection = factory.newConnection()
    val channel = connection.createChannel()

    channel.queueDeclare(queueName, false, false, false, null)

    println("[MQ] the browser is already listening for indexing packages")

    channel.basicConsume(queueName, false, deliverCallback, cancelCallback)


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