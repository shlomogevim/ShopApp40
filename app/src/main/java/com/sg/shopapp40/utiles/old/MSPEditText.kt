package com.sg.shopapp40.utiles.old


import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView

/**
 * This class will be used for Custom font text using the EditText which inherits the AppCompatEditText class.
 */
class MSPEditText(context: Context, attrs: AttributeSet) : AppCompatEditText(context, attrs) {
    /**
     * The init block runs every time the class is instantiated.
     */
    init {
        // Call the function to apply the font to the components.
        applyFont()
    }

    /**
     * Applies a font to a EditText.
     */
    private fun applyFont() {
        // This is used to get the file from the assets folder and set it to the title textView.
        val typeface: Typeface =
         //   Typeface.createFromAsset(context.assets, "Montserrat-Regular.ttf")
            Typeface.createFromAsset(context.assets, "a101_ankaclm_bold_webfont.ttf")
        setTypeface(typeface)
    }
}