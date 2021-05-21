package id.kotlin.kotlinmultiplatformexample.datasource

import id.kotlin.kotlinmultiplatformexample.data.Result
import id.kotlin.kotlinmultiplatformexample.data.network.SpaceXApi
import id.kotlin.kotlinmultiplatformexample.data.remote.RocketLaunch

class RocketDataSource {

    private val api = SpaceXApi()

    suspend fun rocket(): Result<List<RocketLaunch>> {
        return try {
            val request = api.getAllLaunches()
            Result.Success(request)
        } catch (e: Throwable) {
            Result.Error(RuntimeException("Error Getting Data", e))
        }
    }
}