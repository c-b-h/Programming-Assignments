package se.ingenuity.tattoodo.feature.list

import androidx.lifecycle.ViewModel
import se.ingenuity.tattoodo.repository.PostsRepository
import javax.inject.Inject

class ListViewModel @Inject constructor(postsRepository: PostsRepository) : ViewModel() {
    val posts = postsRepository.get()
}