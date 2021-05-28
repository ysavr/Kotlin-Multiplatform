package id.kotlin.kotlinmultiplatformexample.db

import id.kotlin.kotlinmultiplatformexample.data.remote.Links
import id.kotlin.kotlinmultiplatformexample.data.remote.Rocket
import id.kotlin.kotlinmultiplatformexample.data.remote.RocketLaunch

class Database(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = AppDatabase(databaseDriverFactory.createDriver())
    private val query = database.appDatabaseQueries

    internal fun clearDatabase() {
        query.transaction {
            query.removeAllLaunches()
            query.removeAllRockets()
        }
    }

    internal fun getAllLaunches(): List<RocketLaunch> {
        return query.selectAllLaunchesInfo(::mapLaunchSelecting).executeAsList()
    }

    private fun mapLaunchSelecting(
        flightNumber: Long,
        missionName: String,
        launchYear: Int,
        rocketId: String,
        details: String?,
        launchSuccess: Boolean?,
        launchDateUTC: String,
        missionPatchUrl: String?,
        articleUrl: String?,
        rocket_id: String?,
        name: String?,
        type: String?
    ): RocketLaunch {
        return RocketLaunch(
            flightNumber = flightNumber.toInt(),
            missionName = missionName,
            launchYear = launchYear,
            details = details,
            launchDateUTC = launchDateUTC,
            launchSuccess = launchSuccess,
            rocket = Rocket(
                id = rocketId,
                name = name!!,
                type = type!!
            ),
            links = Links(
                missionPatchUrl = missionPatchUrl,
                articleUrl = articleUrl
            )
        )
    }

    internal fun createLaunches(launches: List<RocketLaunch>) {
        query.transaction {
            launches.forEach { launch ->
                val rocket = query.selectRocketById(launch.rocket.id).executeAsOneOrNull()
                if (rocket == null) {
                    insertRocket(launch)
                }

                insertLaunch(launch)
            }
        }
    }

    private fun insertRocket(launch: RocketLaunch) {
        query.insertRocket(
            id = launch.rocket.id,
            name = launch.rocket.name,
            type = launch.rocket.type
        )
    }

    private fun insertLaunch(launch: RocketLaunch) {
        query.insertLaunch(
            flightNumber = launch.flightNumber.toLong(),
            missionName = launch.missionName,
            launchYear = launch.launchYear,
            rocketId = launch.rocket.id,
            details = launch.details,
            launchSuccess = launch.launchSuccess ?: false,
            launchDateUTC = launch.launchDateUTC,
            missionPatchUrl = launch.links.missionPatchUrl,
            articleUrl = launch.links.articleUrl
        )
    }
}
