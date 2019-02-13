package se.ingenuity.tattoodo.feature.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NavigationViewModel : ViewModel() {
    private val navigateToDetails = MutableLiveData<Message<Pair<Long, Array<String>>>>()

    fun userClicksOnPost(postId: Long, transitionTags: Array<String>) {
        navigateToDetails.value = Message(postId to transitionTags)
    }

    fun getNavigateToDetails(): LiveData<Message<Pair<Long, Array<String>>>> = navigateToDetails
}