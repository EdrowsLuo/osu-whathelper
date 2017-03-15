package material.edplan.animation.interpolators;
import android.view.animation.*;

public abstract class AutoCompleteInterpolator implements Interpolator
{
	

	@Override
	public float getInterpolation(float x)
	{
		// TODO: Implement this method
		return (getInterpolationWave(x)-getMin())/(getMax()-getMin());
	}
	
	public float getMax(){
		return getInterpolationWave(1);
	}
	
	public float getMin(){
		return getInterpolationWave(0);
	}
	
	public abstract float getInterpolationWave(float x);

	
}
