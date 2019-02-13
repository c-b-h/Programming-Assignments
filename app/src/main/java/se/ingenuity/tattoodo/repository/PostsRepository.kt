package se.ingenuity.tattoodo.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import se.ingenuity.tattoodo.api.PostsApi
import se.ingenuity.tattoodo.model.PostDetail
import se.ingenuity.tattoodo.model.Posts
import se.ingenuity.tattoodo.vo.Resource
import javax.inject.Inject

class PostsRepository @Inject constructor(private val postsApi: PostsApi) {
    fun get(): LiveData<Resource<Posts>> {
        val posts = MutableLiveData<Resource<Posts>>()

        posts.value = Resource.loading(null)
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = postsApi.getAsync().await()

                withContext(Dispatchers.Main) {
                    posts.value = Resource.success(response)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    posts.value = Resource.error("Unable to get posts due to ${e.message}", null)
                }
            }
        }

        return posts
    }

    fun getDetail(postId: Long): LiveData<Resource<PostDetail>> {
        val post = MutableLiveData<Resource<PostDetail>>()

        post.value = Resource.loading(null)
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = postsApi.getDetailsAsync(postId).await()

                withContext(Dispatchers.Main) {
                    post.value = Resource.success(response)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    post.value = Resource.error("Unable to get posts due to ${e.message}", null)
                }
            }
        }

        return post
    }
}