package ASHU.wordProcessing

import java.io.File
import kotlin.collections.*

object StopWords {

    var stopWordsMap : HashSet<String> = HashSet()

    init {
        File("src/main/resources/stopWords.txt").forEachLine { stopWordsMap.add(it) }
    }

    fun isValidWord(word : String) : Boolean {

        return !stopWordsMap.contains(word)

    }


}