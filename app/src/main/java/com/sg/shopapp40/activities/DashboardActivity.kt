package com.sg.shopapp40.activities

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sg.shopapp40.R
import com.sg.shopapp40.databinding.ActivityDashboardBinding
import com.sg.shopapp40.fragments.ProductsFragment
import com.sg.shopapp40.fragments.OrdersFragment
import com.sg.shopapp40.fragments.DashboardFragment

class DashboardActivity : BaseActivity() {

    lateinit var binding:ActivityDashboardBinding
    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.products -> {
                    moveToFragment(ProductsFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.dashboard -> {
                    moveToFragment(DashboardFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.orders -> {
                    moveToFragment(OrdersFragment())
                    return@OnNavigationItemSelectedListener true
                }

            }
            false
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //supportActionBar!!.setBackgroundDrawable(ContextCompat.getDrawable(this,R.drawable.app_gradient_color_background))

        binding.navButton.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        moveToFragment(ProductsFragment())

    }
    private fun moveToFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .addToBackStack("popy")
            .replace(R.id.container, fragment!!)
            //.addToBackStack("tag")
            .commit()
    }

    override fun onBackPressed() {
        doubleBackToExit()

    }
}