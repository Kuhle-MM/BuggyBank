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
    private lateinit var title: EditText
    private lateinit var transactionType: Spinner
    private lateinit var amount: EditText
    private lateinit var category: Spinner
    private lateinit var paymentMethod: Spinner
    private lateinit var dateOfTransaction: EditText
    private lateinit var startTime: EditText
    private lateinit var endTime: EditText
    private lateinit var description: EditText
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


        title = view.findViewById(R.id.etTitle)
        transactionType = view.findViewById(R.id.spType)
        amount = view.findViewById(R.id.etAmount)
        category = view.findViewById(R.id.spCategory)
        paymentMethod = view.findViewById(R.id.spPayment)
        dateOfTransaction = view.findViewById(R.id.etDate)
        startTime = view.findViewById(R.id.etStartTime)
        endTime = view.findViewById(R.id.etEndTime)
        description = view.findViewById(R.id.editTextDescription)
        btnAdd = view.findViewById(R.id.btnAdd)


        listOf(dateOfTransaction, startTime, endTime).forEach {
            it.isFocusable = false
            it.isClickable = true
        }


        transactionType.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            listOf("Expense", "Income")
        )
        category.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            listOf("Food", "Transport", "Bills", "Shopping", "Other")
        )
        paymentMethod.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            listOf("Cash", "Credit Card", "Debit Card", "Online")
        )

        // Date picker
        dateOfTransaction.setOnClickListener { showDatePicker(dateOfTransaction) }

        // Time pickers
        startTime.setOnClickListener { showTimePicker(startTime) }
        endTime.setOnClickListener   { showTimePicker(endTime) }

        // Add-button
        btnAdd.setOnClickListener {
            val title    = title.text.toString().trim()
            val type     = transactionType.selectedItem as String
            val amount   = amount.text.toString().toDoubleOrNull() ?: 0.0
            val category = category.selectedItem as String
            val payment  = paymentMethod.selectedItem as String
            val date     = dateOfTransaction.text.toString()
            val start    = startTime.text.toString()
            val end      = endTime.text.toString()
            val desc     = description.text.toString().trim()

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

            // TODO: send `expense` to ViewModel / DB / parent Activity
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