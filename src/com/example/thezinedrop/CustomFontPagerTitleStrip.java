package com.example.thezinedrop;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.view.PagerTitleStrip;
import android.util.AttributeSet;
import android.widget.TextView;

public class CustomFontPagerTitleStrip extends PagerTitleStrip {

	public CustomFontPagerTitleStrip(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public CustomFontPagerTitleStrip(Context arg0, AttributeSet arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}
	
	   protected void onLayout(boolean changed, int l, int t, int r, int b) {
	        super.onLayout(changed, l, t, r, b);
	        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/OpenSans-CondBold.ttf");
	        for (int i=0; i<this.getChildCount(); i++) {
	            if (this.getChildAt(i) instanceof TextView) {
	                ((TextView)this.getChildAt(i)).setTypeface(tf);
	            }
	        }
	    }

}
