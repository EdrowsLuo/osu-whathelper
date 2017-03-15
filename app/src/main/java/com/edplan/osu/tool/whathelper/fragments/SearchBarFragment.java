package com.edplan.osu.tool.whathelper.fragments;

import android.app.*;
import android.view.*;
import android.os.*;
import com.edplan.osu.tool.whathelper.*;
import android.widget.*;
import android.widget.TextView.*;
import android.view.inputmethod.*;
import android.content.*;
import android.text.*;

public class SearchBarFragment extends Fragment
{
	EditText editText;
	View main;
	OnSearchListener searchL;
	OnKeyWordsEditorActionListener actionL;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		// TODO: Implement this method
		main=inflater.inflate(R.layout.fragment_search_bar,container,false);
		editText=(EditText)main.findViewById(R.id.fragmentSearchEditText);
		editText.setFocusable(true);  
		editText.setFocusableInTouchMode(true);
		editText.requestFocus();
		InputMethodManager inputManager =  
			(InputMethodManager)editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);  
		inputManager.toggleSoftInput(0,0);
		
		editText.setOnEditorActionListener(new OnEditorActionListener(){
				@Override
				public boolean onEditorAction(TextView view, int actionCode, KeyEvent keyEvent)
				{
					// TODO: Implement this method
					if(actionCode==EditorInfo.IME_ACTION_SEARCH){
						if(searchL!=null){
							if(callOnSearch()){
								return true;
							}
						}
					}
					return false;
				}
		});
		
		editText.addTextChangedListener(new TextWatcher(){
				@Override
				public void beforeTextChanged(CharSequence p1, int p2, int p3, int p4)
				{
					// TODO: Implement this method
				}

				@Override
				public void onTextChanged(CharSequence p1, int p2, int p3, int p4)
				{
					// TODO: Implement this method
				}

				@Override
				public void afterTextChanged(Editable s)
				{
					// TODO: Implement this method
					String t=s.toString();
					if(t.length()>0&&t.substring(t.length()-1).equals("\n")){
						s.delete(s.length()-1,s.length());
						callOnSearch();
					}
				}
		});
		
		return main;
	}

	@Override
	public void onResume()
	{
		// TODO: Implement this method
		super.onResume();
	}
	
	public boolean callOnSearch(){
		/*editText.clearFocus();
		InputMethodManager inputManager =  
			(InputMethodManager)editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);  
		inputManager.toggleSoftInput(0,InputMethodManager.);*/
		Toast.makeText(editText.getContext(),"callOnSearch!",Toast.LENGTH_SHORT).show();
		if(searchL!=null){
			return searchL.onSearch(getEditText().getText().toString());
		}else {
			return false;
		}
	}
	
	public EditText getEditText(){
		return editText;
	}
	
	
	public void setOnSearchListener(OnSearchListener l){
		searchL=l;
	}
	
	public interface OnSearchListener{
		public boolean onSearch(String keyWords);
	}
	
	public interface OnKeyWordsEditorActionListener{
		public boolean onEditorAction(EditText view,int actionCode,KeyEvent keyEvent);
	}
	
}
