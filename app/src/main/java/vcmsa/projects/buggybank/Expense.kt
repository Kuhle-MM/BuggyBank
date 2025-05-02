package vcmsa.projects.buggybank

data class Expense(
    val title: String,
    val type: String,
    val amount: Double,
    val category: String,
    val paymentMethod: String,
    val date: String,
    val startTime: String,
    val endTime: String,
    val description: String,
    val imagePath: String? = null
)