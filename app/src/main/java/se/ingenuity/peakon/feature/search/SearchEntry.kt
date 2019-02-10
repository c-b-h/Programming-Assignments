package se.ingenuity.peakon.feature.search

data class SearchEntry(val id: String, val name: CharSequence, val email: CharSequence) {
    override fun toString(): String {
        return name.toString()
    }
}