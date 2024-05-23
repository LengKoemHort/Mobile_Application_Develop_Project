package kh.edu.rupp.dse.mobileapplicationproject.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import kh.edu.rupp.dse.mobileapplicationproject.databinding.ActivityForgetBinding

class ForgetActivity : BaseActivity() {

    private lateinit var binding: ActivityForgetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        btnBackListener()
        resetPasswordListener()
    }

    private fun resetPasswordListener() {
        binding.RBtnReset.setOnClickListener {
            val email = binding.editEmail.text.toString().trim()

            if (email.isEmpty()) {
                Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Password reset email sent", Toast.LENGTH_SHORT).show()
                        navigateToLogin()
                    } else {
                        Toast.makeText(this, "Failed to send password reset email", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish() // Finish the ForgetActivity to prevent going back to it using the back button
    }

    private fun btnBackListener() {
        binding.forgetBtnBack.setOnClickListener {
            finish()
        }
    }
}