package com.sg.shopapp40.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.sg.shopapp40.R
import com.sg.shopapp40.databinding.ActivitySettingBinding
import com.sg.shopapp40.firestore.FirestoreClass
import com.sg.shopapp40.models.User
import com.sg.shopapp40.utiles.Constants.EXTRA_USER_DETAILS
import com.sg.shopapp40.utiles.GlideLoader
import com.sg.shopapp40.utiles.GlideLoader1

class SettingActivity :  BaseActivity(), View.OnClickListener  {

    private lateinit var mUserDetails: User
    lateinit var binding:ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.tvEdit.setOnClickListener(this)
        binding.btnLogout.setOnClickListener(this)
    }
    override fun onResume() {  // when we load app
        super.onResume()

        getUserDetails()
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.tv_edit -> {
                    val intent = Intent(this, UserProfileActivity::class.java)
                    intent.putExtra(EXTRA_USER_DETAILS, mUserDetails)
                    startActivity(intent)
                }
                R.id.btn_logout -> {
                    FirebaseAuth.getInstance().signOut()
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK  // clear all layers in the stuck
                    startActivity(intent)
                    finish()
                }
            }
        }
    }


    /**
     * A function to get the user details from firestore.
     */
    private fun getUserDetails() {
        showProgressDialog(resources.getString(R.string.please_wait))
        // Call the function of Firestore class to get the user details from firestore which is already created.
        FirestoreClass().getUserDetails(this)
    }

    /**
     * A function to receive the user details and populate it in the UI.
     */
    fun userDetailsSuccess(user: User) {

        mUserDetails = user
        hideProgressDialog()

        GlideLoader(this).loadUserPicture(user.image, binding.ivUserPhoto)

       binding.tvName.text = "${user.firstName} ${user.lastName}"
       binding.tvGender.text = user.gender
        binding.tvEmail.text = user.email
        binding.tvMobileNumber.text = "${user.mobile}"
    }
}

/**
 * A function for actionBar Setup.
 */
/*private fun setupActionBar() {

    setSupportActionBar(toolbar_settings_activity)

    val actionBar = supportActionBar
    if (actionBar != null) {
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
    }

    toolbar_settings_activity.setNavigationOnClickListener { onBackPressed() }
}*/