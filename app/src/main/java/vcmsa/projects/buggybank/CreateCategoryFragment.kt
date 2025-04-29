package vcmsa.projects.buggybank

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class CreateCategoryFragment : Fragment() {

    private lateinit var database: DatabaseReference
    private lateinit var categoryRecyclerView: RecyclerView
    private lateinit var categoryNameInput: EditText
    private lateinit var typeRadioGroup: RadioGroup
    private lateinit var expenseRadioButton: RadioButton
    private lateinit var incomeRadioButton: RadioButton
    private lateinit var addCategoryButton: Button

    private val categoryList = mutableListOf( "Clothing", "Entertainment", "Food", "Fuel",
        "Groceries", "Health", "Housing", "Internet", "Insurance"
    )
    private lateinit var categoryAdapter: CategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,  savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_create_category, container, false)
        categoryRecyclerView   = view.findViewById(R.id.categoryRecyclerView)
        categoryNameInput      = view.findViewById(R.id.categoryNameInput)
        typeRadioGroup         = view.findViewById(R.id.typeRadioGroup)
        expenseRadioButton     = view.findViewById(R.id.expenseRadioButton)
        incomeRadioButton      = view.findViewById(R.id.incomeRadioButton)
        addCategoryButton      = view.findViewById(R.id.addCategoryButton)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        database = FirebaseDatabase.getInstance().reference.child("categories")
        categoryAdapter = CategoryAdapter(categoryList)
        categoryRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        categoryRecyclerView.adapter = categoryAdapter
        addCategoryButton.setOnClickListener { addCategory() }
    }

    private fun addCategory() {
        val name = categoryNameInput.text.toString().trim()
        if (name.isEmpty()) {
            Toast.makeText(requireContext(),
                "Please enter a category name.",
                Toast.LENGTH_SHORT).show()
            return
        }

        val type = when {
            expenseRadioButton.isChecked -> "Expense"
            incomeRadioButton.isChecked  -> "Income"
            else -> {
                Toast.makeText(requireContext(),
                    "Please select Expense or Income.",
                    Toast.LENGTH_SHORT).show()
                return
            }
        }

        val newCategory = "$name ($type)"
        categoryList.add(newCategory)
        categoryAdapter.notifyItemInserted(categoryList.size - 1)

        // Save to Firebase
        val database = FirebaseDatabase.getInstance().reference.child("categories")
        val categoryData = mapOf(
            "name" to name,
            "type" to type
        )

        database.push().setValue(categoryData)
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "Category saved to database", Toast.LENGTH_SHORT).show()
                Log.d("CreateCategory", "Saved: $categoryData")

            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Failed to save to database", Toast.LENGTH_SHORT).show()
                Log.e("CreateCategory", "Firebase save failed", it)
            }

        // Clear UI
        categoryNameInput.text.clear()
        typeRadioGroup.clearCheck()
    }

}
