package vcmsa.projects.buggybank

data class ReportDB(
    var Title : String,
    val Type : String,
    val Amount : Double,
    val ImageURL : String,
    val Date : String,
    val Category : String,
    val PaymentMethod : String,
    val Description : String?,
    val StartTime : String,
    val EndTime : String
)
