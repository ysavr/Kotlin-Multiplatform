package id.kotlin.kotlinmultiplatformexample.repository

import id.kotlin.kotlinmultiplatformexample.data.Result
import id.kotlin.kotlinmultiplatformexample.data.remote.RocketLaunch
import id.kotlin.kotlinmultiplatformexample.datasource.RocketDataSource
import id.kotlin.kotlinmultiplatformexample.db.DatabaseDriverFactory

class RocketRepository {
    private val rocketDataSource: RocketDataSource = RocketDataSource()

    suspend fun getLaunches(database: DatabaseDriverFactory): Result<List<RocketLaunch>> {
        return rocketDataSource.rocket(database)
    }
}