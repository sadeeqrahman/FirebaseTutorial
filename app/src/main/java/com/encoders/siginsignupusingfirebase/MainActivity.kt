package com.encoders.siginsignupusingfirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatButton
import com.encoders.siginsignupusingfirebase.UI.WelcomeActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var singup: AppCompatButton
    private lateinit var signin: AppCompatButton
    override fun onStart() {
        super.onStart()
        val user = Firebase.auth.currentUser
        if (user != null) {
            user?.let {
                // Name, email address, and profile photo Url
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            }

        } else {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        singup = findViewById(R.id.singup)
        signin = findViewById(R.id.signin)




        singup.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        signin.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }
    }
}