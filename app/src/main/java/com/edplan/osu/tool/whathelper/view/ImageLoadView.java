package com.edplan.osu.tool.whathelper.view;
import android.widget.*;
import android.content.*;
import android.graphics.*;
import com.edplan.osu.tool.utils.*;
import com.edplan.osu.tool.whathelper.view.utils.*;
import android.content.res.*;

public class ImageLoadView extends ImageView implements ClearAble
{
	
	public final LoadableBitmap res;
	
	public Bitmap defaultRes;
	
	//public static Bitmap defaultDefaultRes=Resources.getb
	
	
	
	public ImageLoadView(Context c,Bitmap dRes,LoadableBitmap loadRes){
		super(c);
		final ImageLoadView body=this;
		defaultRes=dRes;
		this.setImageBitmap(defaultRes);
		res=loadRes;
		res.setLoadEventListener(new LoadEventListener(){

				@Override
				public void completeLoad()
				{
					// TODO: Implement this method
					ImageLoadView.this.setImageBitmap(res.getBitmap());
				}

				@Override
				public void startLoad()
				{
					// TODO: Implement this method
				}

				@Override
				public void loading(float progress)
				{
					// TODO: Implement this method
				}

				@Override
				public void pauseLoad(float progress)
				{
					// TODO: Implement this method
				}

				@Override
				public void continueLoad(float progress)
				{
					// TODO: Implement this method
				}

				@Override
				public void loadFailed(float progress)
				{
					// TODO: Implement this method
				}

		});
		
	}

	@Override
	public void clear()
	{
		// TODO: Implement this method
		//this.body=null;
	}
	
}
