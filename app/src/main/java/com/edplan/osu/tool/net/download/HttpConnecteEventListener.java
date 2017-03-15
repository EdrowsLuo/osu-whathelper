package com.edplan.osu.tool.net.download;
import java.net.*;
import java.io.*;

public abstract class HttpConnecteEventListener
{
	
	public abstract void fileNotFound(FileNotFoundException fn);
	
	public abstract void unkonwHost(UnknownHostException u);
	
	public abstract void connected(int costTime);
	
}
