package vcmsa.projects.buggybank

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SetBudgetFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SetBudgetFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout
        val view = inflater.inflate(R.layout.fragment_set_budget, container, false)

        val seekBar = view.findViewById<SeekBar>(R.id.seekBarMin)
        val txtSeekValue = view.findViewById<TextView>(R.id.txtSeekValue)
        // Now safely access views from the inflated layout
        val changingHeading = view.findViewById<TextView>(R.id.txtHeadingChange)

        val btnEntertainment = view.findViewById<Button>(R.id.btnEntertainment)
        val btnHealth = view.findViewById<Button>(R.id.btnHealth)
        val btnHousing = view.findViewById<Button>(R.id.btnHousing)
        val btnClothing = view.findViewById<Button>(R.id.btnClothing)
        val btnFood = view.findViewById<Button>(R.id.btnFood)
        val btnFuel = view.findViewById<Button>(R.id.btnFuel)
        val btnGroceries = view.findViewById<Button>(R.id.btnGroceries)
        val btnInsurance = view.findViewById<Button>(R.id.btnInsurance)
        val btnInternet = view.findViewById<Button>(R.id.btnInternet)

        val btnSetCatMinimum = view.findViewById<Button>(R.id.btnSet)

        // Set heading text when buttons are clicked
        btnEntertainment.setOnClickListener { changingHeading.text = "Entertainment" }
        btnHealth.setOnClickListener { changingHeading.text = "Health" }
        btnHousing.setOnClickListener { changingHeading.text = "Housing" }
        btnClothing.setOnClickListener { changingHeading.text = "Clothing" }
        btnFood.setOnClickListener { changingHeading.text = "Food" }
        btnFuel.setOnClickListener { changingHeading.text = "Fuel" }
        btnGroceries.setOnClickListener { changingHeading.text = "Groceries" }
        btnInsurance.setOnClickListener { changingHeading.text = "Insurance" }
        btnInternet.setOnClickListener { changingHeading.text = "Internet" }

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                txtSeekValue.text = "R$progress"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
        btnSetCatMinimum.setOnClickListener {
            val selectedCategory = changingHeading.text.toString()
            val maxValue = seekBar.progress

            if (selectedCategory.isNotEmpty()) {
                val budget = Budget(category = selectedCategory, maximumValue = maxValue)

                val dbRef = FirebaseDatabase.getInstance().getReference("budgets")
                dbRef.push().setValue(budget)
                    .addOnSuccessListener {
                        Toast.makeText(requireContext(), "Budget saved!", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(requireContext(), "Failed to save", Toast.LENGTH_SHORT).show()
                    }
            } else {
                Toast.makeText(requireContext(), "Select a category first", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SetBudgetFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SetBudgetFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}