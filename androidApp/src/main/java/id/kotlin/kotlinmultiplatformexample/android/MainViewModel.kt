package id.kotlin.kotlinmultiplatformexample.android

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.kotlin.kotlinmultiplatformexample.data.LoginDataSource
import id.kotlin.kotlinmultiplatformexample.data.LoginRepository
import id.kotlin.kotlinmultiplatformexample.data.Result

class MainViewModel : ViewModel() {

    companion object {
        private val dataSource: LoginDataSource = LoginDataSource()
        val repository: LoginRepository = LoginRepository(dataSource)
    }

    private val _loginResult = MutableLiveData<String>()
    val loginResult: LiveData<String> get() = _loginResult

    fun login(username: String, password: String) {
        val result = repository.login(username, password)
        if (result is Result.Success) {
            _loginResult.value = "Hello ${result.data.name}"
        } else if (result is Result.Error) {
            _loginResult.value = result.exception.message
        }
    }

}