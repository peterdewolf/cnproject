package server;

import java.io.*; 
import java.net.*; 
import java.util.HashSet;
import java.util.Set;

public class ServerHTTP { 

	private ServerSocket welcomeSocket;
	private Set<String> allowedMethods;
	private Set<String> allowedProtocols;
	private String charset;
	
	public static final String ROOT_SERVER_FILES = "/serverfiles";
	
	public ServerHTTP(String charset) throws IOException
	{
		initialiseAllowedMethods();
		initialiseAllowedProtocols();
		welcomeSocket = new ServerSocket(80);
		this.charset = charset;
	}
	
	public void listenForConnection() throws IOException
	{
		while (true)
		{
			Socket clientSocket = welcomeSocket.accept();
			if (clientSocket != null)
			{
				SessionHandler handler = new SessionHandler(clientSocket, this);
				Thread thread = new Thread(handler);
				thread.start();
			}
		}
	}
	
	public boolean isAllowedMethod(String method)
	{
		return allowedMethods.contains(method);
	}
	
	public boolean isAllowedProtocol(String protocol)
	{
		return allowedProtocols.contains(protocol);
	}
	
	private void initialiseAllowedMethods()
	{
		allowedMethods = new HashSet<String>();
		allowedMethods.add("GET");
		allowedMethods.add("HEAD");
		allowedMethods.add("POST");
		allowedMethods.add("PUT");
	}
	
	private void initialiseAllowedProtocols()
	{
		allowedProtocols = new HashSet<String>();
		allowedProtocols.add("1.0");
		allowedProtocols.add("1.1");
	}
} 