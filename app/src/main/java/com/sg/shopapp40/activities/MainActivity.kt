package com.sg.shopapp40.activities

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.sg.shopapp40.R
import com.sg.shopapp40.databinding.ActivityMainBinding
import com.sg.shopapp40.utiles.Constants.LOGGED_IN_USERNAME
import com.sg.shopapp40.utiles.Constants.MYSHOPPAL_PREFERENCES
import com.sg.shopapp40.utiles.GlideLoader
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private var mSelectedImageFileUri: Uri? = null

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getUsername()
    }

    private fun getUsername() {
        val sharedPreferences =
            getSharedPreferences(MYSHOPPAL_PREFERENCES, Context.MODE_PRIVATE)
        val username = sharedPreferences.getString(LOGGED_IN_USERNAME, "")!!
        binding.textView.text= "Wellcome Mr.   $username."
    }
}














    /*    binding.btnSelectImage.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    this, Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                val galleryIntent = Intent(
                    Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                )
                startActivityForResult(galleryIntent, 222)

            } else {
                ActivityCompat.requestPermissions(
                    this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    121
                )
            }
        }


        binding.btnUploadImage.setOnClickListener {
            if (mSelectedImageFileUri != null) {
                // Get the image extension.
                val imageExtension = MimeTypeMap.getSingleton()
                    .getExtensionFromMimeType(contentResolver.getType(mSelectedImageFileUri!!))
                //getting the storage reference
                val sRef: StorageReference = FirebaseStorage.getInstance().reference.child(
                    "Image" + System.currentTimeMillis() + "."
                            + imageExtension
                )

                //adding the file to reference
                sRef.putFile(mSelectedImageFileUri!!)
                    .addOnSuccessListener { taskSnapshot ->
                        // The image upload is success
                        Log.e("Firebase Image URL", taskSnapshot.metadata!!.reference!!.downloadUrl.toString() )
                        // Get the downloadable url from the task snapshot
                        taskSnapshot.metadata!!.reference!!.downloadUrl
                            .addOnSuccessListener { url ->
                                Log.e("Downloadable Image URL", url.toString())
                                binding.tvImageUploadSuccess.text = "Your image uploaded successfully in ==>  url= $url"

                            }
                            .addOnFailureListener { exception ->
                                Toast.makeText(
                                    this,
                                    exception.message,
                                    Toast.LENGTH_LONG
                                ).show()
                                Log.e(javaClass.simpleName, exception.message, exception)
                            }
                    }
            } else {
                Toast.makeText(
                    this,
                    "Please select the image to upload.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
   }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 121) {
            //If permission is granted
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val galleryIntent = Intent(
                    Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                )
                startActivityForResult(galleryIntent, 222)
            } else {
                Toast.makeText(
                    this,
                    "Oops, you just denied the permission for storage. You can also allow it from settings.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 222) {
                if (data != null) {
                    try {
                        // The uri of selected image from phone storage.
                        mSelectedImageFileUri = data.data!!

                      //  binding.imageView.setImageURI(mSelectedImageFileUri)
                    // GlideLoader(this).loadUserPicture(mSelectedImageFileUri!!,binding.imageView)
                        Glide.with(this).load(mSelectedImageFileUri).into(binding.imageView)



                    } catch (e: IOException) {
                        e.printStackTrace()
                        Toast.makeText(
                            this@MainActivity, "Image selection Failed!", Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
             Log.e("Request Cancelled", "Image selection cancelled")
        }
    }*/


