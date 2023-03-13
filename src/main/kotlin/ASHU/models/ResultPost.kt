package ASHU.models

import kotlinx.serialization.Serializable

@Serializable
data class ResultPost(val id : String,
                      var position : Double) : Comparable<ResultPost> {
    override fun compareTo(other: ResultPost): Int {
        return this.position.compareTo(other.position)
    }
}
