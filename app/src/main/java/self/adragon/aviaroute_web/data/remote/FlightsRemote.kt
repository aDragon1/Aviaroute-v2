package self.adragon.aviaroute_web.data.remote

import io.ktor.client.request.forms.FormDataContent
import io.ktor.http.Parameters
import self.adragon.aviaroute_web.data.model.Flight
import self.adragon.aviaroute_web.data.model.FlightSegment
import self.adragon.aviaroute_web.data.model.converters.FlightsConverter

class FlightsRemote : ApiRemote() {
    override val route = "flights"

    suspend fun fetchFlights(
        depID: Int,
        arrID: Int,
        depDate: String
    ): List<Flight> {
        val postParams = FormDataContent(Parameters.build {
            append("departure_id", "$depID")
            append("arrival_id", "$arrID")
            append("departure_date", depDate)
        })

        val allSegments =
            postJSON<List<List<FlightSegment>>>("$route/search_by_airports_and_date", postParams)
        val flights = FlightsConverter.allSegmentsToFlight(allSegments)

        return flights
    }
}