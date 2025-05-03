package vcmsa.projects.buggybank

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.activity.enableEdgeToEdge
import vcmsa.projects.buggybank.databinding.ActivityMainBinding

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Inflate the layout using View Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        
        Log.d(TAG, "onCreate: View binding and layout set")
        
        // Apply window insets to the root view safely using binding
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            Log.d(TAG, "onCreate: Applied window insets")
            insets
        }
        
        // Button click listeners
        binding.btnSignUp.setOnClickListener {
            Log.d(TAG, "onClick: Sign Up button clicked")
            val intent = Intent(this, Sign_up::class.java)
            startActivity(intent)
        }
        
        binding.btnlogin.setOnClickListener {
            Log.d(TAG, "onClick: Login button clicked")
            val intent = Intent(this, Sign_in::class.java)
            startActivity(intent)
        }
        
        binding.vForgotPassword.setOnClickListener {
            Log.d(TAG, "onClick: Forgot Password button clicked")
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }
    }
}
