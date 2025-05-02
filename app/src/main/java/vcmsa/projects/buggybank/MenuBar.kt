package vcmsa.projects.buggybank

import android.content.Intent
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.io.File
import java.io.FileOutputStream

private val FragReport = ReportFragment()
private val FragAnalysis = AnalysisFragment()
private val FragSetABudget = SetBudgetFragment()
private val FragDashboard = MainPageFragment()
private val FragCreateTransaction = CreateTransactionFragment()
private val FragCreateCategory = CreateCategoryFragment()

class MenuBar : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menubar)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        replaceFrag(FragDashboard)

        val bottomBar = findViewById<BottomNavigationView>(R.id.NavBar)
        bottomBar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.ic_home -> replaceFrag(FragDashboard)
                R.id.ic_analysis ->  replaceFrag(FragAnalysis)
                R.id.ic_transactions -> replaceFrag(FragReport)
                R.id.ic_create -> replaceFrag(FragCreateTransaction)
                R.id.ic_trophies -> replaceFrag(FragDashboard)
            }
            true
        }
    }

    private fun replaceFrag(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainerView, fragment)
        transaction.commit()
    }

    fun createPDF(transactions: List<Transaction>) {
        val pdfDocument = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(300, 600, 1).create()
        val page = pdfDocument.startPage(pageInfo)
        val canvas = page.canvas
        val paint = Paint()
        paint.textSize = 12f

        var y = 30f
        canvas.drawText("BuggyBank Expense Report", 10f, y, paint)
        y += 20f
        //loop each transaction within db
        transactions.forEach {
            canvas.drawText("${it.dateOfTransaction}: ${it.description} - R${it.amount}", 10f, y, paint)
            y += 20
        }

        pdfDocument.finishPage(page)

        val docsFolder = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
        if (docsFolder != null && !docsFolder.exists()) {
            docsFolder.mkdirs()
        }

        val file = File(docsFolder, "Report.pdf")

        try {
            pdfDocument.writeTo(FileOutputStream(file))
            pdfDocument.close()

            Toast.makeText(this, "PDF saved to: ${file.absolutePath}", Toast.LENGTH_LONG).show()

            val uri: Uri = FileProvider.getUriForFile(
                this,
                applicationContext.packageName + ".provider",
                file
            )

            val openIntent = Intent(Intent.ACTION_VIEW)
            openIntent.setDataAndType(uri, "application/pdf")
            openIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            startActivity(openIntent)

        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
}
