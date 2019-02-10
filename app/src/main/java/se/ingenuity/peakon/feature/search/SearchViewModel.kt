package se.ingenuity.peakon.feature.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import se.ingenuity.peakon.repository.Repository
import se.ingenuity.peakon.vo.Resource
import se.ingenuity.peakon.vo.Status
import javax.inject.Inject

class SearchViewModel @Inject constructor(repository: Repository) : ViewModel() {
    private val employeeId = MutableLiveData<String>()
    private val employeeResponse = repository.get()

    val employeeDetails = Transformations.map(employeeId) { id ->
        val data = employeeResponse.value?.data
        val employee = data?.data?.first {
            it.id == id
        }
        val account = data?.included?.firstOrNull {
            employee?.relationships?.account?.data?.id == it.id
        }
        employee to account
    }

    val searchEntries: LiveData<Resource<List<SearchEntry>>> =
        Transformations.map(employeeResponse) {
            val data = if (it.status == Status.SUCCESS) {
                val searchEntries = mutableListOf<SearchEntry>()
                it.data!!.data.forEach { employee ->
                    val name = employee.attributes.name
                    val email = it.data.included.firstOrNull { account ->
                        val id = employee.relationships.account.data.id
                        id == account.id
                    }?.attributes?.email ?: ""

                    searchEntries.add(SearchEntry(employee.id, name, email))
                }

                searchEntries
            } else {
                null
            }

            Resource(it.status, data, it.message)
        }

    fun employeeId(id: String) {
        if (id != employeeId.value) {
            employeeId.value = id
        }
    }
}