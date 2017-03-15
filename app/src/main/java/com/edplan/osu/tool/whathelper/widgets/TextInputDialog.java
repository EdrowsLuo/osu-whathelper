package com.edplan.osu.tool.whathelper.widgets;
import android.content.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import com.edplan.osu.tool.whathelper.*;
import com.gc.materialdesign.views.*;

public class TextInputDialog extends MyDialog
{
	public View rootView;
	public View mainView;
	public String title;
	public boolean clickDismiss;

	public TextView titleView;
	public ButtonFlat acceptB;
	public ButtonFlat cancelB;
	
	public EditText editText;
	
	public TextInputDialog(Context c,String title,boolean clickDismiss){
		super(c/*,android.R.style.Theme_Translucent*/);
		this.title=title;
		this.clickDismiss=clickDismiss;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_textinput);
		editText=(EditText)findViewById(R.id.dialogTextInput);
		rootView=findViewById(R.id.dialogRootView);
		mainView=findViewById(R.id.dialogMainView);
		((TextView)findViewById(R.id.dialogTitle)).setText(title);
		acceptB=(ButtonFlat)findViewById(R.id.dialogAcceptB);
		cancelB=(ButtonFlat)findViewById(R.id.dialogCancelB);
		
		acceptB.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
					if(acceptL!=null)acceptL.onAccept(TextInputDialog.this);
				}
		});
		
		

		rootView.setOnTouchListener(new OnTouchListener(){
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
	
	public String getTextInputString(){
		return editText.getText().toString();
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
