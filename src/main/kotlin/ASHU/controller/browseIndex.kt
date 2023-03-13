package ASHU.controller

import ASHU.dataAccess.RedisConnector
import ASHU.models.PostSearched
import ASHU.models.ResultPost
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

suspend fun rawSearch(query : String) : List<String>{

    val words : List<String> = processWordList(query.split(" "))
    var resultList : Set<String> = setOf()

    GlobalScope.launch {
        words.forEach {

            val wordSet : List<String> = RedisConnector.getInstance().smembers(it)
            resultList = resultList.plus(wordSet)

        }
    }.join()

    return resultList.toList()
}

suspend fun sortResult(query: String, toBeSorted : List<PostSearched>) : List<ResultPost>{

    var queue : PriorityQueue<ResultPost> = PriorityQueue(Collections.reverseOrder())

    toBeSorted.forEach {
        queue.add(getPosition(query, it))
    }

    return queue.toList()

}

suspend fun getPosition(query: String, post: PostSearched): ResultPost {

    val postWords : List<String> = processWordList(post.toString().split(" "))
    val queryWords : List<String> = processWordList(query.split(" "))
    var TFIDF = 0.0

    queryWords.forEach {

        val TF = getTF(it, postWords)
        val IDF = getIDF(it)
        TFIDF += TF * IDF

    }

    val rate = post.rating * 0.3
    val view = post.views * 0.7

    val pos = TFIDF * rate * view

    return ResultPost(post.id, pos)
}


fun getTF(word : String, completeText : List<String>) : Double {

    val frequency = Collections.frequency(completeText, word)

    return frequency.toDouble() / completeText.size

}

suspend fun getIDF(word : String) : Double{

    var IDF = 0.0

    GlobalScope.launch {

        val nDocuments : Double = RedisConnector.getInstance().get(RedisConnector.DocCountKey).toString().toDouble()
        val nDocumentWithWord : Long = RedisConnector.getInstance().scard(word)

        IDF = nDocumentWithWord / nDocuments

    }.join()

    return IDF
}