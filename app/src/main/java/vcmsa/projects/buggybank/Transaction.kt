package vcmsa.projects.buggybank

import java.sql.Time
import java.util.Date

data class Transaction(
        val title: String,
        val category: String,
        val paymentMethod: String,
        val amount: Double,
        val dateOfTransaction: String,  // Change to Date if you need proper Date handling
        val transactionType: String,
        val description: String,
        val startTime: String,
        val endTime: String,
        var isExpanded: Boolean = false  // To track if the item is expanded
)