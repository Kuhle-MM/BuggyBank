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
import com.google.firebase.ktx.Firebase
import vcmsa.projects.buggybank.databinding.ActivitySignInBinding

class Sign_in : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth;
    private lateinit var binding: ActivitySignInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
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
                if (email.isNotEmpty() && password.isNotEmpty()) {
                    if (password.length < 6 && !password.contains("@") || !password.contains("#") || !password.contains("$") || !password.contains("%") || !password.contains("^") || !password.contains("&") || !password.contains("*")) {
                        Toast.makeText(this@Sign_in, "Password is too weak", Toast.LENGTH_SHORT).show()
                    }else if(!password.contains("@") || !password.contains("#") || !password.contains("$") || !password.contains("%") || !password.contains("^") || !password.contains("&") || !password.contains("*")) {
                        Toast.makeText(this@Sign_in, "Password must at least have one special character", Toast.LENGTH_SHORT).show()
                    }
                    else if(!password.contains("1") || !password.contains("2") || !password.contains("3") || !password.contains("4") || !password.contains("5") || !password.contains("6") || !password.contains("7") || !password.contains("8") || !password.contains("9") || !password.contains("0")) {
                        Toast.makeText(this@Sign_in, "Email must contain at least one number", Toast.LENGTH_SHORT).show()
                    }
                    else if(!password.contains("A") || !password.contains("B") || !password.contains("C") || !password.contains("D") || !password.contains("E") || !password.contains("F") || !password.contains("G") || !password.contains("H") || !password.contains("I") || !password.contains("J") || !password.contains("K") || !password.contains("L") || !password.contains("M") || !password.contains("N") || !password.contains("O") || !password.contains("P") || !password.contains("Q") || !password.contains("R") || !password.contains("S") || !password.contains("T") || !password.contains("U") || !password.contains("V") || !password.contains("W") || !password.contains("X") || !password.contains("Y") || !password.contains("Z")) {
                        Toast.makeText(this@Sign_in, "Email must contain at least one letter", Toast.LENGTH_SHORT).show()                    }
                    }else if(!email.contains("@")) {
                        Toast.makeText(this@Sign_in, "Email must contain @", Toast.LENGTH_SHORT).show()
                    } else {
                    auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                        if (it.isSuccessful) {
                            val intent = Intent(this@Sign_in, MainActivity::class.java)
                            startActivity(intent)
                        } else {
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
}