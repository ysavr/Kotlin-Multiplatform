package id.kotlin.kotlinmultiplatformexample.android.rocketLaunch

import androidx.lifecycle.*
import id.kotlin.kotlinmultiplatformexample.android.utils.Resource
import id.kotlin.kotlinmultiplatformexample.data.Result
import id.kotlin.kotlinmultiplatformexample.network.SpaceXSDK
import id.kotlin.kotlinmultiplatformexample.data.remote.RocketLaunch
import id.kotlin.kotlinmultiplatformexample.db.DatabaseDriverFactory
import id.kotlin.kotlinmultiplatformexample.repository.RocketRepository
import kotlinx.coroutines.launch

class RocketViewModel: ViewModel() {

    private val repository: RocketRepository = RocketRepository()

    private val _rockets = MutableLiveData<Resource<List<RocketLaunch>>>()
    val rockets: LiveData<Resource<List<RocketLaunch>>> get() = _rockets

    private lateinit var sdk: SpaceXSDK

    fun getRockets(database: DatabaseDriverFactory) {
        _rockets.value = Resource.loading()
        viewModelScope.launch {
            try {
                val result = repository.getLaunches(database)
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

    fun getRocketFromSDK(database: DatabaseDriverFactory) {
        sdk = SpaceXSDK(database)
        _rockets.value = Resource.loading()
        viewModelScope.launch {
            try {
                val result = sdk.getLaunches()
                _rockets.value = Resource.success(result)
            } catch (e: Throwable) {
                _rockets.value = Resource.error(e)
                e.printStackTrace()
            }
        }
    }
}