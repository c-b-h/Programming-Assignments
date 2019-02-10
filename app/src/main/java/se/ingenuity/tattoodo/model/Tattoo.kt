package se.ingenuity.tattoodo.model

data class Tattoo(val id: Long,
                  val image: Image)

data class Image(val url: String)