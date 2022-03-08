package com.encoders.siginsignupusingfirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var email_address: EditText
    private lateinit var password: EditText
    private lateinit var singup: AppCompatButton
    private lateinit var progress_circular: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        auth = Firebase.auth
        email_address = findViewById(R.id.email_address)
        password = findViewById(R.id.password)
        singup = findViewById(R.id.singup)
        progress_circular = findViewById(R.id.progress_circular)

        singup.setOnClickListener {
            FirebaseSignUP()
        }

    }

    fun FirebaseSignUP() {
        progress_circular.visibility = View.VISIBLE
        auth.createUserWithEmailAndPassword(
            email_address.text.toString(),
            password.text.toString()
        )
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    progress_circular.visibility = View.GONE
                    startActivity(Intent(this,WelcomeActivity::class.java))
                    finish()
                } else {

                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "createUserWithEmail:failure", task.exception)
//                    Toast.makeText(baseContext, "Authentication failed.",
//                        Toast.LENGTH_SHORT).show()

                }
            }
    }

}