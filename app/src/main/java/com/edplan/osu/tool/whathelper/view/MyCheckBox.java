package com.edplan.osu.tool.whathelper.view;

import android.content.*;
import android.util.*;
import android.graphics.*;

public class MyCheckBox extends com.gc.materialdesign.views.CheckBox
{
	public int checkColor=Color.parseColor("#268CF1");
	//public int uncheckColor=Color.argb(;
	
	
	public MyCheckBox(Context context, AttributeSet attrs) {
		super(context, attrs);
		setAttributes(attrs);
		if(isCheck()){
			getTextView().setTextColor(checkColor);
		}else{
			getTextView().setTextColor(makeUncheckColor(checkColor));
		}
		//checkColor=getTextView().getTextColors().getDefaultColor();
	}

	@Override
	protected void setAttributes(AttributeSet attrs)
	{
		// TODO: Implement this method
		super.setAttributes(attrs);
	}

	@Override
	public void setChecked(boolean check)
	{
		// TODO: Implement this method
		super.setChecked(check);
		//Log.v("MyView","MyCheckBox Check!");
		if(check){
			getTextView().setTextColor(checkColor);
			//Log.v("MyView","MyCheckBox setTextColor<true|"+isCheck()+">: "+checkColor);
		}else{
			getTextView().setTextColor(makeUncheckColor(checkColor));
			//Log.v("MyView","MyCheckBox setTextColor<false|"+isCheck()+">: "+makeUncheckColor(checkColor));
		}
	}
	
	public int makeUncheckColor(int c){
		/*
		int r = (c >> 16) & 0xFF;
		int g = (c >> 8) & 0xFF;
		int b = (c >> 0) & 0xFF;
		r = (r - 30 < 0) ? 0 : r - 30;
		g = (g - 30 < 0) ? 0 : g - 30;
		b = (b - 30 < 0) ? 0 : b - 30;
		return Color.argb(70, r, g, b);
		*/
		return Color.argb(200,55,55,55);
	}
	
}
