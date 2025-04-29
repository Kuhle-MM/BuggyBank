package vcmsa.projects.buggybank

import android.content.Intent
import android.graphics.Paint
import android.graphics.pdf.PdfDocument

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    var reportArray =ArrayList<ReportDB>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val intent = Intent(this,menubar::class.java)
        startActivity(intent)

    }

    
    
}

    //   docsFolder.mkdirs()
}
val pdfReportDocument = PdfDocument()
val reportInfo = PdfDocument.PageInfo.Builder(300,600,1).create()//height and width of the page
val page = pdfReportDocument.startPage(reportInfo)
val canvas = page.canvas
val paint = Paint()
//size of paint

//   fun createPDF (view: View)
//    {
//        val docsFolder = File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).toString())
//        if(!docsFolder.exists()){
//        paint.textSize = 30f
//
//        //Populating reportArray
//        reportArray.add(ReportDB("McDonalds", "Expense", 126.90,"https://picsum.photos/200/300", "2025-03-28", "Food", "Cash", "Took my wife out for our McD's dates", "08:55:63", "08:59:02"))
//        reportArray.add(ReportDB("Spar", "Expense", 433.86,"https://picsum.photos/200/300", "2025-03-28", "Groceries", "Card", null, "09:55:63", "09:59:02"))
//        reportArray.add(ReportDB("Astron Energy", "Expense", 300.00,"https://picsum.photos/200/300", "2025-03-28", "Petrol", "Card", "Emergency petrol purchase for cousin", "10:55:63", "10:59:02"))
//        reportArray.add(ReportDB("CottonOn", "Expense", 275.45,"https://picsum.photos/200/300", "2025-03-28", "Clothes", "Cash", "Birthday gift for friend", "14:55:63", "14:59:02"))
//
//        //contents within pdf document
//        var title = "Generated report"
//        canvas.drawText(title,80f,50f,paint)
//        paint.textSize = 12f
//
//        var text = "LET'S GOOOOOOOO. PDF File successfully created."
//        canvas.drawText(text,5f,70f, paint)
//        pdfReportDocument.finishPage(page)
//
//        val file = File(docsFolder.absolutePath,"Report.pdf")
//        pdfReportDocument.writeTo(FileOutputStream(file))
//        pdfReportDocument.close()
//
//        Toast.makeText(this,"Report PDF successfully downloaded.", Toast.LENGTH_LONG).show()

//   }

//    private fun beginDownload() {
//        val downloadManager = applicationContext.getSystemService(DownloadManager::class.java)
//        val request = DownloadManager.Request("https://picsum.photos/200/300")
//    }
//}
