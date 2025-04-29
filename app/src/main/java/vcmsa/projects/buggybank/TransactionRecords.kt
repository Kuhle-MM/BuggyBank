package vcmsa.projects.buggybank

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


/**
 * A simple [Fragment] subclass.
 * Use the [TransactionRecords.newInstance] factory method to
 * create an instance of this fragment.
 */
class TransactionRecords : Fragment() {
    private lateinit var rootNode: FirebaseDatabase
    private lateinit var userReference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val layout = inflater.inflate(R.layout.fragment_transaction_records, container, false)
        val transactionsList: RecyclerView = layout.findViewById(R.id.rvTransactions)

         //is the database
        userReference = rootNode.getReference("Transactions") //finds this in the database; this is calling all the data that is linked with the users name

        return layout
    }

    private fun fetchTransactionsFromFirebase() {
        rootNode = FirebaseDatabase.getInstance()
        val databaseRef = FirebaseDatabase.getInstance().getReference("transactions")

        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                transactions.clear()
                for (child in snapshot.children) {
                    val transaction = child.getValue(Transaction::class.java)
                    if (transaction != null) {
                        transactions.add(transaction)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "Failed to load transactions", Toast.LENGTH_SHORT).show()
            }
        })
    }
}