package com.sg.shopapp40.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sg.shopapp40.R
import com.sg.shopapp40.databinding.ActivityUserProfileBinding

class UserProfileActivity : AppCompatActivity() {
    lateinit var binding: ActivityUserProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}