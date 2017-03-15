package com.edplan.task.utils;
import java.util.*;

public class ObjectPush<E>
{
	protected ArrayList<E> objects;
	protected int visibleObjCount;
	
	public ObjectPush(){
		objects=new ArrayList<E>();
		visibleObjCount=0;
	}
	
	
	public int getObjectCount(){
		return visibleObjCount;
	}
	
	public E getEAtIndex(int i){
		return objects.get(i);
	}
	
	public boolean contain(E e){
		return objects.contains(e);
	}
	
	public int getIndex(E e){
		return objects.indexOf(e);
	}
	
	public synchronized void addObj(E e){
		/*objects.add(visibleObjCount,e);
		visibleObjCount++;*/
		addObj(e,visibleObjCount);
	}
	
	public synchronized void addObj(E e,int index){
		if(index>=visibleObjCount)return;
		
		moveArray(index,false);
		objects.add(index,e);
	}
	
	public synchronized void sweep(int index1,int index2){
		E e=objects.get(index1);
		objects.add(index1,objects.get(index2));
		objects.add(index2,e);
	}
	
	public synchronized E push(int pos){
		if(visibleObjCount<=pos){
			return null;
		}
		E e=objects.get(pos);
		objects.add(pos,null);
		moveArray(pos,true);
		visibleObjCount--;
		return e;
	}
	
	public synchronized E push(){
		return push(0);
	}
	
	private synchronized void moveArray(int pos,boolean ROrL){
		if(ROrL){
			//←
			for(int i=pos;i<visibleObjCount;i++){
				sweep(i,i+1);
			}
		}else{
			//→
			objects.add(visibleObjCount,null);
			visibleObjCount++;
			for(int i=visibleObjCount-1;i>pos;i--){
				sweep(i,i-1);
			}
		}
	}
	
}
