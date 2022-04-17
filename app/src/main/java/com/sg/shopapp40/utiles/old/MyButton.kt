package com.sg.shopapp40.utiles.old



import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton

class MyButton(context: Context, attributeSet: AttributeSet) :
    AppCompatButton(context, attributeSet) {
    init {
        applyButtom()
    }

    private fun applyButtom() {
        val boldTypeface: Typeface =
            Typeface.createFromAsset(context.assets, "a110_abraham_regular.ttf")
        typeface = boldTypeface
    }
}