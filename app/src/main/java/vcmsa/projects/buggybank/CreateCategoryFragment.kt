package vcmsa.projects.buggybank

import android.content.ContentValues.TAG
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

/**
 * Fragment for creating a new category.
 *
 * This fragment will allow the user to input a name for the category and select whether it is an expense or income.
 * The category will be saved to the Firebase Realtime Database under the current user's UID.
 */
private const val TAG = "CreateCategoryFragment"

class CreateCategoryFragment : Fragment() {
    
    /**
     * The database reference for the categories.
     */
    private lateinit var database: DatabaseReference
    
    /**
     * The RecyclerView for displaying the list of categories.
     */
    private lateinit var categoryRecyclerView: RecyclerView
    
    /**
     * The EditText for inputting the name of the category.
     */
    private lateinit var categoryNameInput: EditText
    
    /**
     * The RadioGroup for selecting whether the category is an expense or income.
     */
    private lateinit var typeRadioGroup: RadioGroup
    
    /**
     * The RadioButton for selecting expense.
     */
    private lateinit var expenseRadioButton: RadioButton
    
    /**
     * The RadioButton for selecting income.
     */
    private lateinit var incomeRadioButton: RadioButton
    
    /**
     * The Button for adding the category.
     */
    private lateinit var addCategoryButton: Button
    
    /**
     * The list of categories.
     */
    private val categoryList = mutableListOf(
        "Clothing", "Entertainment", "Food", "Fuel",
        "Groceries", "Health", "Housing", "Internet", "Insurance"
    )
    
    /**
     * The adapter for the RecyclerView.
     */
    private lateinit var categoryAdapter: CategoryAdapter
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "onCreateView")
        val view = inflater.inflate(R.layout.fragment_create_category, container, false)
        categoryRecyclerView = view.findViewById(R.id.categoryRecyclerView)
        categoryNameInput = view.findViewById(R.id.categoryNameInput)
        typeRadioGroup = view.findViewById(R.id.typeRadioGroup)
        expenseRadioButton = view.findViewById(R.id.expenseRadioButton)
        incomeRadioButton = view.findViewById(R.id.incomeRadioButton)
        addCategoryButton = view.findViewById(R.id.addCategoryButton)
        return view
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated")
        
        // Initialize user-scoped database reference
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return
        database = FirebaseDatabase.getInstance()
            .getReference("users").child(uid).child("categories")
        
        // Setup RecyclerView
        categoryAdapter = CategoryAdapter(categoryList)
        categoryRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        categoryRecyclerView.adapter = categoryAdapter
        
        addCategoryButton.setOnClickListener { addCategory() }
    }
    
    /**
     * Adds a new category to the database.
     */
    private fun addCategory() {
        Log.d(TAG, "addCategory")
        val name = categoryNameInput.text.toString().trim()
        if (name.isEmpty()) {
            Toast.makeText(requireContext(), "Please enter a category name.", Toast.LENGTH_SHORT)
                .show()
            return
        }
        
        val type = when {
            expenseRadioButton.isChecked -> "Expense"
            incomeRadioButton.isChecked -> "Income"
            else -> {
                Toast.makeText(
                    requireContext(),
                    "Please select Expense or Income.",
                    Toast.LENGTH_SHORT
                ).show()
                return
            }
        }
        
        // Push to Firebase under current user
        
        val categoryData = mapOf("name" to name, "type" to type)
        
//        val users = FirebaseAuth.getInstance().currentUser
//        if (users != null) {
//            database.child("categories").child(users.uid).get().addOnCompleteListener { task ->
//                if (!task.result.exists()) {
//                    database.child("categories").child(users.uid).setValue(true)
//                }
//            }
//        }
        val user = FirebaseAuth.getInstance().currentUser ?: run {
            Log.e(TAG, "No user logged in")
            return
        }
        
        
        database.child("categories").child(user.uid).push().setValue(categoryData)
            .addOnSuccessListener {
                Log.d(TAG, "Saved under Users/${user.uid}/Category: $categoryData")
                Toast.makeText(requireContext(), "Category added", Toast.LENGTH_SHORT).show()
                
                // update local list and UI
                val display = "$name ($type)"
                categoryList.add(display)
                categoryAdapter.notifyItemInserted(categoryList.size - 1)
                categoryNameInput.text.clear()
                typeRadioGroup.clearCheck()
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "Error adding category", exception)
                Toast.makeText(requireContext(), "Failed to add category", Toast.LENGTH_SHORT)
                    .show()
            }
    }
    
}