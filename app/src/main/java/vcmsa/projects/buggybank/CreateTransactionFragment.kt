package vcmsa.projects.buggybank

import Expense
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import java.text.SimpleDateFormat
import java.util.*

class CreateTransactionFragment : Fragment() {
    private lateinit var etTitle: EditText
    private lateinit var spType: Spinner
    private lateinit var etAmount: EditText
    private lateinit var spCategory: Spinner
    private lateinit var spPayment: Spinner
    private lateinit var etDate: EditText
    private lateinit var etStartTime: EditText
    private lateinit var etEndTime: EditText
    private lateinit var etDescription: EditText
    private lateinit var btnAdd: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_transaction, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        etTitle       = view.findViewById(R.id.etTitle)
        spType        = view.findViewById(R.id.spType)
        etAmount      = view.findViewById(R.id.etAmount)
        spCategory    = view.findViewById(R.id.spCategory)
        spPayment     = view.findViewById(R.id.spPayment)
        etDate        = view.findViewById(R.id.etDate)
        etStartTime   = view.findViewById(R.id.etStartTime)
        etEndTime     = view.findViewById(R.id.etEndTime)
        etDescription = view.findViewById(R.id.editTextDescription)
        btnAdd        = view.findViewById(R.id.btnAdd)


        listOf(etDate, etStartTime, etEndTime).forEach {
            it.isFocusable = false
            it.isClickable = true
        }


        spType.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            listOf("Expense", "Income")
        )
        spCategory.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            listOf("Clothing", "Entertainment", "Food", "Fuel", "Groceries", "Health", "Housing", "Internet", "Insurance")
        )
        spPayment.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            listOf("Cash", "Credit Card", "Debit Card")
        )

        // Date picker
        etDate.setOnClickListener { showDatePicker(etDate) }

        // Time pickers
        etStartTime.setOnClickListener { showTimePicker(etStartTime) }
        etEndTime.setOnClickListener   { showTimePicker(etEndTime) }

        // Add-button
        btnAdd.setOnClickListener {
            val title    = etTitle.text.toString().trim()
            val type     = spType.selectedItem as String
            val amount   = etAmount.text.toString().toDoubleOrNull() ?: 0.0
            val category = spCategory.selectedItem as String
            val payment  = spPayment.selectedItem as String
            val date     = etDate.text.toString()
            val start    = etStartTime.text.toString()
            val end      = etEndTime.text.toString()
            val desc     = etDescription.text.toString().trim()

            if (title.isEmpty() || amount <= 0.0 || date.isEmpty()) {
                Toast.makeText(requireContext(),
                    "Please fill Title, Amount & Date",
                    Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val expense = Expense(
                title, type, amount, category, payment,
                date, start, end, desc
            )


            Toast.makeText(requireContext(),
                "Added: $expense",
                Toast.LENGTH_LONG).show()
        }
    }
    private fun showDatePicker(target: EditText) {
        val cal = Calendar.getInstance()
        DatePickerDialog(
            requireContext(),
            { _, y, m, d ->
                cal.set(y, m, d)
                val fmt = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                target.setText(fmt.format(cal.time))
            },
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun showTimePicker(target: EditText) {
        val cal = Calendar.getInstance()
        TimePickerDialog(
            requireContext(),
            { _, h, min ->
                target.setText(String.format("%02d:%02d", h, min))
            },
            cal.get(Calendar.HOUR_OF_DAY),
            cal.get(Calendar.MINUTE),
            true
        ).show()
    }
}