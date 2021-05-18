package id.kotlin.kotlinmultiplatformexample.data

import id.kotlin.kotlinmultiplatformexample.data.model.UserModel

class LoginRepository(private val dataSource: LoginDataSource) {

    fun login(username: String, password: String): Result<UserModel> {
        return dataSource.login(username, password)
    }
}