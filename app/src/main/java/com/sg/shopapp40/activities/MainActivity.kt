package com.sg.shopapp40.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sg.shopapp40.R
import com.sg.shopapp40.databinding.ActivityMainBinding
import com.sg.shopapp40.utiles.Constants.LOGGED_IN_USERNAME
import com.sg.shopapp40.utiles.Constants.MYSHOPPAL_PREFERENCES

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getUsername()



        //startActivity(Intent(this,LoginActivity::class.java))
    }

    private fun getUsername() {
        val sharedPreferences =
        getSharedPreferences(MYSHOPPAL_PREFERENCES, Context.MODE_PRIVATE)
        val username = sharedPreferences.getString(LOGGED_IN_USERNAME, "")!!
        binding.tvMain.text= "The logged in user is $username."
    }
}