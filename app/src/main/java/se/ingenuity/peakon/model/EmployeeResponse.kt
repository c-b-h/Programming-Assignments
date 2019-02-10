package se.ingenuity.peakon.model

data class EmployeeResponse(
    val data: List<Employee>,
    val included: List<Account>
)