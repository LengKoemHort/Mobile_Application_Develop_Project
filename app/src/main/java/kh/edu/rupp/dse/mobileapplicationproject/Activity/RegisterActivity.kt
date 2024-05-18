package kh.edu.rupp.dse.mobileapplicationproject.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import kh.edu.rupp.dse.mobileapplicationproject.R
import com.google.firebase.auth.GoogleAuthProvider
import kh.edu.rupp.dse.mobileapplicationproject.databinding.ActivityRegisterBinding


class RegisterActivity : BaseActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.client_id))
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(this, gso)

        btnBackListener()
        linkLoginListener()
        setVariables()

        binding.googlebtn.setOnClickListener{
            googleSignInClient.signOut()
            startActivityForResult(googleSignInClient.signInIntent, 13)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 13 && resultCode == RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val account = task.getResult(ApiException::class.java)!!
            firebaseAuthWithGoogle(account.idToken!!)
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            }.addOnFailureListener {
                Toast.makeText(this, "Failed to sign in with Google", Toast.LENGTH_SHORT).show()
            }
    }

    private fun setVariables() {
        binding.RBtnSignUp.setOnClickListener {
            val fullname: String = binding.editFullname.text.toString()
            val email = binding.editEmail.text.toString()
            val password = binding.editPassword1.text.toString()
            if (!isValidEmail(email)) {
                Toast.makeText(this, "Email is not valid", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (password.length < 6) {
                Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT)
                    .show()
            }
            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = mAuth.currentUser
                        user!!.sendEmailVerification()
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Toast.makeText(
                                        this,
                                        "Verification email sent to ${user.email}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        Toast.makeText(this, "User created successfully, Verification email sent to ${user.email}. Please verify your email.", Toast.LENGTH_SHORT).show()
                        // Navigate to login page
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                    } else {
                        // Check if the exception indicates user already exists
                        if (task.exception is FirebaseAuthUserCollisionException) {
                            Toast.makeText(this, "User already exists", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this, "Failed to create user", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
        }
    }

    private fun linkLoginListener() {
        binding.txtLinkLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun btnBackListener() {
        binding.registerBtnBack.setOnClickListener {
            finish()
        }
    }
}