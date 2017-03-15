package material.edplan.views;
import com.gc.materialdesign.views.*;
import android.util.*;
import android.content.*;
import com.gc.materialdesign.utils.*;
import android.view.*;

public class FastRippleButtonFloat extends ButtonFloat
{
	public FastRippleButtonFloat(Context c,AttributeSet attr){
		super(c,attr);
		setRippleSpeed(Utils.dpToPx(2,getResources()));
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		// TODO: Implement this method
		invalidate();
		if (isEnabled()) {
			isLastTouch = true;
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				radius = getHeight() / rippleSize;
				x = event.getX();
				y = event.getY();
			} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
				radius = getHeight() / rippleSize;
				x = event.getX();
				y = event.getY();
				if (!((event.getX() <= getWidth() && event.getX() >= 0) && (event
					.getY() <= getHeight() && event.getY() >= 0))) {
					isLastTouch = false;
					x = -1;
					y = -1;
				}
			} else if (event.getAction() == MotionEvent.ACTION_UP) {
				if ((event.getX() <= getWidth() && event.getX() >= 0)
					&& (event.getY() <= getHeight() && event.getY() >= 0)) {
					radius++;
					if (onClickListener != null) {
						onClickListener.onClick(this);
					}
				} else {
					isLastTouch = false;
					x = -1;
					y = -1;
				}
			} else if (event.getAction() == MotionEvent.ACTION_CANCEL) {
				isLastTouch = false;
				x = -1;
				y = -1;
			}
		}
		return true;
	}
}
