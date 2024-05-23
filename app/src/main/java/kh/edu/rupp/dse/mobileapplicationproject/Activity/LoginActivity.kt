package kh.edu.rupp.dse.mobileapplicationproject.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import kh.edu.rupp.dse.mobileapplicationproject.databinding.ActivityLoginBinding

class LoginActivity : BaseActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        btnBackListener()
        linkRegisterListener()
        forgetPasswordListener()
        setVariable()
    }

    private fun forgetPasswordListener() {
        binding.txtForget.setOnClickListener {
            val intent = Intent(this, ForgetActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setVariable() {
        binding.RBtnLogin.setOnClickListener {
            val email = binding.editEmail.text.toString()
            val password = binding.editPassword1.text.toString()

            if (!isValidEmail(email)) {
                showToast("Please enter a valid email address")
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                showToast("Please enter a password")
                return@setOnClickListener
            }

            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = mAuth.currentUser
                        if (user != null) {
                            if (user.isEmailVerified) {
                                val intent = Intent(this, HomeActivity::class.java)
                                startActivity(intent)
                                finish() // Finish current activity after login
                            } else {
                                user.sendEmailVerification()
                                showToast("Please verify your email. A verification email has been sent.")
                            }
                        } else {
                            showToast("User does not exist")
                        }
                    } else {
                        showToast("User does not exist")
                    }
                }
        }
    }

    private fun handleLoginFailure(exception: Exception?) {
        if (exception != null) {
            when (exception) {
                is FirebaseAuthInvalidUserException -> {
                    showToast("User does not exist")
                }
                is FirebaseAuthInvalidCredentialsException -> {
                    showToast("Wrong password")
                }
                else -> {
                    showToast("Failed to login: ${exception.message}")
                }
            }
        } else {
            showToast("Failed to login")
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun linkRegisterListener() {
        binding.txtLinkRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun btnBackListener() {
        binding.loginBtnBack.setOnClickListener {
            finish()
        }
    }
}
