package vcmsa.projects.buggybank

import android.content.Intent
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.FileProvider
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.google.android.material.navigation.NavigationView
import java.io.File
import java.io.FileOutputStream

private val FragReport = ReportFragment()
private val FragAnalysis = AnalysisFragment()
private val FragDashboard = MainPageFragment()
private val FragCreatePopUp = CreatPopUpFragment()
private val FragTransactionRecords = TransactionRecords()
private val FragSetABudget = SetBudgetFragment()
//private val FragSettings = SettingsFragment()
//private val FragLogout


class MenuBar : AppCompatActivity() {
    
    lateinit var navToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menubar)
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.menu)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)
        val sideNavView: NavigationView = findViewById(R.id.sideMenubar)


     //   replaceFrag(CreateTransactionFragment())

    //    val drawerLayout : DrawerLayout = findViewById(R.id.drawerLayout)
     //   val sideNavView : NavigationView = findViewById(R.id.sideMenubar)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.bringToFront()

        replaceFrag(FragDashboard)
        
        sideNavView.bringToFront()
        drawerLayout.requestLayout()
        
        //Bottom menu bar nav code
        val bottomBar = findViewById<BottomNavigationView>(R.id.NavBar)
        
        
        bottomBar.setOnItemSelectedListener {
            when (it.itemId) {
                
                R.id.ic_home -> replaceFrag(FragDashboard)
                R.id.ic_analysis -> replaceFrag(FragAnalysis)
                R.id.ic_create -> {
                    val showPopUp = FragCreatePopUp
                    showPopUp.show(supportFragmentManager, "showPopUp")
                }

                    R.id.ic_transactions -> replaceFrag(FragTransactionRecords)
                    R.id.ic_trophies -> replaceFrag(FragDashboard)
                }

                true
            }

            //Side nav menu bar code
            navToggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
            drawerLayout.addDrawerListener(navToggle)
            navToggle.syncState()

            supportActionBar?.setDisplayHomeAsUpEnabled(true)

            sideNavView.setNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.is_setABudget -> replaceFrag(FragSetABudget)
                    R.id.is_reports -> replaceFrag(FragReport)
                    R.id.is_calculator -> Toast.makeText(this,"Calculator coming soon",Toast.LENGTH_LONG).show()
                    R.id.is_currencyConvertor -> Toast.makeText(this, "Currency convertor coming soon",Toast.LENGTH_LONG).show()
                    R.id.is_switchAccount -> Toast.makeText(this, "Switch account coming soon", Toast.LENGTH_LONG).show()
                    R.id.is_budgetBuddy -> Toast.makeText(this, "Budget buddy coming soon", Toast.LENGTH_LONG).show()
                }
                drawerLayout.closeDrawer(GravityCompat.START)
                true

            }


    }
        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            return if (navToggle.onOptionsItemSelected(item)) {
                true
            } else super.onOptionsItemSelected(item)
        }


        private fun replaceFrag(fragment: Fragment) {
            val transaction = supportFragmentManager.beginTransaction()
            lifecycleScope.launch {
                transaction.replace(R.id.fragmentContainerView, fragment)
                transaction.commit()
            }
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
                canvas.drawText(
                    "${it.dateOfTransaction}: ${it.description} - R${it.amount}",
                    10f,
                    y,
                    paint
                )
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

