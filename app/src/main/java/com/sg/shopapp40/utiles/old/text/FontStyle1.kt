package com.sg.shopapp40.utiles.old.text

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class FontStyle1(context: Context, attributeSet: AttributeSet) :
    AppCompatTextView(context, attributeSet) {
    init {
        applyFont()
    }

    private fun applyFont() {
        val boldTypeface: Typeface =
            Typeface.createFromAsset(context.assets, "a100_gveretlevinalefalefalef_regular.ttf")
        typeface = boldTypeface
    }
}