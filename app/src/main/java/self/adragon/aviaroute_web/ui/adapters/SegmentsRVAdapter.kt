package self.adragon.aviaroute_web.ui.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import okhttp3.internal.format
import self.adragon.aviaroute_web.R
import self.adragon.aviaroute_web.data.model.Flight
import self.adragon.aviaroute_web.data.model.converters.LocalDateConverter
import self.adragon.aviaroute_web.utils.LOG_TAG

class SegmentsRVAdapter(private val flight: Flight) :
    RecyclerView.Adapter<SegmentsRVAdapter.SegmentsRVViewHolder>() {

    inner class SegmentsRVViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val flightNumberTextView: TextView = itemView.findViewById(R.id.flightNumberTextView)
        val departureTimeTextView: TextView = itemView.findViewById(R.id.departureTimeTextView)
        val departureAirportTextView: TextView =
            itemView.findViewById(R.id.departureAirportTextView)
        val arrivalTimeTextView: TextView = itemView.findViewById(R.id.arrivalTimeTextView)
        val arrivalAirportTextView: TextView = itemView.findViewById(R.id.arrivalAirportTextView)
        val flightSummaryTextView: TextView = itemView.findViewById(R.id.flightSummaryTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SegmentsRVViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.segment_item, parent, false)

        return SegmentsRVViewHolder(itemView)
    }

    override fun getItemCount(): Int = flight.segments.size


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: SegmentsRVViewHolder, position: Int) {
        val segment = flight.segments[position]
        Log.d(LOG_TAG, "pos = $position: $segment")
        holder.apply {
            val depDate = LocalDateConverter.fromEpochSecondsStringDate(segment.departureEpoch)
            val arrDate = LocalDateConverter.fromEpochSecondsStringDate(segment.arrivalEpoch)
            val economy = segment.details.economy.price
            val priority = segment.details.priority.price
            val business = segment.details.business.price
            val priceText = format("Цена: [%.2f, %.2f, %.2f]", economy, priority, business)

            val flightEpoch = segment.arrivalEpoch - segment.departureEpoch
            val flightTime = LocalDateConverter.fromEpochSecondToTimeString(flightEpoch)
            val tailNumber = segment.details.tailNumber

            flightNumberTextView.text = "Номер рейса: $tailNumber"
            departureTimeTextView.text = depDate
            departureAirportTextView.text = "${segment.departureAirportLocation}"
            arrivalTimeTextView.text = arrDate
            arrivalAirportTextView.text = "${segment.arrivalAirportLocation}"
            flightSummaryTextView.text = "$priceText\n$flightTime"
        }
    }
}