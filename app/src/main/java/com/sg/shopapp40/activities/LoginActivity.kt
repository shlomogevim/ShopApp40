package com.sg.shopapp40.activities

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.sg.shopapp40.R
import com.sg.shopapp40.databinding.ActivityLoginBinding
import com.sg.shopapp40.firestore.FirestoreClass
import com.sg.shopapp40.modeles.User
import com.sg.shopapp40.utiles.Constants.EXTRA_USER_DETAILS

class LoginActivity : BaseActivity(), View.OnClickListener {
    lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        DemiData()

        binding.tvRegister.setOnClickListener(this)
        binding.btnLogin.setOnClickListener(this)
        binding.tvForgotPassword.setOnClickListener(this)
    }

    private fun DemiData() {
        binding.etEmail.setText("aa1@gmail.com")
        binding.etPassword.setText("111111")
    }


    private fun validateLoginDetails(): Boolean {
        return when {
            TextUtils.isEmpty(binding.etEmail.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_email), true)
                false
            }
            TextUtils.isEmpty(binding.etPassword.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_password), true)
                false
            }
            else -> {
                true
            }
        }
    }

    private fun logInRegisteredUser() {

        if (validateLoginDetails()) {

            // Show the progress dialog.
            showProgressDialog(resources.getString(R.string.please_wait))

            // Get the text from editText and trim the space
            val email = binding.etEmail.text.toString().trim { it <= ' ' }
            val password = binding.etPassword.text.toString().trim { it <= ' ' }

            // Log-In using FirebaseAuth
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->

                        if (task.isSuccessful) {
                        FirestoreClass().getUserDetails(this)
                    } else {
                        hideProgressDialog()
                        showErrorSnackBar(task.exception!!.message.toString(), true)
                    }
                }
        }
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.btn_login -> {
                    logInRegisteredUser()
                }
                R.id.tv_register -> {
                    startActivity(Intent(this, RegisterActivity::class.java))
                }
                R.id.tv_forgot_password -> {
                    startActivity(Intent(this, ForgetPasswordActivity::class.java))
                }
            }

        }
    }

    fun userLoggedInSuccess(user: User) {
        hideProgressDialog()
        // Print the user details in the log as of now.
        Log.i("gg", user.firstName)
        Log.i("gg", user.lastName)
        Log.i("gg", user.email)

         if (user.profileCompleted == 0) {
             // If the user profile is incomplete then launch the UserProfileActivity.
         val intent = Intent(this@LoginActivity, UserProfileActivity::class.java)
          //  intent.putExtra(EXTRA_USER_DETAILS, user)
             startActivity(intent)
         } else {
             // Redirect the user to Main Screen after log in.
             startActivity(Intent(this@LoginActivity, MainActivity::class.java))
         }


        finish()
    }


}


