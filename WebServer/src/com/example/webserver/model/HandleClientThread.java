package com.example.webserver.model;

import java.net.*;
import java.sql.Date;
import java.io.*;

import org.omg.CORBA_2_3.portable.InputStream;

public class HandleClientThread extends Thread{

	Socket client_socket;
	
	public HandleClientThread(Socket client_socket)
	{
		this.client_socket=client_socket;
	}
	
	public void run()
	{
		try{
			String client_str=null;
			String file_name=null;
			String content_type=null;
			
				java.io.InputStream socket_in=client_socket.getInputStream();
				InputStreamReader isr;
				isr = new InputStreamReader(socket_in,"8859_1");
				BufferedReader client_in=new BufferedReader(isr);
				
				client_str=client_in.readLine();
				System.out.println("Client message: "+client_str);
				String[] tokens=client_str.split(" ");
				
				if((tokens.length>=2) && tokens[0].equals("GET")){
					file_name=tokens[1];
				}
				
				if(file_name.startsWith("/"))
					 file_name=file_name.substring(1);
				
				if(file_name.endsWith("/") || file_name.equals(""))
					file_name=file_name + "index.html";
				
				
				if(file_name.endsWith(".html") || file_name.endsWith(".htm")){
					content_type = "text/html";
				}
				
			OutputStream out = client_socket.getOutputStream();
			OutputStreamWriter osr = new OutputStreamWriter(out,"8859_1");
			PrintWriter pw_client_out=new PrintWriter(osr,true);
			
			FileInputStream file_in = new FileInputStream(file_name);
			pw_client_out.print("HTTP/1.0 200 OK\r\n");
			File file=new File(file_name);
			Date date = new Date(file.lastModified());
			pw_client_out.print("Date: "+date+"\r\n");
			pw_client_out.print("Server: ShivamServer 1.0\r\n");
			pw_client_out.print("Content-length: "+file_in.available() + "\r\n");
			pw_client_out.print("Content-type: "+content_type+"\r\n\r\n");
			
			byte[] data = new byte[file_in.available()];
			file_in.read(data);
			client_out.write(data);
			client_out.flush();
			file_in.close();
			
		}
		catch(IOException e){
			
		}
		
	}
	
	
}
