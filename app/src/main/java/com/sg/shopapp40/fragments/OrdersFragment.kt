package com.sg.shopapp40.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.sg.shopapp40.R


class OrdersFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       val view= inflater.inflate(R.layout.fragment_orders, container, false)
        val tv=view.findViewById<TextView>(R.id.textView)
        tv.text="Its Notification Fragment mister ..."

        return view
    }


}