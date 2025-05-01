package vcmsa.projects.buggybank

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import vcmsa.projects.buggybank.databinding.ActivitySignUpBinding


class Sign_up : AppCompatActivity() {
    
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var databaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().reference
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.SignUpLogin.setOnClickListener {
            val intent = Intent(this@Sign_up, Sign_in::class.java)
            startActivity(intent)
        }
        binding.SignUpButton.setOnClickListener {
            val email = binding.signUpEmail.text.toString()
            val password = binding.SignUpPassword.text.toString()
            val passwordConfirm = binding.SignUpPasswordConfirm.text.toString()
            val username = binding.username.text.toString()
            
            if (email.isNotEmpty() && password.isNotEmpty() && passwordConfirm.isNotEmpty() && username.isNotEmpty()) {
                
                if (password == passwordConfirm) {
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            val user = auth.createUserWithEmailAndPassword(email, password).await()
                            val uid = user.user?.uid
                            if (uid != null) {
                                val userMap = mutableMapOf<String, String>()
                                userMap["email"] = email
                                userMap["username"] = username
                                userMap["password"] = password
                                databaseReference.child("users").child(uid).setValue(userMap)
                            }
                            withContext(Dispatchers.Main) {
                                val intent = Intent(this@Sign_up, Sign_in::class.java)
                                startActivity(intent)
                            }
                        } catch (e: Exception) {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(this@Sign_up, "Sign up failed", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                } else {
                    Toast.makeText(this@Sign_up, "Passwords do not match", Toast.LENGTH_SHORT).show()
                }
            } else if (!password.any { it in "@#$%^&*" }) {
                Toast.makeText(this@Sign_up, "Password must contain at least one special character", Toast.LENGTH_SHORT).show()
            } else if (!password.any { it.isDigit() }) {
                Toast.makeText(this@Sign_up, "Password must contain at least one number", Toast.LENGTH_SHORT).show()
            } else if (!password.any { it.isUpperCase() }) {
                Toast.makeText(this@Sign_up, "Password must contain at least one uppercase letter", Toast.LENGTH_SHORT).show()
            } else if (!email.contains("@")) {
                Toast.makeText(this@Sign_up, "Email must contain @", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@Sign_up, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
            
        }
    }
}