package ASHU.controller

fun processWordList(words: List<String>) : List<String>{

    val newWords : MutableList<String> = ArrayList()

    words.forEach {

        val word = removePoints(it.trim().lowercase())
        newWords.add(word)

    }


    return newWords

}

fun removePoints(word: String) : String{

    return word.replace(",", "").replace("á", "a")
        .replace("é", "e").replace("í", "i")
        .replace("ó", "o").replace("ú", "u")
        .replace(".", "").replace("?", " ")
        .replace("¿", "")

}