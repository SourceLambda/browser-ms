package ASHU.plugins

import ASHU.controller.indexPost
import ASHU.controller.rawSearch
import ASHU.controller.sortResult
import ASHU.models.Post
import ASHU.models.PostSearched
import ASHU.wordProcessing.StopWords
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText(StopWords.stopWordsMap.size.toString())
        }
        post("/browser/index") {
            val post : Post = call.receive<Post>()
            indexPost(post)
            call.response.status(HttpStatusCode.OK)
            call.respondText("Indexed Correctly")
        }
        get("/browser/search"){
            if(call.parameters["q"] == null) {
                call.response.status(HttpStatusCode.BadRequest)
                call.respondText("There is no text to search")
            }

            val query : String = call.parameters["q"].toString()
            val response = rawSearch(query)

            call.response.status(HttpStatusCode.OK)
            call.respond(response)

        }
        post("/browser/sort"){
            if(call.parameters["q"] == null) {
                call.response.status(HttpStatusCode.BadRequest)
                call.respondText("There is no text to search")
            }
            val posts : List<PostSearched> =  call.receive()
            val query : String = call.parameters["q"].toString()
            val resultList = sortResult(query, posts)
            call.response.status(HttpStatusCode.OK)
            call.respond(resultList)
        }
    }
}
