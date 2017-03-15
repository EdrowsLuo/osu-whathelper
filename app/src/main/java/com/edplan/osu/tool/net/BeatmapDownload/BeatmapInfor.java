package com.edplan.osu.tool.net.BeatmapDownload;
import org.json.*;

public class BeatmapInfor
{
	public double ar;
	public double bpm;
	public double cs;
	public double hp;
	public int id;
	public int lenght;
	public int mode;
	public String name;
	public double od;
	
	public BeatmapInfor(){
		
	}
	
	public BeatmapInfor(JSONObject map){
		
	}

	public void setAr(double ar)
	{
		this.ar = ar;
	}

	public double getAr()
	{
		return ar;
	}

	public void setBpm(double bpm)
	{
		this.bpm = bpm;
	}

	public double getBpm()
	{
		return bpm;
	}

	public void setCs(double cs)
	{
		this.cs = cs;
	}

	public double getCs()
	{
		return cs;
	}

	public void setHp(double hp)
	{
		this.hp = hp;
	}

	public double getHp()
	{
		return hp;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public int getId()
	{
		return id;
	}

	public void setLenght(int lenght)
	{
		this.lenght = lenght;
	}

	public int getLenght()
	{
		return lenght;
	}

	public void setMode(int mode)
	{
		this.mode = mode;
	}

	public int getMode()
	{
		return mode;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	public void setOd(double od)
	{
		this.od = od;
	}

	public double getOd()
	{
		return od;
	}
	
	/**
	 *返回值必须是现在BeatmapInfor的实例
	 */
	
	public BeatmapInfor getSample(JSONObject map)throws JSONException{
		return null;
	}

	@Override
	public String toString()
	{
		// TODO: Implement this method
		String s="";
		s="difficulty:\n  "+getName()+
		  "\n  ar:"+getAr()+"\n  bpm:"+getBpm()+"\n  length:"+getLenght();
		return s;
	};
	
}
