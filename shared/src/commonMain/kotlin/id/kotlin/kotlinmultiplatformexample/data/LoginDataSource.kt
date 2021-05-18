package id.kotlin.kotlinmultiplatformexample.data

import id.kotlin.kotlinmultiplatformexample.data.model.UserModel
import kotlin.random.Random

class LoginDataSource {

    fun login(username: String, password: String): Result<UserModel> {
        return try {
            if (password.lowercase() == username.lowercase()) {
                val id = Random.nextInt(0, 2000)
                val user = UserModel(id.toString(), username)
                Result.Success(user)
            } else Result.Error(RuntimeException("Password not matches with username"))
        } catch (e: Throwable) {
            Result.Error(RuntimeException("Error login", e))
        }
    }

}