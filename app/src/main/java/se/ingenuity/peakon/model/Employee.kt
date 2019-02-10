package se.ingenuity.peakon.model

import com.squareup.moshi.Json

data class Employee(
    val id: String,
    val attributes: Attributes,
    val relationships: Relationships
) {
    data class Attributes(
        val name: String,
        @Json(name = "Age") val birth: String?
    )
}

data class Relationships(val account: Account) {
    data class Account(val data: Data) {
        data class Data(val id: String)
    }
}