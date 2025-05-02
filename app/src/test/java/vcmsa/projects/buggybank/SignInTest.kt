package vcmsa.projects.buggybank

import com.google.firebase.auth.FirebaseAuth
import org.junit.Assert.assertEquals
import org.junit.Test

class SignInTest {
    @Test
    fun signInUser_test() {
        val auth = FirebaseAuth.getInstance()
        val email = "test@gmail.com"
        val password = "test123"
        auth.createUserWithEmailAndPassword(email, password)
        auth.signInWithEmailAndPassword(email, password)
        val user = auth.currentUser
        assertEquals(email, user?.email)
    }
    
}
