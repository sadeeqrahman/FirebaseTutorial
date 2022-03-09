package com.encoders.siginsignupusingfirebase.UI

import android.Manifest
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.app.ActivityCompat
import com.encoders.siginsignupusingfirebase.R
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.*


class UploadImageActivity : AppCompatActivity() {
    private val REQUEST_CODE_ASK_PERMISSIONS = 123
    private val PICK_IMAGE = 100
    private lateinit var gallery_image: ImageView
    private var imageUri: Uri? = null
    var storage: FirebaseStorage? = null
    var storageReference: StorageReference? = null
    private lateinit var upload_image: AppCompatButton

    private lateinit var pick_from_gallery: AppCompatButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_image)

        storage = FirebaseStorage.getInstance()
        storageReference = storage!!.reference

        gallery_image = findViewById(R.id.gallery_image)
        pick_from_gallery = findViewById(R.id.pick_from_gallery)

        upload_image = findViewById(R.id.upload_image)

        pick_from_gallery.setOnClickListener {
            if (isStoragePermissionGranted()) {
                openGallery()
            }
        }

        upload_image.setOnClickListener {

            uploadImage()

        }

    }

    fun isStoragePermissionGranted(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED
            ) {
                // Log.v(TAG, "Permission is granted")
                true
            } else {
                // Log.v(TAG, "Permission is revoked")
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    REQUEST_CODE_ASK_PERMISSIONS
                )
                false
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            //  Log.v(TAG, "Permission is granted")
            true
        }
    }

    private fun openGallery() {
        val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(gallery, PICK_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data!!.data
            gallery_image.setImageURI(imageUri)


        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CODE_ASK_PERMISSIONS -> if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted, do your stuff
                openGallery()
            } else {
                // Permission Denied
                Toast.makeText(this@UploadImageActivity, "Storage Permission Denied", Toast.LENGTH_SHORT)
                    .show()
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }


    private fun uploadImage() {
        if (imageUri != null) {
            val progressDialog = ProgressDialog(this)
            progressDialog.setTitle("Uploading Image...")
            progressDialog.show()
            val ref: StorageReference = storageReference!!.child("images/" + UUID.randomUUID().toString())
            ref.putFile(imageUri!!)
                .addOnSuccessListener {
                    progressDialog.dismiss()
                    Toast.makeText(this@UploadImageActivity, "Uploaded", Toast.LENGTH_SHORT).show()

                    val storageReference = storage!!.reference

                    Log.e("sdfdsafasdfsadf", storageReference.name.toString())



                }
                .addOnFailureListener { e ->
                    progressDialog.dismiss()
                    Toast.makeText(this@UploadImageActivity, "Failed " + e.message, Toast.LENGTH_SHORT).show()
                }
                .addOnProgressListener { taskSnapshot ->
                    val progress = 100.0 * taskSnapshot.bytesTransferred / taskSnapshot
                        .totalByteCount
                    progressDialog.setMessage("Uploaded " + progress.toInt() + "%")
                }
        }
    }

}