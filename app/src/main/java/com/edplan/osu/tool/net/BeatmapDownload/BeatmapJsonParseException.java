package com.edplan.osu.tool.net.BeatmapDownload;

public class BeatmapJsonParseException extends RuntimeException
{
	public BeatmapJsonParseException(String errcode){
		super(errcode);
	}
}
