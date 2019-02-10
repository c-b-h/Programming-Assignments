package se.ingenuity.peakon.model

data class Account(
    val id: String,
    val attributes: Attributes
) {
    data class Attributes(val email: String?)
}