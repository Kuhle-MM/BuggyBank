package vcmsa.projects.buggybank

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import vcmsa.projects.buggybank.databinding.ActivityForgotPasswordBinding

class ForgotPasswordActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityForgotPasswordBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        auth = FirebaseAuth.getInstance()
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.forgotPasswordPage)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.btnSubmit.setOnClickListener {
            val email = binding.txtEmailAddress.text.toString().trim { it <= ' ' }
            if (email.isEmpty()) {
                Toast.makeText(
                    this@ForgotPasswordActivity,
                    "Please enter email address.",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                auth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            this@ForgotPasswordActivity,
                            "Email sent.",
                            Toast.LENGTH_SHORT
                        ).show()
                        
                        finish()
                        val intent = Intent(this,Sign_in::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(
                            this@ForgotPasswordActivity,
                            task.exception!!.message.toString(),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }
}