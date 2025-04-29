package vcmsa.projects.buggybank

import java.sql.Time
import java.util.Date

data class Transaction(
        var title: String = "",
        var transactionType: String = "",
        var paymentMethod: String = "",
        var amount: Double = 0.0,
        var category: String = "",
        var dateOfTransaction: Date = Date(),
        var endTime: Time = Time(0),
        var startTime: Time = Time(0),
        var description: String = ""
)