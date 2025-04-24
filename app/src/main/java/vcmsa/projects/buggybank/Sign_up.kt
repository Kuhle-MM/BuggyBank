package vcmsa.projects.buggybank

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import vcmsa.projects.buggybank.databinding.ActivitySignUpBinding

class Sign_up : AppCompatActivity() {
    
    private lateinit var auth: FirebaseAuth;
    private lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.SignUpButton.setOnClickListener {
            val email = binding.SignUpEmail.text.toString()
            val password = binding.SignUpPassword.text.toString()
            val passwordConfirm = binding.SignUpPasswordConfirm.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty() && passwordConfirm.isNotEmpty()) {
                if (password == passwordConfirm) {
                    
                    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener{
                        if (it.isSuccessful) {
                            val intent = Intent(this@Sign_up, Sign_in::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this@Sign_up, "Sign up failed", Toast.LENGTH_SHORT).show()
                        }
                        
                        }
                    }else {
                        Toast.makeText(this@Sign_up, "Passwords do not match", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@Sign_up, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    
    
