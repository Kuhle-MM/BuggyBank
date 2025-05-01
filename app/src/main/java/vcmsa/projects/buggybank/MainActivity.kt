package vcmsa.projects.buggybank

import android.content.Intent

import android.graphics.Canvas

import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import vcmsa.projects.buggybank.databinding.ActivityMainBinding
import com.google.firebase.FirebaseApp

class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        FirebaseApp.initializeApp(this)
        setContentView(R.layout.activity_main)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, CreateCategoryFragment())
            .commit()
        val intent = Intent(this,menubar::class.java)

        startActivity(intent)
        
        binding.btnSignUp.setOnClickListener {
            val intent = Intent(this,Sign_up::class.java)
            startActivity(intent)
        }
        binding.btnlogin.setOnClickListener {
            val intent = Intent(this,Sign_in::class.java)
            startActivity(intent)
        }
        binding.vForgotPassword.setOnClickListener {
            val intent = Intent(this,ForgotPasswordActivity::class.java)
            startActivity(intent)
        }

    }
}

