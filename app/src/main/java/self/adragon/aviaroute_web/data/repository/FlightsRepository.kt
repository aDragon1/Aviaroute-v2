package self.adragon.aviaroute_web.data.repository

import self.adragon.aviaroute_web.data.remote.FlightsRemote

object FlightsRepository {
    private val remote = FlightsRemote()

    suspend fun getFlights(depID: Int, arrID: Int, depDate: String) =
        remote.fetchFlights(depID, arrID, depDate)
}