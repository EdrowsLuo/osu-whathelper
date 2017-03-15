package com.edplan.osu.tool.net.BeatmapDownload;

public interface BeatmapInforGetter
{
	
	
	
	public void get(String toSearch,
	                boolean containRank,boolean containUnrank,
					boolean containApproved,boolean containQualified,
					int page);
	/**
	 *
	 *
	 */
	
	public String getUrl(String toSearch,
						 boolean containRank,boolean containUnrank,
						 boolean containApproved,boolean containQualified,
						 int page)
	
	
}
