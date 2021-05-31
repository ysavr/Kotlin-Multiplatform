package id.kotlin.kotlinmultiplatformexample.datasource

import id.kotlin.kotlinmultiplatformexample.data.Result
import id.kotlin.kotlinmultiplatformexample.data.model.UserModel
import kotlin.random.Random

class LoginDataSource {

    @ExperimentalStdlibApi
    fun login(username: String, password: String): Result<UserModel> {
        return try {
            when {
                password.isEmpty() and username.isEmpty() -> {
                    Result.Error(RuntimeException("Field must not empty "))
                }
                password.lowercase() == username.lowercase() -> {
                    val id = Random.nextInt(0, 2000)
                    val user = UserModel(id.toString(), username)
                    Result.Success(user)
                }
                else -> Result.Error(RuntimeException("Password not matches with username"))
            }
        } catch (e: Throwable) {
            Result.Error(RuntimeException("Error login", e))
        }
    }

}