package material.edplan.animation.interpolators;
import android.view.animation.*;

public class TestInterpolator implements Interpolator
{
	float v;
	
	public TestInterpolator(float v){
		this.v=v;
	}
	
	public TestInterpolator(){
		this(0.8f);
	}

	@Override
	public float getInterpolation(float x)
	{
		// TODO: Implement this method
		return x*(2*v-x)/(2*v-1);
	}

}
