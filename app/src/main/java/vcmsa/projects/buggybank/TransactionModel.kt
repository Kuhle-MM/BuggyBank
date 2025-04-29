package vcmsa.projects.buggybank

import java.sql.Time
import java.util.Date

class TransactionModel (

        public var title: String,
        public var transactionType: String,
        public var paymentMethod: String,
        public var amount: Double,
        public var category: String,
        public var dateOfTransaction: Date,
        public var endTime: Time,
        public var startTime: Time,
        public var description: String



)