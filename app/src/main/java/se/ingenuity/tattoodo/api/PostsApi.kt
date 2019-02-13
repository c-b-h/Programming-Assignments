package se.ingenuity.tattoodo.api

import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import se.ingenuity.tattoodo.model.PostDetail
import se.ingenuity.tattoodo.model.Posts

interface PostsApi {
    @GET("/api/v2/search/posts")
    fun getAsync(): Deferred<Posts>

    @GET("/api/v2/posts/{id}")
    fun getDetailsAsync(@Path("id") id: Long): Deferred<PostDetail>
}