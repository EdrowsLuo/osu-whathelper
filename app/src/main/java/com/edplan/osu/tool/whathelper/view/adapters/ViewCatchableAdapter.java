package com.edplan.osu.tool.whathelper.view.adapters;
import android.widget.*;
import android.view.*;
import android.util.*;

public abstract class ViewCatchableAdapter extends BaseAdapter
{
	private ArrayMap<Integer,View> views;
	
	public ViewCatchableAdapter(){
		views=new ArrayMap<Integer,View>();
	}
	

	/**
	 *先getView,再发生onScroll
	 */
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO: Implement this method
		views.put(position,convertView);
		return convertView;
	}
	
	public View getSavedView(int pos){
		return views.get(pos);
	}
	
}
