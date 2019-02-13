package se.ingenuity.tattoodo.feature.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import se.ingenuity.tattoodo.repository.PostsRepository
import javax.inject.Inject

class DetailViewModel @Inject constructor(postsRepository: PostsRepository) : ViewModel() {
    private val postId = MutableLiveData<Long>()
    val detail = Transformations.switchMap(postId) {
        postsRepository.getDetail(it)
    }

    fun init(id: Long) {
        if (id != postId.value) {
            postId.value = id
        }
    }
}