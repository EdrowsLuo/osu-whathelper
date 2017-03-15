package com.edplan.osu.tool.whathelper.widgets;
import android.widget.*;
import android.util.*;
import android.content.*;
import android.widget.AbsListView.*;
import android.view.*;
import com.edplan.osu.tool.whathelper.view.adapters.*;

public class MyListView extends ListView
{
	private OnScrollListener scrollL;
	
	private OnViewIOListener viewIOL;
	
	private int firstVisibleItemPosition=-1;
	private int lastVisibleItemPosition=-1;
	
	public MyListView(Context c,AttributeSet attr){
		super(c,attr);
		super.setOnScrollListener(
			new OnScrollListener(){
				@Override
				public void onScrollStateChanged(AbsListView view, int scrollState) {
					// TODO: Implement this method
					if(scrollL!=null){
						scrollL.onScrollStateChanged(view,scrollState);
					}
				}

				@Override
				public void onScroll(AbsListView view, int first, int count, int total) {
					// TODO: Implement this method
					if(scrollL!=null){
						scrollL.onScroll(view,first,count,total);
					}
					if(first!=firstVisibleItemPosition){
						boolean iOrO=(first<firstVisibleItemPosition);
						firstVisibleItemPosition=first;
						if(viewIOL!=null){
							Log.v("view io","first: "+firstVisibleItemPosition);
							viewIOL.onViewIO(MyListView.this,MyListView.this.getViewAtPosition(firstVisibleItemPosition),firstVisibleItemPosition,iOrO);
						}
						//Log.v("scroll","first change: "+first);
					}
					if(first+count!=lastVisibleItemPosition){
						boolean iOrO=(first+count>lastVisibleItemPosition);
						lastVisibleItemPosition=first+count;
						if(viewIOL!=null){
							viewIOL.onViewIO(MyListView.this,MyListView.this.getViewAtPosition(firstVisibleItemPosition),lastVisibleItemPosition,iOrO);
						}
					}
					//MyListView.this.getAdapter().get
				}
			}
		);
	}
	
	//public int getFirstVi
	
	public View getViewAtPosition(int pos){
		if(vc!=null){
			return vc.getSavedView(pos);
		}else{
			Log.v("view io","vc null!: "+getAdapter());
			return null;
		}
	}
	
	ViewCatchableAdapter vc;

	@Override
	public void setAdapter(ListAdapter adapter) {
		// TODO: Implement this method
		super.setAdapter(adapter);
		if(adapter instanceof ViewCatchableAdapter){
			vc=(ViewCatchableAdapter) adapter;
		}
	}
	
	@Override
	public void setOnScrollListener(AbsListView.OnScrollListener l) {
		// TODO: Implement this method
		//super.setOnScrollListener(l);
		scrollL=l;
	}
	
	public void setOnViewIOListener(OnViewIOListener l){
		this.viewIOL=l;
	}
	
	public interface OnViewIOListener{
		
		public static final boolean IN=true;
		public static final boolean OUT=false;
		
		public void onViewIO(AbsListView parent,View view,int position,boolean iOrO);
	}
	
}
