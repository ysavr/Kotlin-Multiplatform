package id.kotlin.kotlinmultiplatformexample.repository

import id.kotlin.kotlinmultiplatformexample.data.Result
import id.kotlin.kotlinmultiplatformexample.data.model.UserModel
import id.kotlin.kotlinmultiplatformexample.datasource.LoginDataSource

class LoginRepository(private val dataSource: LoginDataSource) {

    @ExperimentalStdlibApi
    fun login(username: String, password: String): Result<UserModel> {
        return dataSource.login(username, password)
    }
}