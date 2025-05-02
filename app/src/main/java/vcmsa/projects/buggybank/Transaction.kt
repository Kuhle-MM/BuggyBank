package vcmsa.projects.buggybank

import java.sql.Time
import java.util.Date

data class Transaction(
        val title: String = "",
        val category: String = "",
        val paymentMethod: String = "",
        val amount: Double = 0.0,
        val dateOfTransaction: String = "",
        val transactionType: String = "",
        val description: String = "",
        val startTime: String = "",
        val endTime: String = "",
        var isExpanded: Boolean = false
)