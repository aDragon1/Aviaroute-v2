package self.adragon.aviaroute_web.ui.fragments

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import okhttp3.internal.format
import self.adragon.aviaroute_web.R
import java.util.Calendar

class DatePicker(private val hint: String? = null, private val onDatePick: (String) -> Unit) :
    Fragment(R.layout.date_picker) {

    private lateinit var datePickerRoot: LinearLayout
    private lateinit var dateTextView: TextView

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        datePickerRoot = view.findViewById(R.id.datePickerRoot)
        dateTextView = view.findViewById(R.id.dateTextView)
        if (hint != null) dateTextView.hint = hint

        datePickerRoot.setOnClickListener {
            val onDateSetListener =
                DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                    val dayOfWeek = getDayOfWeekString(year, month, dayOfMonth)
                    val date = getTimeInString(year, month, dayOfMonth)

                    dateTextView.text = "$dayOfWeek, $dayOfMonth ${getMonthString(month)} $year"
                    onDatePick(date)
                }

            val calendar = Calendar.getInstance()
            val year = calendar[Calendar.YEAR]
            val month = calendar[Calendar.MONTH]
            val day = calendar[Calendar.DAY_OF_MONTH]

            val datePickerDialog = DatePickerDialog(
                requireContext(), onDateSetListener, year, month, day
            )

            datePickerDialog.show()
        }
    }

    private fun getDayOfWeekString(year: Int, month: Int, dayOfMonth: Int): String {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)

        val dayOfWeek = calendar[Calendar.DAY_OF_WEEK]
        return when (dayOfWeek) {
            Calendar.SUNDAY -> "Воскресенье"
            Calendar.MONDAY -> "Понедельник"
            Calendar.TUESDAY -> "Вторник"
            Calendar.WEDNESDAY -> "Среда"
            Calendar.THURSDAY -> "Четверг"
            Calendar.FRIDAY -> "Пятница"
            Calendar.SATURDAY -> "Суббота"
            else -> "Неизвестно"
        }
    }

    private fun getMonthString(month: Int) = when (month) {
        0 -> "янв."
        1 -> "фев."
        2 -> "мар."
        3 -> "апр."
        4 -> "май"
        5 -> "июн."
        6 -> "июл."
        7 -> "авг."
        8 -> "сен."
        9 -> "окт."
        10 -> "ноя."
        11 -> "дек."
        else -> ""
    }

    private fun getTimeInString(year: Int, month: Int, dayOfMonth: Int): String {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)

        return format("%02d.%02d.%d", dayOfMonth, month + 1, year)
    }
}