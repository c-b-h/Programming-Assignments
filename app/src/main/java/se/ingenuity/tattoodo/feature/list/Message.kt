package se.ingenuity.tattoodo.feature.list

class Message<out T>(private val data: T) {

    var handled = false
        private set

    fun getContentIfUnhandled(): T? {
        return if (handled) {
            null
        } else {
            handled = true
            data
        }
    }
}