package material.edplan.animation.interpolators;

public class CubeInterpolator extends AutoCompleteInterpolator
{

	/**
	 *f'=a(x-1)(x-v)=a(x*x-(1+v)x+v)
	 *f=a(x*x/3-(1+v)/2*x+v*x)
	 */
	 
	public float v;
	
	public CubeInterpolator(float v){
		this.v=v;
	}
	
	@Override
	public float getInterpolationWave(float x)
	{
		// TODO: Implement this method
		return x*x*x/3-(1+v)/2*x*x+v*x;
	}

}
