package vcmsa.projects.buggybank

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import vcmsa.projects.buggybank.databinding.ActivitySignInBinding

private const val TAG = "Sign_in"

class Sign_in : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivitySignInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        Log.d(TAG, "onCreate: ")

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.SignInRegister.setOnClickListener {
            Log.d(TAG, "onClick: Register button clicked")
            val intent = Intent(this@Sign_in, Sign_up::class.java)
            startActivity(intent)
        }
        binding.SignInButton.setOnClickListener {
            Log.d(TAG, "onClick: Sign In button clicked")

            val email = binding.SignInEmail.text.toString().trim { it <= ' ' }
            val password = binding.SignInPassword.text.toString().trim { it <= ' ' }

            if (email.isEmpty() || password.isEmpty()) {
                Log.d(TAG, "onClick: Email or password is empty")
                Toast.makeText(
                    this@Sign_in,
                    "Please enter email and password",
                    Toast.LENGTH_SHORT
                )
                    .show()

            } else {
                Log.d(TAG, "onClick: Valid credentials entered")

                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        Log.d(TAG, "signInWithEmailAndPassword: Started")
                        auth.signInWithEmailAndPassword(email, password).await()
                        Log.d(TAG, "signInWithEmailAndPassword: Completed")

                        val dbRef = FirebaseDatabase.getInstance().getReference("users")
                            .child(auth.currentUser!!.uid)
                        dbRef.child("signedIn").setValue(true)
                        Log.d(
                            TAG,
                            "signInWithEmailAndPassword: Signed in status changed to true"
                        )

                        withContext(Dispatchers.Main) {
                            Log.d(TAG, "signInWithEmailAndPassword: Navigating to MenuBar")
                            val intent = Intent(this@Sign_in, MenuBar::class.java)
                            startActivity(intent)
                            finish()
                        }
                    } catch (e: Exception) {
                        Log.d(TAG, "signInWithEmailAndPassword: Error", e)
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                this@Sign_in,
                                "User does not exist",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                }
            }


            binding.vForgotPassword.setOnClickListener {
                Log.d(TAG, "onClick: Forgot Password button clicked")
                val intent = Intent(this, ForgotPasswordActivity::class.java)
                startActivity(intent)
            }

        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: ")

        val currentUser = auth.currentUser
        if (currentUser != null) {
            val dbRef = FirebaseDatabase.getInstance().getReference("users")
                .child(currentUser.uid)
                .child("signedIn")
            Log.d(TAG, "onStart: Checking if user is logged in")
            dbRef.get().addOnCompleteListener { task ->
                if (task.isSuccessful && task.result.exists() && task.result.value as Boolean) {
                    Log.d(TAG, "onStart: User is logged in")
                    val intent = Intent(this@Sign_in, MenuBar::class.java)
                    intent.putExtra("user", currentUser)
                    startActivity(intent)
                    finish()
                }
            }
        }


    }
}