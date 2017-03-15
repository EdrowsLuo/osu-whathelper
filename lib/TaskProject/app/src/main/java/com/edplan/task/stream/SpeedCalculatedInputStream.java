package com.edplan.task.stream;
import java.io.*;

public class SpeedCalculatedInputStream extends InputStream
{
	protected InputStream in;
	
	//public long[] time;
	//public int[] bytes;
	public float speed;
	public long lastUpdateTime;
	public int lastUpdateByte;
	public int currentByte;
	public int updateDeltaTime=200;
	
	public SpeedCalculatedInputStream(InputStream in){
		this.in=in;
		speed=0;
		lastUpdateByte=0;
		lastUpdateTime=System.currentTimeMillis();
		currentByte=0;
		//time=new long[10];
		//bytes=new int[10];
	}

	@Override
	public int read(byte[] b, int off, int len) throws IOException
	{
		// TODO: Implement this method
		int i=in.read(b, off, len);
		currentByte+=i;
		cheakSpeed();
		return i;
	}

	
	/*
	@Override
	public int read(byte[] b) throws IOException
	{
		// TODO: Implement this method
		return super.read(b);
	}*/
	
	@Override
	public int read() throws IOException
	{
		// TODO: Implement this method
		currentByte++;
		return in.read();
	}
	
	public void cheakSpeed(){
		if((System.currentTimeMillis()-lastUpdateTime)>updateDeltaTime){
			speed=1000*((float)(currentByte-lastUpdateByte))/(System.currentTimeMillis()-lastUpdateTime);
			lastUpdateTime=System.currentTimeMillis();
			lastUpdateByte=currentByte;
		}
	}
	
}
