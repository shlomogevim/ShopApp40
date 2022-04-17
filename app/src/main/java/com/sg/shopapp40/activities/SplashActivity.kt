package com.sg.shopapp40.activities

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.core.content.res.ResourcesCompat
import com.sg.shopapp40.databinding.ActivitySplashBinding
import com.sg.shopapp40.utiles.MyFontFamilies

class SplashActivity : AppCompatActivity() {
    lateinit var binding:ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setText()
        pauseIt()

    }

    private fun setText() {
        val font=MyFontFamilies()
        val fontAddress = font.getFamilyFont(103)
        binding.tvAppName.typeface = ResourcesCompat.getFont(this, fontAddress)

        binding.tvAppName.textSize= 22F
        binding.tvAppName.text="זה מה שלימדו אותנו היום בגן ..."
    }
    private fun pauseIt() {
        Handler().postDelayed(
            {  startActivity(Intent(this, MainActivity::class.java))
                finish()
            },2500
        )
    }
}



















/*        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }*/