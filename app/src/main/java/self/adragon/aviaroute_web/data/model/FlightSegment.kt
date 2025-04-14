package self.adragon.aviaroute_web.data.model

import kotlinx.serialization.Serializable

@Serializable
data class FlightSegment(
    val departureAirportLocation: AirportLocation,
    val arrivalAirportLocation: AirportLocation,
    val departureEpoch: Long,
    val arrivalEpoch: Long,
    val details: FlightDetails,
    val position: Int,
) : java.io.Serializable