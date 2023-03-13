package ASHU.models

import kotlinx.serialization.Serializable

@Serializable
data class Post(val id : String,
                val title : String,
                val desc : String,
                val category : String){
    override fun toString(): String {
        return "$title $desc $category"
    }
}
