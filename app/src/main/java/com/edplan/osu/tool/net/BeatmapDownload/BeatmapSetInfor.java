package com.edplan.osu.tool.net.BeatmapDownload;
import com.edplan.osu.tool.utils.*;

public abstract class BeatmapSetInfor
{
	public String artist;
	public String artistU;
	public BeatmapInforArray beatmaps;
	public String creator;
	public int creatorId;
	public int download;
	public int id;
	public int status;
	public long synced;
	public String syncedDateTime;
	public String title;
	public String titleU;

	@Override
	public int hashCode()
	{
		// TODO: Implement this method
		return id+title.hashCode()+creator.hashCode()+creatorId;
	}
	
	
	//unrank=0,rank=1,approved=2,qualified=3
	public abstract int getRankedStatusId();
	
	public String getRankedStatusName(){
		return MapStatus.getRandStatusName(getRankedStatusId());
	}
	
	public GameModeSet getModes(){
		return beatmaps.getModeList();
	}
	
	public float getBpm(){
		return beatmaps.getBpm();
	}
	
	public int getLength(){
		return beatmaps.getLength();
	}

	public void setArtist(String artist)
	{
		this.artist = artist;
	}

	public String getArtist()
	{
		return artist;
	}

	public void setArtistU(String artistU)
	{
		this.artistU = artistU;
	}

	public String getArtistU()
	{
		return artistU;
	}

	public void setBeatmaps(BeatmapInforArray beatmaps)
	{
		this.beatmaps = beatmaps;
	}

	public BeatmapInforArray getBeatmaps()
	{
		return beatmaps;
	}

	public void setCreator(String creator)
	{
		this.creator = creator;
	}

	public String getCreator()
	{
		return creator;
	}

	public void setCreatorId(int creatorId)
	{
		this.creatorId = creatorId;
	}

	public int getCreatorId()
	{
		return creatorId;
	}

	public void setDownloadTimes(int download)
	{
		this.download = download;
	}

	public int getDownloadTimes()
	{
		return download;
	}

	public void setBeatmapSetId(int id)
	{
		this.id = id;
	}

	public int getBeatmapSetId()
	{
		return id;
	}

	public void setStatus(int status)
	{
		this.status = status;
	}

	public int getStatus()
	{
		return status;
	}

	public void setSyncedTime(long synced)
	{
		this.synced = synced;
	}

	public long getSyncedTime()
	{
		return synced;
	}

	public void setSyncedDateTime(String syncedDateTime)
	{
		this.syncedDateTime = syncedDateTime;
	}

	public String getSyncedDateTime()
	{
		return syncedDateTime;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitleU(String titleU)
	{
		this.titleU = titleU;
	}

	public String getTitleU()
	{
		return titleU;
	}
}
