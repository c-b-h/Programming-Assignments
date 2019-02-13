package se.ingenuity.tattoodo.model

data class Post(
    val id: Long,
    val image: Image,
    val latest_comment: LatestComment?,
    val uploader: Uploader
)

data class Image(val url: String?)

data class LatestComment(val content: String, val user: User)
data class Uploader(val image_url: String?, val name: String = "")
data class User(val name: String)