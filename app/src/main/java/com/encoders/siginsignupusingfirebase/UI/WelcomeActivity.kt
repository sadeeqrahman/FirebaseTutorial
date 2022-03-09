package com.encoders.siginsignupusingfirebase.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatButton
import com.encoders.siginsignupusingfirebase.MainActivity
import com.encoders.siginsignupusingfirebase.R
import com.google.firebase.auth.FirebaseAuth

class WelcomeActivity : AppCompatActivity() {
    private lateinit var logout: AppCompatButton
    private lateinit var fetch_send_data: AppCompatButton
    private lateinit var upload_image: AppCompatButton

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)


        logout = findViewById(R.id.logout)
        fetch_send_data = findViewById(R.id.fetch_send_data)
        upload_image = findViewById(R.id.upload_image)

        logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, MainActivity::class.java))
        }

        fetch_send_data.setOnClickListener {

            startActivity(Intent(this, SendRetrieveDataActivity::class.java))
        }
        upload_image.setOnClickListener {

            startActivity(Intent(this, UploadImageActivity::class.java))
        }


    }


}
