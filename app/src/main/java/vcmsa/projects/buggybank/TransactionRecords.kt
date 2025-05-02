package vcmsa.projects.buggybank

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBindings
import androidx.viewbinding.ViewBindings.findChildViewById
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.w3c.dom.Text
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

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
    private lateinit var adapter: TransactionRecordsAdapter
    private lateinit var transactionsList: RecyclerView
    private val transactions = ArrayList<Transaction>()
    private lateinit var noTransactions :TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val layout = inflater.inflate(R.layout.fragment_transaction_records, container, false)

        transactionsList = layout.findViewById(R.id.rvTransactions)
        noTransactions = layout.findViewById<TextView>(R.id.tvNoTransactions)
        adapter = TransactionRecordsAdapter(transactions)
        transactionsList.adapter = adapter

        // Initialize Firebase and fetch data

        rootNode = FirebaseDatabase.getInstance()
        userReference = rootNode.getReference("transactions") // Be consistent with your key names


        fetchTransactionsFromFirebase()
        return layout
    }

    private fun fetchTransactionsFromFirebase() {

        lifecycleScope.launch {
            try {
                // Move network-related code to background thread
                val snapshot = withContext(Dispatchers.IO) {
                    val dataSnapshot = suspendCoroutine<DataSnapshot> { continuation ->
                        userReference.addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(dataSnapshot: DataSnapshot) {
                                continuation.resume(dataSnapshot)
                            }

                            override fun onCancelled(databaseError: DatabaseError) {
                                continuation.resumeWithException(databaseError.toException())
                            }
                        })
                    }
                    dataSnapshot
                }

                // Update the UI on the main thread
                transactions.clear()
                for (snapshot1 in snapshot.children) {
                    val transaction = snapshot1.getValue(Transaction::class.java)
                    if (transaction != null) {
                        transactions.add(transaction)
                    }
                }
                adapter.notifyDataSetChanged()

            } catch (e: Exception) {
                // Handle errors, e.g. network failures
                context?.let {
                    Toast.makeText(
                        it,
                        "You have no transactions",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                val TAG = "TransactionRecords"
                Log.e(TAG, "Failed to Fetch Transactions ", e )
                noTransactions.visibility = View.VISIBLE;

            }
        }
    }
}
