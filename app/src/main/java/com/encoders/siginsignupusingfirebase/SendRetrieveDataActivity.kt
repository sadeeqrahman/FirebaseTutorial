package com.encoders.siginsignupusingfirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.encoders.siginsignupusingfirebase.Adapter.UsersListAdapter
import com.encoders.siginsignupusingfirebase.Adapter.onUserlickListner
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*

class SendRetrieveDataActivity :AppCompatActivity() , onUserlickListner {
    private lateinit var ref: DatabaseReference
    private lateinit var save_user: AppCompatButton
    private lateinit var username: EditText
    private lateinit var designation: EditText
    private lateinit var progress_circular: ProgressBar
    private lateinit var userslist: RecyclerView
    private lateinit var userlist: MutableList<User>
    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_retrieve_data)
        username = findViewById(R.id.username)
        designation = findViewById(R.id.designation)
        progress_circular = findViewById(R.id.progress_circular)
        userslist = findViewById(R.id.userslist)

        save_user = findViewById(R.id.save_user)


        save_user.setOnClickListener {
            Save_User()
        }


        getdata()
    }


    fun Save_User() {
        ref = FirebaseDatabase.getInstance().getReference("Users")
        if ( username.text.toString().isEmpty() || designation.text.toString().isEmpty()) {
            Toast.makeText(
                baseContext, "Enter Username and Designation First",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            progress_circular.visibility = View.VISIBLE
            val sdf = SimpleDateFormat("dd/MM/yyyy hh:mm:ss")
            val currentDate = sdf.format(Date())
            val heroId = ref.push().key
            val hero =
                User(currentDate.toString(), username.text.toString(), designation.text.toString())
            ref.child(heroId!!).setValue(hero).addOnCompleteListener {
                username.setText("")
                designation.setText("")
                progress_circular.visibility = View.GONE
            }
        }
    }


    private fun getdata() {
        userlist = mutableListOf()
        val nm = FirebaseDatabase.getInstance().getReference("Users")
        nm.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    userlist = mutableListOf()


                    for (npsnapshot in dataSnapshot.children) {
                        val l = npsnapshot.getValue(User::class.java)
                        userlist.add(l!!)
                    }

                    UserList()

                }

            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })


    }

    fun UserList() {
        userslist.layoutManager =
            LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false
            )

        val usersListAdapter =
            UsersListAdapter(
                userlist, this@SendRetrieveDataActivity,
            )
        userslist.adapter = usersListAdapter


    }

    override fun onUserlickListner(user: User, position: Int) {

    }

}
