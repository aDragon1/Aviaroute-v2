package self.adragon.aviaroute_web.ui.fragments

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import okhttp3.internal.format
import self.adragon.aviaroute_web.R
import self.adragon.aviaroute_web.data.model.Flight
import self.adragon.aviaroute_web.data.model.converters.LocalDateConverter
import self.adragon.aviaroute_web.ui.adapters.SegmentsRVAdapter
import self.adragon.aviaroute_web.utils.LOG_TAG

class SearchedFlightInfo(private val flight: Flight) :
    DialogFragment(R.layout.search_result_flight_info), OnClickListener {

    var isShown = false

    private lateinit var backImageButton: ImageButton
    private lateinit var flightSearchInfoCodesTextView: TextView

    private lateinit var buyButton: Button
    private lateinit var totalFlightTimeTextView: TextView
    private lateinit var totalPriceTextView: TextView

    private lateinit var segmentsRecyclerView: RecyclerView

    override fun onCreateDialog(savedInstanceState: Bundle?) =
        Dialog(requireContext(), R.style.FullScreenDialogTheme)

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        isShown = true

        initViews(view)

        val codes = buildString {
            flight.segments.forEachIndexed { index, segment ->
                append(segment.departureAirportLocation.airportCode)
                append(" -> ")
                if (index == flight.segments.lastIndex)
                    append(segment.arrivalAirportLocation.airportCode)
            }
        }
        flightSearchInfoCodesTextView.text = codes
        buyButton.text = "Выбрать билет"

        Log.d(LOG_TAG, "Selected flight = $flight")
        val adapter = SegmentsRVAdapter(flight)
        segmentsRecyclerView.adapter = adapter

        val departureEpoch = flight.departureEpoch
        val arrivalEpoch = flight.arrivalEpoch
        val flightEpoch = arrivalEpoch - departureEpoch

        val economy = flight.segments.sumOf { it.details.economy.price }
        val priority = flight.segments.sumOf { it.details.priority.price }
        val business = flight.segments.sumOf { it.details.business.price }
        val priceText =
            format("Общая цена билета: [%.0f, %.0f, %.0f] у.е.", economy, priority, business)

        val totalFlightTimeStr = LocalDateConverter.fromEpochSecondToTimeString(flightEpoch)
        totalFlightTimeTextView.text = "Общее время перелёта - $totalFlightTimeStr"

        totalPriceTextView.text = "Общая цена билета - $priceText"
    }

    private fun initViews(view: View) {
        segmentsRecyclerView = view.findViewById(R.id.segmentsRecyclerView)
        segmentsRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        backImageButton = view.findViewById(R.id.backImageButton)
        flightSearchInfoCodesTextView = view.findViewById(R.id.flightSearchInfoCodesTextView)

        buyButton = view.findViewById(R.id.buyButton)
        totalFlightTimeTextView = view.findViewById(R.id.totalFlightTimeTextView)
        totalPriceTextView = view.findViewById(R.id.totalPriceTextView)

        buyButton.setOnClickListener(this)
        backImageButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buyButton -> {
                Toast.makeText(context, "Это уже другая работа (:", Toast.LENGTH_SHORT).show()
//                val departure = flight.flightAirportCodes.first()
//                val destination = flight.flightAirportCodes.last()
//                val totalPrice = flight.totalPrice.round()
//
//                val cond = purchasedViewModel.purchasedLiveData.value
//                    ?.any { it.flightIndex == flight.flightIndex } == true
//
//                val (titleText, messageText) = if (cond) "Повторное добавление билета" to
//                        "Вы уверены, что хотите повторно выбрать билет из $departure в $destination за $totalPrice?"
//                else "Добавление билета" to
//                        "Вы уверены, что хотите выбрать билет из $departure в $destination за $totalPrice?"
//
//                AlertDialog.Builder(requireContext())
//                    .setTitle(titleText)
//                    .setMessage(messageText)
//                    .setPositiveButton("Да") { _, _ ->
//                        insert(flight)
//                        val message = "Билет из $departure в $destination был выбран"
//                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
//
//                        dismiss()
//                    }
//                    .setNegativeButton("Нет") { _, _ -> }
//                    .show()
            }

            R.id.backImageButton -> dismiss()
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        isShown = false
    }

}