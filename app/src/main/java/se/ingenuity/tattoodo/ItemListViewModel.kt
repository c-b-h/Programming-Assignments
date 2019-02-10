package se.ingenuity.tattoodo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ItemListViewModel : ViewModel() {
    private val _navigateToDetails = MutableLiveData<Event<String>>()

    val navigateToDetails: LiveData<Event<String>>
        get() = _navigateToDetails


    fun userClicksOnButton(itemId: String) {
        _navigateToDetails.value = Event(itemId)
    }
}