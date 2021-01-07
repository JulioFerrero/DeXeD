package com.example.dexed;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class NastyFontTextView extends androidx.appcompat.widget.AppCompatTextView {

    public NastyFontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context, attrs);

    }
    private void applyCustomFont(Context context, AttributeSet attrs) {
        TypedArray attributeArray = context.obtainStyledAttributes(
                attrs,
                R.styleable.MyTextView);

        String fontName = attributeArray.getString(R.styleable.MyTextView_fontFile);
        Typeface customFont = selectTypeface(context, fontName);
        setTypeface(customFont);

        attributeArray.recycle();
    }

    private Typeface selectTypeface(Context context, String fontName) {
        try {
            return FontCache.getTypeface(fontName, context);
        }
        catch (Exception e)
        {
            //Font specified in XML not found in the resources
            return null;
        }
    }
}

