package se.ingenuity.tattoodo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ItemListViewModel : ViewModel() {
    private val navigateToDetails = MutableLiveData<Event<String>>()

    fun userClicksOnButton(itemId: String) {
        navigateToDetails.value = Event(itemId)
    }

    fun getNavigateToDetails(): LiveData<Event<String>> = navigateToDetails
}