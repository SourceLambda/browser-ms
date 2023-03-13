package ASHU.models

import kotlinx.serialization.Serializable

@Serializable
data class PostSearched(val id : String,
                        val rating : Double,
                        val views : Int,
                        val title : String,
                        val desc : String,
                        val category : String) {
    override fun toString(): String {
        return "$title $desc $category"
    }
}
