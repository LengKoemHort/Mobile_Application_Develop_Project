package kh.edu.rupp.dse.mobileapplicationproject.Activity

import android.content.Intent
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import kh.edu.rupp.dse.mobileapplicationproject.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding

    companion object {
        lateinit var auth: FirebaseAuth
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()

        // Check if the user is already signed in
        if (auth.currentUser != null) {
            // User is signed in, redirect to another activity (e.g., HomeActivity)
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        btnLoginListener()
        btnRegisterListener()
    }

    private fun btnRegisterListener() {
        binding.mainBtnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun btnLoginListener() {
        binding.mainBtnLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

}