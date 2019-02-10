package se.ingenuity.peakon.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import se.ingenuity.peakon.api.EmployeesApi
import se.ingenuity.peakon.model.EmployeeResponse
import se.ingenuity.peakon.vo.Resource
import javax.inject.Inject

class Repository @Inject constructor(private val employeesApi: EmployeesApi) {
    fun get(): LiveData<Resource<EmployeeResponse>> {
        val posts = MutableLiveData<Resource<EmployeeResponse>>()

        posts.value = Resource.loading(null)
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = employeesApi.getAsync().await()

                withContext(Dispatchers.Main) {
                    posts.value = Resource.success(response)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    posts.value = Resource.error("Unable to get employee response due to ${e.message}", null)
                }
            }
        }

        return posts
    }
}