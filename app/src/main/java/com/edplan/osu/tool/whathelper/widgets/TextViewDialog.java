package com.edplan.osu.tool.whathelper.widgets;
import android.content.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import com.edplan.osu.tool.whathelper.*;

public class TextViewDialog extends MyDialog
{
	public Context context;

	public View rootView;
	public View mainView;
	public String title;
	public String text;
	public boolean clickDismiss;

	public TextView titleView;
	public TextView textView;
	
	public TextViewDialog(Context c,String title,String text,boolean clickDismiss){
		super(c,R.style.DialogTheme);
		//android.R.style.Theme_Translucent
		context=c;
		this.text=text;
		this.title=title;
		this.clickDismiss=clickDismiss;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_textview);
		rootView=findViewById(R.id.dialogRootView);
		mainView=findViewById(R.id.dialogMainView);
		textView=(TextView)findViewById(R.id.dialogTextView);
		textView.setText(text);
		((TextView)findViewById(R.id.dialogTitle)).setText(title);
		rootView.setOnTouchListener(new OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if (event.getX() < mainView.getLeft() 
						|| event.getX() >mainView.getRight()
						|| event.getY() > mainView.getBottom() 
						|| event.getY() < mainView.getTop()) {
						if(clickDismiss)dismiss();
					}
					return false;
				}
			});

	}
	
	
	
	@Override
	public View getRootView()
	{
		// TODO: Implement this method
		return rootView;
	}

	@Override
	public View getMainView()
	{
		// TODO: Implement this method
		return mainView;
	}
	
	
}
