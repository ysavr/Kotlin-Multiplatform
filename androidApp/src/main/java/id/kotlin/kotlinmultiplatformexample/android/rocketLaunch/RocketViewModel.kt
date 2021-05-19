package id.kotlin.kotlinmultiplatformexample.android.rocketLaunch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.kotlin.kotlinmultiplatformexample.android.utils.Resource
import id.kotlin.kotlinmultiplatformexample.data.Result
import id.kotlin.kotlinmultiplatformexample.data.remote.RocketLaunch
import id.kotlin.kotlinmultiplatformexample.repository.RocketRepository
import kotlinx.coroutines.launch

class RocketViewModel: ViewModel() {

    private val repository: RocketRepository = RocketRepository()

    private val _rockets = MutableLiveData<Resource<List<RocketLaunch>>>()
    val rockets: LiveData<Resource<List<RocketLaunch>>> get() = _rockets

    fun getRockets() {
        _rockets.value = Resource.loading()
        viewModelScope.launch {
            try {
                val result = repository.getLaunches()
                if (result is Result.Success) {
                    _rockets.value = Resource.success(result.data)
                } else if (result is Result.Error) {
                    _rockets.value = Resource.error(result.exception)
                }
            } catch (e: Throwable) {
                _rockets.value = Resource.error(e)
                e.printStackTrace()
            }
        }
    }
}