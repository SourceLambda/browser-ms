package ASHU.controller

import ASHU.dataAccess.RedisConnector
import ASHU.models.Post
import ASHU.wordProcessing.StopWords
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.system.measureNanoTime

suspend fun indexPost(post : Post) {

    registerDocumentCount()
    createIndexPerWord(post)

}

suspend private fun registerDocumentCount(){
    GlobalScope.launch {
        RedisConnector.getInstance().incr(RedisConnector.DocCountKey)

    }.join()
}

//suspend private fun createIndexPerWord(post : Post){
//
//    GlobalScope.launch {
//
//        val words : List<String> = processWordList(post.toString().split(" "))
//
//        words.forEach {
//
//            if(StopWords.isValidWord(it)){
//                RedisConnector.getInstance().sadd(it, post.id)
//            }
//        }
//
//    }.join()
//
//}

suspend private fun createIndexPerWord(post : Post){

    GlobalScope.launch {

        var words : List<String> = post.toString().split(" ")
        val nanoProcess = measureNanoTime {
            words = processWordList(words)
        }

        println("----------------------------- nano process is $nanoProcess")

        val nanoSave = measureNanoTime {
            words.forEach {

                if(StopWords.isValidWord(it)){
                    RedisConnector.getInstance().sadd(it, post.id)
                }
            }
        }

        println("----------------------------- nano save is $nanoSave")

    }.join()

}