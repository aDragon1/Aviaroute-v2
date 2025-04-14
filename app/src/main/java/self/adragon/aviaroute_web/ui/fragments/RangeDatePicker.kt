package self.adragon.aviaroute_web.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.util.Pair
import androidx.fragment.app.Fragment
import com.google.android.material.datepicker.MaterialDatePicker
import self.adragon.aviaroute_web.R
import self.adragon.aviaroute_web.data.model.converters.LocalDateConverter

class RangeDatePicker(
    private val hint: String? = null,
    private val onDatePick: (startDate: String, endDate: String) -> Unit
) : Fragment(R.layout.date_picker) {

    private lateinit var datePickerRoot: LinearLayout
    private lateinit var dateTextView: TextView

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        datePickerRoot = view.findViewById(R.id.datePickerRoot)
        dateTextView = view.findViewById(R.id.dateTextView)
        if (hint != null) dateTextView.hint = hint

        datePickerRoot.setOnClickListener {
            val datePickerDialog = MaterialDatePicker.Builder.dateRangePicker()
                .setTheme(R.style.ThemeMaterialCalendar)
                .setTitleText("Выберите диапазон дат")
                .setSelection(Pair(null, null))
                .build()

            datePickerDialog.addOnPositiveButtonClickListener {
                val pattern = "dd.MM.yyyy, HH:mm"
                val startDate =
                    LocalDateConverter.fromEpochSecondsStringDate(it.first / 1_000, pattern)
                val endDate =
                    LocalDateConverter.fromEpochSecondsStringDate(it.second / 1_000, pattern)

                onDatePick(startDate, endDate)

                dateTextView.text = "$startDate - $endDate"
            }
            datePickerDialog.show(childFragmentManager, "datePicker")
        }
    }
}
