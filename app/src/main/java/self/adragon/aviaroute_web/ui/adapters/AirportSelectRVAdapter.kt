package self.adragon.aviaroute_web.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import self.adragon.aviaroute_web.R
import self.adragon.aviaroute_web.data.model.AirportLocation

class AirportSelectRVAdapter(private val onClick: (AirportLocation) -> Any) :
    RecyclerView.Adapter<AirportSelectRVAdapter.FlightSearchForResultVH>() {

    private var data: List<AirportLocation> = emptyList()
    fun fillData(airportNames: List<AirportLocation>) {
        data = airportNames
        notifyDataSetChanged()
    }

    inner class FlightSearchForResultVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val searchItemTextView: TextView = itemView.findViewById(R.id.searchItemTextView)
        val searchItemCardView: CardView = itemView.findViewById(R.id.searchItemCardView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlightSearchForResultVH {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.search_item, parent, false)

        return FlightSearchForResultVH(itemView)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: FlightSearchForResultVH, position: Int) {
        val item = data[position]
        holder.searchItemTextView.text = item.airportName

        holder.searchItemCardView.setOnClickListener { onClick(item) }
    }
}