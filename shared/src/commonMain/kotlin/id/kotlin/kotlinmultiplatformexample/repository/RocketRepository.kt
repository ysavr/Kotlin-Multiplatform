package id.kotlin.kotlinmultiplatformexample.repository

import id.kotlin.kotlinmultiplatformexample.data.Result
import id.kotlin.kotlinmultiplatformexample.data.remote.RocketLaunch
import id.kotlin.kotlinmultiplatformexample.datasource.RocketDataSource

class RocketRepository {
    private val rocketDataSource: RocketDataSource = RocketDataSource()

    suspend fun getLaunches(): Result<List<RocketLaunch>> {
        return rocketDataSource.rocket()
    }
}