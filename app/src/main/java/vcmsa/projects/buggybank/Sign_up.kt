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
        binding.SignUpLogin.setOnClickListener {
            val intent = Intent(this@Sign_up, Sign_in::class.java)
            startActivity(intent)
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
            }else if(!password.contains("@") || !password.contains("#") || !password.contains("$") || !password.contains("%") || !password.contains("^") || !password.contains("&") || !password.contains("*"))
            {
                Toast.makeText(this@Sign_up, "Password must at least have one special character", Toast.LENGTH_SHORT).show()
            }
            else if(!password.contains("1") || !password.contains("2") || !password.contains("3") || !password.contains("4") || !password.contains("5") || !password.contains("6") || !password.contains("7") || !password.contains("8") || !password.contains("9") || !password.contains("0"))
            {
                Toast.makeText(this@Sign_up, "Email must contain at least one number", Toast.LENGTH_SHORT).show()
            }
            else if(!password.contains("A") || !password.contains("B") || !password.contains("C") || !password.contains("D") || !password.contains("E") || !password.contains("F") || !password.contains("G") || !password.contains("H") || !password.contains("I") || !password.contains("J") || !password.contains("K") || !password.contains("L") || !password.contains("M") || !password.contains("N") || !password.contains("O") || !password.contains("P") || !password.contains("Q") || !password.contains("R") || !password.contains("S") || !password.contains("T") || !password.contains("U") || !password.contains("V") || !password.contains("W") || !password.contains("X") || !password.contains("Y") || !password.contains("Z"))
            {
                Toast.makeText(this@Sign_up, "Email must contain at least one letter", Toast.LENGTH_SHORT).show()
            }
            else if(!email.contains("@"))
            {
            Toast.makeText(this@Sign_up, "Email must contain @", Toast.LENGTH_SHORT).show()
            }
            else
            {
                Toast.makeText(this@Sign_up, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
            
            }
        }
}
    
    
