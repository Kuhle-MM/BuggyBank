package vcmsa.projects.buggybank

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TransactionAdapter(private val transactions: List<Transaction>) :
RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    inner class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tvName)
        val tvCategory: TextView = itemView.findViewById(R.id.tvCategory)
        val tvPaymentMethod: TextView = itemView.findViewById(R.id.tvPaymentType)
        val tvAmount: TextView = itemView.findViewById(R.id.tvAmount)
        val tvDate: EditText = itemView.findViewById(R.id.tvDate)
        val tvTransactionType: TextView = itemView.findViewById(R.id.tvTransactionType)
        val tvDescription: TextView = itemView.findViewById(R.id.tvDescription)
        val tvStartTime: TextView = itemView.findViewById(R.id.tvStartTime)
        val tvEndTime: TextView = itemView.findViewById(R.id.tvEndTime)

        val expandedLayout: View = itemView.findViewById(R.id.expandedLayout)


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

        holder.tvTransactionType.text = transaction.transactionType
        holder.tvDescription.text = transaction.description
        holder.tvStartTime.text = transaction.startTime
        holder.tvEndTime.text = transaction.endTime

        //For the extended view
        holder.expandedLayout.visibility = if (transaction.isExpanded) View.VISIBLE else View.GONE

        holder.itemView.setOnClickListener {
            transaction.isExpanded = !transaction.isExpanded
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int = transactions.size
}
