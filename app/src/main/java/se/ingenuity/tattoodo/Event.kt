package se.ingenuity.tattoodo

class Event<out T>(private val content: T) {

    var handled = false
        private set

    fun getContentIfUnhandled(): T? {
        return if (handled) {
            null
        } else {
            handled = true
            content
        }
    }
}