package material.edplan.views;
import android.widget.*;
import android.util.*;
import android.content.*;
import android.graphics.*;

public class FrameAnimaView extends ImageView
{
	public Bitmap[] frames;
	public float totalTime=1;
	public boolean ifAgain=true;
	public long lastDraw;
	public Statu statu=Statu.Stopped;
	
	public enum Statu{
		Drawing,Stopped
	}
	
	public FrameAnimaView(Context c,AttributeSet attrs){
		super(c,attrs);
	}
	
	public void setFrameImages(Bitmap[] f){
		frames=f;
	}
	
	public void start(){
		statu=Statu.Drawing;
		lastDraw=System.currentTimeMillis();
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		// TODO: Implement this method
		super.onDraw(canvas);
		
	}
}
