package com.encoders.siginsignupusingfirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class WelcomeActivity : AppCompatActivity() {
    private lateinit var logout: AppCompatButton
    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        logout = findViewById(R.id.logout)
        logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this,MainActivity::class.java))
        }
    }
}