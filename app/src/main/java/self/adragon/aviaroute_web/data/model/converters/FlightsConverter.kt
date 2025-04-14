package self.adragon.aviaroute_web.data.model.converters

import self.adragon.aviaroute_web.data.model.Flight
import self.adragon.aviaroute_web.data.model.FlightSegment

object FlightsConverter {

    // List<List<FlightSegment>> => List<Flight>
    fun allSegmentsToFlight(allSegments: List<List<FlightSegment>>) = allSegments.mapNotNull {
        if (it.isEmpty()) return@mapNotNull null

        val depEpoch = it.first().departureEpoch
        val arrEpoch = it.last().arrivalEpoch

        Flight(it, depEpoch, arrEpoch)
    }
}