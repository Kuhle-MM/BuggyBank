package vcmsa.projects.buggybank

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import vcmsa.projects.buggybank.databinding.ActivitySignUpBinding
import java.security.MessageDigest


class Sign_up : AppCompatActivity() {
    
    private val TAG = "SignUp"
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
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.signupPage)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        
        binding.SignUpLogin.setOnClickListener {
            // Go to sign in page
            val intent = Intent(this@Sign_up, Sign_in::class.java)
            startActivity(intent)
        }
        binding.SignUpButton.setOnClickListener {
            val email = binding.signUpEmail.text.toString()
            val password = binding.SignUpPassword.text.toString()
            val passwordConfirm = binding.SignUpPasswordConfirm.text.toString()
            val username = binding.username.text.toString()
            
            if (email.isNotEmpty() && password.isNotEmpty() && passwordConfirm.isNotEmpty()) {
                
                // Check if the user has filled in all fields
                if (email.isEmpty() || password.isEmpty() || passwordConfirm.isEmpty()) {
                    Log.d(TAG, "Please fill in all fields")
                    Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                
                // Check if the email is valid
                if (!email.contains("@")) {
                    Log.d(TAG, "Email must contain @")
                    Toast.makeText(this, "Email must contain @", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                
                // Check if the password is valid
                if (!password.any { it in "@#\$%^&*!`~-_=+}][{\\|':;<>,./?" }) {
                    Log.d(TAG, "Password must contain at least one special character")
                    Toast.makeText(
                        this,
                        "Password must contain at least one special character",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
                if (!password.any { it.isDigit() }) {
                    Log.d(TAG, "Password must contain at least one number")
                    Toast.makeText(
                        this,
                        "Password must contain at least one number",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
                if (!password.any { it.isUpperCase() }) {
                    Log.d(TAG, "Password must contain at least one uppercase letter")
                    Toast.makeText(
                        this,
                        "Password must contain at least one uppercase letter",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
                if (password != passwordConfirm) {
                    Log.d(TAG, "Passwords do not match")
                    Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                
                // Create user in Firebase Authentication
                lifecycleScope.launch {
                    try {
                        Log.d(TAG, "Creating user with email: $email and password: $password")
                        auth.createUserWithEmailAndPassword(email, password).await()
                        withContext(Dispatchers.Main) {
                            Log.d(TAG, "Sign up successful")
                            Toast.makeText(
                                this@Sign_up,
                                "Sign up successful",
                                Toast.LENGTH_LONG
                            ).show()
                            
                            // Get the current user
                            val user = auth.currentUser
                            
                            val hashedPassword = sha256(password)
                            
                            // Create a reference to the user's node in the database
                            val userReference = databaseReference.child("users").child(user!!.uid)
                            
                            // Add the user's details to the database
                            Log.d(TAG, "Storing user details in database")
                            userReference.child("username").setValue(username)
                            userReference.child("email").setValue(email)
                            
                            
                            
                            userReference.child("password").setValue(hashedPassword)
                            
                            // Check if data was stored by getting the snapshot
                            val snapshot = userReference.child("username").get().await()
                            if (snapshot.exists()) {
                                Log.d(TAG, "Data stored successfully")
                                Toast.makeText(
                                    this@Sign_up,
                                    "Data stored successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                Log.d(TAG, "Data not stored")
                                Toast.makeText(
                                    this@Sign_up,
                                    "Data not stored",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            
                            // Clear the input fields
                            binding.signUpEmail.text.clear()
                            binding.SignUpPassword.text?.clear()
                            binding.SignUpPasswordConfirm.text?.clear()
                            binding.username.text.clear()
                            
                            startActivity(Intent(this@Sign_up, Sign_in::class.java))
                        }
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            // Clear the input fields
                            binding.signUpEmail.text.clear()
                            binding.SignUpPassword.text?.clear()
                            binding.SignUpPasswordConfirm.text?.clear()
                            binding.username.text.clear()
                            Log.d(TAG, "Sign up failed: ${e.message}")
                            Toast.makeText(
                                this@Sign_up,
                                "Sign up failed: ${e.message}",
                                Toast.LENGTH_LONG
                            ).show()
                            
                        }
                    }
                    
                }
            }
        }
    }
    fun sha256(base: String): String {
        try {
            val digest = MessageDigest.getInstance("SHA-256")
            val hash = digest.digest(base.toByteArray(charset("UTF-8")))
            val hexString = StringBuffer()
            
            for (i in hash.indices) {
                val hex = Integer.toHexString(0xff and hash[i].toInt())
                if (hex.length == 1) hexString.append('0')
                hexString.append(hex)
            }
            
            return hexString.toString()
        } catch (ex: java.lang.Exception) {
            throw RuntimeException(ex)
        }
    }
}