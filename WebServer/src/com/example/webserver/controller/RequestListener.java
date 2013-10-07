package com.example.webserver.controller;


import java.io.IOException;
import java.net.*;

import com.example.webserver.model.HandleClientThread;

//Will Listen to incoming Requests
//Instantiate a HTTPRequest object and put it in requestbuffer
//Run separate threads
public class RequestListener {

	int maxClients;
	
	public RequestListener() {
		// TODO Auto-generated constructor stub
		maxClients=100;
	}
	
	public void Listen() throws IOException
	{
		ServerSocket servsock = new ServerSocket(80);
		System.out.println("Server running...");
		
		while(true)
		{
			if(HandleClientThread.activeCount()<maxClients){
				Socket client_socket=servsock.accept();
				HandleClientThread t=new HandleClientThread(client_socket);
				t.start();
			}
			
		}
	}
}
