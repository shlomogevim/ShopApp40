package com.sg.shopapp40.activities

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.sg.shopapp40.R
import com.sg.shopapp40.databinding.ActivityUserProfileBinding
import com.sg.shopapp40.firestore.FirestoreClass
import com.sg.shopapp40.models.User
import com.sg.shopapp40.utiles.Constants
import com.sg.shopapp40.utiles.Constants.COMPLETE_PROFILE
import com.sg.shopapp40.utiles.Constants.EXTRA_USER_DETAILS
import com.sg.shopapp40.utiles.Constants.FEMALE
import com.sg.shopapp40.utiles.Constants.FIRST_NAME
import com.sg.shopapp40.utiles.Constants.GENDER
import com.sg.shopapp40.utiles.Constants.IMAGE
import com.sg.shopapp40.utiles.Constants.MALE
import com.sg.shopapp40.utiles.Constants.MOBILE
import com.sg.shopapp40.utiles.Constants.READ_STORAGE_PERMISSION_CODE
import com.sg.shopapp40.utiles.GlideLoader
import java.io.IOException


class UserProfileActivity : BaseActivity() {

    lateinit var binding: ActivityUserProfileBinding
    lateinit var mUserDetail: User
    private var mSelectedImageFileUri: Uri? = null
    private var mUserProfileImageURL: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mUserDetail = User()
        getExsistData()
        operateAllButtons()
    }

    private fun getExsistData() {

        if (intent.hasExtra(EXTRA_USER_DETAILS)) {
            mUserDetail = intent.getParcelableExtra(EXTRA_USER_DETAILS)!!
        }

        binding.etFirstName.setText(mUserDetail.firstName)
        binding.etLastName.setText(mUserDetail.lastName)

        binding.etEmail.isEnabled = false
        binding.etEmail.setText(mUserDetail.email)

        if (mUserDetail.profileCompleted == 0) {
            binding.tvTitle.text = resources.getString(R.string.title_complete_profile)
            binding.etFirstName.isEnabled = false
            binding.etLastName.isEnabled = false
        }else{
            setupActionBar()
            binding.tvTitle.text = resources.getString(R.string.title_edit_profile)

            GlideLoader(this@UserProfileActivity).loadUserPicture(mUserDetail.image,binding.ivUserPhoto)

            // Set the existing values to the UI and allow user to edit except the Email ID.

            if (mUserDetail.mobile != 0L) {
                binding.etMobileNumber.setText(mUserDetail.mobile.toString())
            }
            if (mUserDetail.gender == MALE) {
               binding.rbMale.isChecked = true
            } else {
                binding.rbFemale.isChecked = true
            }
        }
    }
    private fun setupActionBar() {

    /*    setSupportActionBar(toolbar_user_profile_activity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
        }

        toolbar_user_profile_activity.setNavigationOnClickListener { onBackPressed() }*/
    }

    private fun operateAllButtons() {
        binding.ivUserPhoto.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    this, Manifest.permission.READ_EXTERNAL_STORAGE
                )
                == PackageManager.PERMISSION_GRANTED
            ) {
                // showErrorSnackBar("You have already storage permission",false)
                Constants.showImageChooser(this@UserProfileActivity)
            } else {
                /*Requests permissions to be granted to this application. These permissions
                 must be requested in your manifest, they should not be granted to your app,
                 and they should have protection level*/
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    READ_STORAGE_PERMISSION_CODE
                )
            }
        }
        binding.btnSubmit.setOnClickListener {
            if (validateUserProfileDetails()) {
                // submitBtnInAction()
                showProgressDialog(resources.getString(R.string.please_wait))
                if (mSelectedImageFileUri != null) {
                    FirestoreClass().uploadImageToCloudStorage(this, mSelectedImageFileUri)
                } else {
                    updateUserProfileDetails()
                }
            }
        }
    }

    private fun updateUserProfileDetails() {
        val userHashMap = HashMap<String, Any>()

        val firstName = binding.etFirstName.text.toString().trim { it <= ' ' }
        if (firstName != mUserDetail.firstName) {
            userHashMap[FIRST_NAME] = firstName
        }
        val lastName = binding.etLastName.text.toString().trim { it <= ' ' }
        if (lastName != mUserDetail.lastName) {
            userHashMap[Constants.LAST_NAME] = lastName
        }

        val mobileNumber = binding.etMobileNumber.text.toString().trim { it <= ' ' }
        if (mobileNumber.isNotEmpty() && mobileNumber != mUserDetail.mobile.toString()) {
            userHashMap[MOBILE] = mobileNumber.toLong()
        }

        val gender = if (binding.rbMale.isChecked) {
            MALE
        } else {
            FEMALE
        }
        if (gender.isNotEmpty() && gender != mUserDetail.gender) {
            userHashMap[GENDER] = gender
        }

        if (mUserProfileImageURL.isNotEmpty()) {
            userHashMap[IMAGE] = mUserProfileImageURL
        }
        if (mUserProfileImageURL.isNotEmpty()) {
            userHashMap[IMAGE] = mUserProfileImageURL
        }
        // Here if user is about to complete the profile then update the field or else no need.
        // 0: User profile is incomplete.
        // 1: User profile is completed.

        userHashMap[COMPLETE_PROFILE] = 1


        // call the reg isterUser function of FireStore class to make an entry in the database.
        FirestoreClass().updateUserProfileData(this@UserProfileActivity, userHashMap)
    }

    /**
     * A function to notify the success result and proceed further accordingly after updating the user details.
     */
    fun userProfileUpdateSuccess() {
        hideProgressDialog()
        Toast.makeText(
            this@UserProfileActivity,
            resources.getString(R.string.msg_profile_update_success),
            Toast.LENGTH_SHORT
        ).show()

        startActivity(Intent(this@UserProfileActivity, DashboardActivity::class.java))
        finish()
    }

    fun imageUploadSuccess(imageURL: String) {
        hideProgressDialog()
        //Toast.makeText(this,"Your image is uploaded successful, imageUrl=$imageURL",Toast.LENGTH_LONG).show()
        mUserProfileImageURL = imageURL
        updateUserProfileDetails()
    }

    private fun validateUserProfileDetails(): Boolean {
        return when {
            // We have kept the user profile picture is optional.
            // The FirstName, LastName, and Email Id are not editable when they come from the login screen.
            // The Radio button for Gender always has the default selected value.
            // Check if the mobile number is not empty as it is mandatory to enter.
            TextUtils.isEmpty(binding.etMobileNumber.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_mobile_number), true)
                false
            }
            else -> {
                true
            }
        }
    }


    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == Constants.PICK_IMAGE_REQUEST_CODE) {
                if (data != null) {
                    try {
                        // The uri of selected image from phone storage.
                        mSelectedImageFileUri = data.data!!
                        // binding.ivUserPhoto.setImageURI(selectedImageFileUri)
                        GlideLoader(this@UserProfileActivity).loadUserPicture(
                            mSelectedImageFileUri!!, binding.ivUserPhoto
                        )
                    } catch (e: IOException) {
                        e.printStackTrace()
                        Toast.makeText(
                            this@UserProfileActivity,
                            resources.getString(R.string.image_selection_failed),
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            // A log is printed when user close or cancel the image selection.
            Log.e("Request Cancelled", "Image selection cancelled")
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == READ_STORAGE_PERMISSION_CODE) {
            //If permission is granted
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Constants.showImageChooser(this@UserProfileActivity)
                // showErrorSnackBar("The storage permission is grated",false)
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(
                    this,
                    resources.getString(R.string.read_storage_permission_denied),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

}


/*   private fun submitBtnInAction() {
       showProgressDialog(resources.getString(R.string.please_wait))
       if (mSelectedImageFileUri != null) {
           FirestoreClass().uploadImageToCloudStorage(this, mSelectedImageFileUri)
       }
        val userHashMap = HashMap<String, Any>()

        val mobileNumber = binding.etMobileNumber.text.toString().trim { it <= ' ' }
        if (mobileNumber.isNotEmpty()) {
            userHashMap[MOBILE] = mobileNumber.toLong()
        }

        val gender = if (binding.rbMale.isChecked) {
            MALE
        } else {
            FEMALE
        }
        userHashMap[GENDER] = gender

        // call the registerUser function of FireStore class to make an entry in the database.
        FirestoreClass().updateUserProfileData(this@UserProfileActivity, userHashMap)

   }*/
/*  override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {

                R.id.iv_user_photo -> {

                    if (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        )
                        == PackageManager.PERMISSION_GRANTED
                    ) {
                        Constants.showImageChooser(this@UserProfileActivity)
                    } else {
                        /*Requests permissions to be granted to this application. These permissions
                         must be requested in your manifest, they should not be granted to your app,
                         and they should have protection level*/
                        ActivityCompat.requestPermissions(
                            this,
                            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                            Constants.READ_STORAGE_PERMISSION_CODE
                        )
                    }
                }

                R.id.btn_save -> {

                    if (validateUserProfileDetails()) {

                        // TODO Step 4: Create a HashMap of user details to be updated in the database and add the values init.
                        // START
                        val userHashMap = HashMap<String, Any>()

                        // Here the field which are not editable needs no update. So, we will update user Mobile Number and Gender for now.

                        // Here we get the text from editText and trim the space
                        val mobileNumber = et_mobile_number.text.toString().trim { it <= ' ' }

                        val gender = if (rb_male.isChecked) {
                            Constants.MALE
                        } else {
                            Constants.FEMALE
                        }

                        if (mobileNumber.isNotEmpty()) {
                            userHashMap[Constants.MOBILE] = mobileNumber.toLong()
                        }

                        userHashMap[Constants.GENDER] = gender
                        // END


                        // TODO Step 6: Remove the message and call the function to update user details.
                        // START
                        /*showErrorSnackBar("Your details are valid. You can update them.", false)*/

                        // Show the progress dialog.
                        showProgressDialog(resources.getString(R.string.please_wait))

                        // call the registerUser function of FireStore class to make an entry in the database.
                        FirestoreClass().updateUserProfileData(
                            this@UserProfileActivity,
                            userHashMap
                        )
                        // END
                    }
                }
            }
        }
    }*/