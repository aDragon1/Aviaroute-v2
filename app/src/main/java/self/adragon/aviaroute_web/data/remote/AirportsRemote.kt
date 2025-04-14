package self.adragon.aviaroute_web.data.remote

import self.adragon.aviaroute_web.data.model.AirportLocation

class AirportsRemote : ApiRemote() {
    override val route = "airports"

    suspend fun fetchLocations() = fetchJSON<List<AirportLocation>>("$route/location_list")
}