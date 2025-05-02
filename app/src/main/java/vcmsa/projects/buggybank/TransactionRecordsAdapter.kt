package vcmsa.projects.buggybank

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TransactionRecordsAdapter(private val transactions: List<Transaction>) :
    RecyclerView.Adapter<TransactionRecordsAdapter.TransactionViewHolder>() {

    inner class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tvName)
        val tvCategory: TextView = itemView.findViewById(R.id.tvCategory)
        val tvPaymentMethod: TextView = itemView.findViewById(R.id.tvPaymentType)
        val tvAmount: TextView = itemView.findViewById(R.id.tvAmount)
        val tvDate: TextView = itemView.findViewById(R.id.tvDate)

        // Uncomment when you want to add the  expanded view of the transactions
        //val tvTransactionType: TextView = itemView.findViewById(R.id.tvTransactionType)
        //val tvDescription: TextView = itemView.findViewById(R.id.tvDescription)
        //val tvStartDate: TextView = itemView.findViewById(R.id.tvExpStartDate)
        //val tvEndDate: TextView = itemView.findViewById(R.id.tvExpEndDate)

        // Commented out the expanded layout stuff
        // Uncomment when you want to add the  expanded view of the transactions
        // val expandedLayout: View = itemView.findViewById(R.id.cvExpandedTransaction)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.singletransaction, parent, false)
        return TransactionViewHolder(view)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val transaction = transactions[position]
        holder.tvTitle.text = transaction.title
        holder.tvCategory.text = transaction.category
        holder.tvPaymentMethod.text = transaction.paymentMethod
        holder.tvAmount.text = transaction.amount.toString()
        holder.tvDate.setText(transaction.dateOfTransaction.toString())

        // Uncomment when you want to add the  expanded view of the transactions
        //holder.tvTransactionType.text = transaction.transactionType
        //holder.tvDescription.text = transaction.description
        //holder.tvStartDate.text = transaction.startTime.toString()
        //holder.tvEndDate.text = transaction.endTime.toString()

        // Commented out expanded view toggle
        /*
        holder.expandedLayout.visibility = if (transaction.isExpanded) View.VISIBLE else View.GONE

        holder.itemView.setOnClickListener {
            transaction.isExpanded = !transaction.isExpanded
            notifyItemChanged(position)
        }
        */
    }

    override fun getItemCount(): Int = transactions.size
}
