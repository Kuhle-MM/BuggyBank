package vcmsa.projects.buggybank

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import vcmsa.projects.buggybank.databinding.ActivitySignInBinding

class Sign_in : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivitySignInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.createTransactionContainer)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.SignInRegister.setOnClickListener {
            val intent = Intent(this@Sign_in, Sign_up::class.java)
            startActivity(intent)
        }
        binding.SignInButton.setOnClickListener {
            val email = binding.SignInEmail.text.toString().trim{ it <= ' '}
            val password = binding.SignInPassword.text.toString().trim{ it <= ' '}
            
                     if(email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this@Sign_in, "Please enter email and password", Toast.LENGTH_SHORT).show()
                
            }   else {
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        auth.signInWithEmailAndPassword(email, password).await()
                        withContext(Dispatchers.Main) {
                            val intent = Intent(this@Sign_in, MenuBar::class.java)
                            startActivity(intent)
                            finish()
                        }
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@Sign_in, "User does not exist", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }
        }
        binding.vForgotPassword.setOnClickListener {
            val intent = Intent(this,ForgotPasswordActivity::class.java)
            startActivity(intent)
        }
    }

    //Add more requirement for this since Logging in should be linked with a device
    //so if(loggedIn on Samsung A15) then {send them to Menubar}
    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val intent = Intent(this@Sign_in, MenuBar::class.java)
            intent.putExtra("user", currentUser)
            startActivity(intent)
            finish()
        }
    }

}