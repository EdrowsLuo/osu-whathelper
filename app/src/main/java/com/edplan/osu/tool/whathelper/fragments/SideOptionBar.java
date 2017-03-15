package com.edplan.osu.tool.whathelper.fragments;
import android.app.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import com.edplan.osu.tool.whathelper.*;
import com.edplan.osu.tool.whathelper.activitys.*;
import com.edplan.osu.tool.whathelper.widgets.*;
import com.gc.materialdesign.views.*;
import java.io.*;
import android.util.*;
import android.preference.*;
import com.edplan.osu.tool.whathelper.view.*;
import android.content.*;
import com.edplan.osu.framework.utils.*;
import com.edplan.osu.framework.net.BeatmapDownload.*;

public class SideOptionBar extends Fragment
{
	
	public OnRankStatuOptionChangeListener rankL;
	public OnModeOptionChangeListener modeL;
	public OnSiteChangeListener siteL;
	
	public ViewGroup sideOptionList;
	LinearLayout sideDownloadList;
	
	public ButtonFlat mpHelperButton;
	
	public MyCheckBox[] rankStatuOptions;
	public MyCheckBox[] modeOptions;
	public MyCheckBox[] sites;
	
	public SideOptionBar(){
		
	}
	
	public SideOptionBar(OnRankStatuOptionChangeListener l){
		rankL=l;
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		// TODO: Implement this method
		final View bar=inflater.inflate(R.layout.side_option_bar,container,false);
		
		sideOptionList=(ViewGroup)bar;// (ViewGroup)bar.findViewById(R.id.sideOptionList);
		//sideOptionList.addView(inflater.inflate(R.layout.side_option_bar,null,false));
		
		{
			sideDownloadList=(LinearLayout) sideOptionList.findViewById(R.id.sideDownloadList);
			sideDownloadList.addView(inflater.inflate(R.layout.entry_download_list,null,false));
			ProgressBarDeterminate progressBar=(ProgressBarDeterminate)sideOptionList.findViewById(R.id.downloadProgress);
			progressBar.setProgress(62);
		}
		
		
		final TextView songsPathText=(TextView)sideOptionList.findViewById(R.id.songsPathText);
		songsPathText.setText(Options.getString("OsuSongsDir"));

		
		ButtonFlat sideOptionSongsPath=(ButtonFlat)sideOptionList.findViewById(R.id.sideOptionSongsPath);
		
		rankStatuOptions=new MyCheckBox[]{
			(MyCheckBox)sideOptionList.findViewById(R.id.checkBox_Unrank),
			(MyCheckBox)sideOptionList.findViewById(R.id.checkBox_Ranked),
			(MyCheckBox)sideOptionList.findViewById(R.id.checkBox_Approved),
			(MyCheckBox)sideOptionList.findViewById(R.id.checkBox_Qualified)
		};
		
		mpHelperButton=(ButtonFlat) sideOptionList.findViewById(R.id.mpActivityEntrance);
		
		mpHelperButton.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View p1) {
					// TODO: Implement this method
					Intent openMpHelper=new Intent(bar.getContext(),MpHelperActivity.class);
					startActivity(openMpHelper);
				}
		});
		
		for(int i=0;i<rankStatuOptions.length;i++){
			rankStatuOptions[i].setOncheckListener(
				new com.gc.materialdesign.views.CheckBox.OnCheckListener(){
					@Override
					public void onCheck(com.gc.materialdesign.views.CheckBox view, boolean check)
					{
						// TODO: Implement this method
						if(rankL!=null){
							rankL.onRankStatuOptionChange(
								new boolean[]{
									rankStatuOptions[0].isCheck(),
									rankStatuOptions[1].isCheck(),
									rankStatuOptions[2].isCheck(),
									rankStatuOptions[3].isCheck()
								}
							);
						}
					}
				});
		}
		
		modeOptions=new MyCheckBox[]{
			(MyCheckBox)sideOptionList.findViewById(R.id.checkBox_std),
			(MyCheckBox)sideOptionList.findViewById(R.id.checkBox_taiko),
			(MyCheckBox)sideOptionList.findViewById(R.id.checkBox_ctb),
			(MyCheckBox)sideOptionList.findViewById(R.id.checkBox_mania)
		};
		for(int i=0;i<modeOptions.length;i++){
			modeOptions[i].setOncheckListener(
				new com.gc.materialdesign.views.CheckBox.OnCheckListener(){
					@Override
					public void onCheck(com.gc.materialdesign.views.CheckBox view, boolean check)
					{
						// TODO: Implement this method
						if(modeL!=null){
							GameModeSet modes=new GameModeSet();
							for(int ii=0;ii<modeOptions.length;ii++){
								modes.setMode(ii,modeOptions[ii].isCheck());
							}
							modeL.onModeOptionChange(modes);
						}
					}
				});
		}
		
		sites=new MyCheckBox[]{
			(MyCheckBox)sideOptionList.findViewById(R.id.checkBox_Mengsky),
			(MyCheckBox)sideOptionList.findViewById(R.id.checkBox_bloodcat)
		};
		
		for(int i=0;i<sites.length;i++){
			final int fi=i;
			sites[i].setOncheckListener(new com.gc.materialdesign.views.CheckBox.OnCheckListener(){
					@Override
					public void onCheck(com.gc.materialdesign.views.CheckBox view, boolean check) {
						// TODO: Implement this method
						for(int i=0;i<sites.length;i++){
							if(i==fi){
								sites[i].setChecked(true);
								if(siteL!=null){
									Site s;
									switch(i){
										case 0:
											s=Site.Mengsky;
											break;
										case 1:
											s=Site.Bloodcat;
											break;
										default:
											s=Site.Mengsky;
									}
									siteL.onSiteChange(s);
								}
							}else{
								sites[i].setChecked(false);
							}
						}
					}
			});
		}
		
		
		sideOptionSongsPath.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
					final TextInputDialog pathSet=new TextInputDialog(bar.getContext(),"输入songs目录",true);
					pathSet.setOnAcceptListener(new TextInputDialog.OnAcceptListener(){
							@Override
							public void onAccept(MyDialog dialog)
							{
								// TODO: Implement this method
								String input=((TextInputDialog)dialog).getTextInputString();
								File songsDir=new File(input);
								if(!songsDir.exists()){
									Toast.makeText(dialog.getContext(),"路径不存在 (ಥ_ಥ)",Toast.LENGTH_SHORT).show();
								}else if(!songsDir.isDirectory()){
									Toast.makeText(dialog.getContext(),"这不是一个文件夹 (ಥ_ಥ)",Toast.LENGTH_SHORT).show();
								}else{
									if(!input.substring(input.length()-1).equals("/")){
										input+="/";
									}
									songsPathText.setText(input);
									Options.putString("OsuSongsDir",input);
									dialog.dismiss();
								}
							}
						});
					pathSet.show();
				}
			});
		
		return bar;
	}
	
	public void addDownloaderToDownloadList(){
		
	}
	
	public void setOnRankStatuOptionChangeListener(OnRankStatuOptionChangeListener l){
		rankL=l;
	}
	
	public interface OnRankStatuOptionChangeListener{
		public void onRankStatuOptionChange(boolean[] rankStatus);
	}
	
	public void setOnModeOptionChangeListener(OnModeOptionChangeListener l){
		modeL=l;
	}
	
	public interface OnModeOptionChangeListener{
		public void onModeOptionChange(GameModeSet modes);
	}
	
	public void setOnSiteChangeListener(OnSiteChangeListener l){
		siteL=l;
	}
	
	public interface OnSiteChangeListener{
		public void onSiteChange(Site site);
	}
	
}
