package vcmsa.projects.buggybank

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

private lateinit var rvReportList: MutableList<Transaction>
private lateinit var transactionAdapter: ArrayAdapter<String> // For display
private lateinit var listView: ListView


class ReportFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_report, container, false)

        listView = view.findViewById(R.id.rvReportList)
        rvReportList = mutableListOf()

        // Firebase auth & db
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            val userId = user.uid
            val dbRef = FirebaseDatabase.getInstance().getReference("Transactions").child(userId)


            dbRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    rvReportList.clear()
                    for (txSnapshot in snapshot.children) {
                        val tx = txSnapshot.getValue(Transaction::class.java)
                        tx?.let { rvReportList.add(it) }
                    }

                    val listItems =
                        rvReportList.map { "${it.dateOfTransaction} - ${it.description} - R${it.amount}" }
                    transactionAdapter = ArrayAdapter(
                        requireContext(),
                        android.R.layout.simple_list_item_1,
                        listItems
                    )
                    listView.adapter = transactionAdapter
                }

                override fun onCancelled(error: DatabaseError) {
                    if (isAdded && context != null){
                        Toast.makeText(context, "Error loading data", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        Log.e("ReportFragment", "Fragment is not attached or context is null")
                    }
                }
            })
        } else {
            Toast.makeText(context, "No user logged in", Toast.LENGTH_SHORT).show()
        }
        return view
    }


}