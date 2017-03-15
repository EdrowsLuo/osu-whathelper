package com.edplan.osu.tool.whathelper.widgets;
import android.view.animation.*;
import android.view.*;
import com.edplan.osu.tool.whathelper.*;
import android.content.*;
import com.gc.materialdesign.views.*;

public abstract class MyDialog extends android.app.Dialog
{
	OnAcceptListener acceptL;
	OnCancelButtonClickListener cancelBL;
	
	ButtonFlat acceptB;
	ButtonFlat cancelB;
	
	
	public MyDialog(Context c,int style){
		super(c,style);
	}
	
	public MyDialog(Context c){
		this(c,R.style.DialogTheme);
		//android.R.style.Theme_Translucent
	}
	
	
	public abstract View getRootView();
	
	public abstract View getMainView();
	
	public void setOnAcceptListener(OnAcceptListener l){
		acceptL=l;
	}
	
	public interface OnAcceptListener{
		public void onAccept(MyDialog dialog);
	}
	
	public void setOnCancelButtonClickListener(OnCancelButtonClickListener l){
		cancelBL=l;
	}
	
	public interface OnCancelButtonClickListener{
		public void onCancel(MyDialog dialog);
	}
	
	@Override
	public void dismiss()
	{
		// TODO: Implement this method
		Animation anim=AnimationUtils.loadAnimation(getContext(),R.anim.dialog_main_hide_amination);
		anim.setAnimationListener(new Animation.AnimationListener(){

				@Override
				public void onAnimationStart(Animation p1)
				{
					// TODO: Implement this method
				}

				@Override
				public void onAnimationEnd(Animation p1)
				{
					// TODO: Implement this method
					getMainView().post(new Runnable() {
							@Override
							public void run() {
								MyDialog.super.dismiss();
							}
						});
				}

				@Override
				public void onAnimationRepeat(Animation p1)
				{
					// TODO: Implement this method
				}


			});

		getRootView().startAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.dialog_root_hide_amin));
		getMainView().startAnimation(anim);

		//super.dismiss();
	}
	
	@Override
	public void show()
	{
		// TODO: Implement this method
		super.show();
		getRootView().startAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.dialog_root_show_amin));
		getMainView().startAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.dialog_main_show_amination));
	}
	
}
