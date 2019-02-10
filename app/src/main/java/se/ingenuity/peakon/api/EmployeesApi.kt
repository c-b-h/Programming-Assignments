package se.ingenuity.peakon.api

import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import se.ingenuity.peakon.model.EmployeeResponse

interface EmployeesApi {
    @GET("employees.json")
    fun getAsync(): Deferred<EmployeeResponse>
}