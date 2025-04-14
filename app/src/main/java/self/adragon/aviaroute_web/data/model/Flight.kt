package self.adragon.aviaroute_web.data.model

data class Flight(
    val segments: List<FlightSegment>,
    val departureEpoch: Long,
    val arrivalEpoch: Long,
)