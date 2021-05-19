package id.kotlin.kotlinmultiplatformexample.android.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.kotlin.kotlinmultiplatformexample.datasource.LoginDataSource
import id.kotlin.kotlinmultiplatformexample.repository.LoginRepository
import id.kotlin.kotlinmultiplatformexample.data.Result
import id.kotlin.kotlinmultiplatformexample.data.model.UserModel

class MainViewModel : ViewModel() {

    companion object {
        private val dataSource: LoginDataSource = LoginDataSource()
        val repository: LoginRepository = LoginRepository(dataSource)
    }

    private val _loginResult = MutableLiveData<Result<UserModel>>()
    val loginResult: LiveData<Result<UserModel>> get() = _loginResult

    @ExperimentalStdlibApi
    fun login(username: String, password: String) {
        val result = repository.login(username, password)
        if (result is Result.Success) {
            _loginResult.value = result
        } else if (result is Result.Error) {
            _loginResult.value = result
        }
    }

}