package com.encoders.siginsignupusingfirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignInActivity : AppCompatActivity() {  private lateinit var auth: FirebaseAuth
    private lateinit var email_address: EditText
    private lateinit var password: EditText
    private lateinit var singin: AppCompatButton
    private lateinit var progress_circular: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        auth = Firebase.auth
        email_address = findViewById(R.id.email_address)
        password = findViewById(R.id.password)
        singin = findViewById(R.id.singin)
        progress_circular = findViewById(R.id.progress_circular)

        singin.setOnClickListener {
            FirebaseSignIn()
        }

    }

    fun FirebaseSignIn() {
        progress_circular.visibility = View.VISIBLE
        auth.signInWithEmailAndPassword(
            email_address.text.toString(),
            password.text.toString()

        )
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    progress_circular.visibility = View.GONE
                    val user = auth.currentUser
                    startActivity(Intent(this,WelcomeActivity::class.java))
                    finish()

                }else{
                    progress_circular.visibility = View.GONE
                    Toast.makeText(this,"Email Or Password is incorrect",Toast.LENGTH_SHORT).show()
                }
            }
    }

}