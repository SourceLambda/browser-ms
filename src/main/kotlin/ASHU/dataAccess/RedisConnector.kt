package ASHU.dataAccess

import io.github.crackthecodeabhi.kreds.connection.Endpoint
import io.github.crackthecodeabhi.kreds.connection.KredsClient
import io.github.crackthecodeabhi.kreds.connection.newClient


//object RedisConnector {
//    private val client : KredsClient = newClient(
//        Endpoint("redis-17116.c241.us-east-1-4.ec2.cloud.redislabs.com",
//        17116)
//    )
//
//    const val DocCountKey = "QG29auKuotaXFB4OFhLekAuAKTVl70dDBGEH8IJQ4jhD4Skoaq1UJsVzpDoBFMtccR7PFsOd1Lkq16E9GINc00AyHReaYaKd5Vf"
//
//
//
//    suspend fun getInstance() : KredsClient {
//
//        GlobalScope.launch {
//            client.auth("haGiCqFVxLoxJfCC94dRkZdgzTVQwxlY")
//        }.join()
//
//        return client;
//    }
//
//}

object RedisConnector {
    private val host = System.getenv("REDIS_BROWSER_HOST")!!
    private val port = System.getenv("REDIS_BROWSER_PORT")!!
//    private val host = "127.0.0.1"
//    private val port = "6379"

    private val client : KredsClient = newClient(Endpoint(
        host, //172.21.0.3
        Integer.parseInt(port)))

    const val DocCountKey = "QG29auKuotaXFB4OFhLekAuAKTVl70dDBGEH8IJQ4jhD4Skoaq1UJsVzpDoBFMtccR7PFsOd1Lkq16E9GINc00AyHReaYaKd5Vf"



    suspend fun getInstance() : KredsClient {
        return client;
    }

}