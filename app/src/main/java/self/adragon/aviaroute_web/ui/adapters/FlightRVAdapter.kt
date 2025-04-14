package self.adragon.aviaroute_web.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import okhttp3.internal.format
import self.adragon.aviaroute_web.R
import self.adragon.aviaroute_web.data.model.Flight
import self.adragon.aviaroute_web.data.model.converters.LocalDateConverter

class FlightRVAdapter(private val onClick: (Flight) -> Unit) :
    RecyclerView.Adapter<FlightRVAdapter.FlightRVViewHolder>() {

    private var flights: List<Flight> = listOf()

    fun fillData(newFlights: List<Flight>) {
        flights = newFlights
    }

    inner class FlightRVViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val countTransferTextView: TextView = itemView.findViewById(R.id.countTransfer)
        val priceTextView: TextView = itemView.findViewById(R.id.flightSummaryTextView)
        val departureDateTextView: TextView = itemView.findViewById(R.id.departureDateTextView)
        val destinationDateTextView: TextView = itemView.findViewById(R.id.destinationDateTextView)

        val flightCardView: CardView = itemView.findViewById(R.id.flightCardView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlightRVViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.search_flight_list_item, parent, false)

        return FlightRVViewHolder(itemView)
    }

    override fun getItemCount(): Int = flights.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: FlightRVViewHolder, position: Int) {

        val flight = flights[position]
        val segmentsSize = flight.segments.size
        holder.apply {
            val depDateString = LocalDateConverter.fromEpochSecondsStringDate(flight.departureEpoch)
            val arrDateString = LocalDateConverter.fromEpochSecondsStringDate(flight.arrivalEpoch)

            val economy = flight.segments.sumOf { it.details.economy.price }
            val priority = flight.segments.sumOf { it.details.priority.price }
            val business = flight.segments.sumOf { it.details.business.price }
            val priceText =
                format("Общая цена билета: [%.0f, %.0f, %.0f] у.е.", economy, priority, business)

            departureDateTextView.text = depDateString
            destinationDateTextView.text = arrDateString
            priceTextView.text = priceText

            flightCardView.setOnClickListener { onClick(flight) }

            val message = when (segmentsSize) {
                1 -> "Прямой"
                2 -> "Транзит"
                else -> "Количество пересадок - $segmentsSize"
            }
            countTransferTextView.text = message
        }
    }
}