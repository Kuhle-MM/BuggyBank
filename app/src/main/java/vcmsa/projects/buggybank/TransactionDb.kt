package vcmsa.projects.buggybank

import android.view.SurfaceControl.Transaction
import android.widget.Toast

class TransactionDb {
    var transactionsList: ArrayList<TransactionModel?> = arrayListOf<TransactionModel?>()

    fun addToTransactionList(transaction: TransactionModel ){

        transactionsList.add(transaction)
    }

}