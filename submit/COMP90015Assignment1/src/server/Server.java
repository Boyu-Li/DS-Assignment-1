/*
 COMP90015
 Name:Boyu Li
 Student ID:878890
 Email address:boyul@student.unimelb.edu.au
 Tutor: Xunyun Liu
 */
package server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class Server {
	
	public void launch(int port, String filePath) {
		
		Server s = new Server();
		ServerSocket listeningSocket = null;
		try {
			//Create a server socket listening on port 4444
			listeningSocket = new ServerSocket(port);
			
			
			System.out.println(Thread.currentThread().getName() + 
					" - Server listening on port "+port+" for a connection");
			
			int clientNum = 0;
			String clientAdd = "";
			//Listen for incoming connections for ever 
			while (true) {
				clientAdd="";
				//Accept an incoming client connection request 
				Socket clientSocket = listeningSocket.accept(); 
				System.out.println(Thread.currentThread().getName() 
						+ " - Client conection accepted");
				clientNum++;
				clientAdd = clientSocket.getInetAddress().getHostAddress();
								
				//Create a client connection to listen for and process all the messages
				//sent by the client
				ClientConnection clientConnection = new ClientConnection(clientSocket, clientNum,filePath,s);
				clientConnection.setName("Thread" + clientNum);
				clientConnection.setAddress(clientAdd);
				clientConnection.start(); 
				
				//Update the server state to reflect the new connected client
				ServerState.getInstance().clientConnected(clientConnection);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(listeningSocket != null) {
				try {
					listeningSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	
	}
	
	public String[] showThread(){
		List<ClientConnection> clients = ServerState.getInstance().getConnectedClients();
		String[] c = new String[clients.size()];
		int i=0;
		for(ClientConnection cl : clients)
		{
			c[i] = cl.getName();
			i++;
		}
		return c;
	}
	
	public String[] showAddress(){
		List<ClientConnection> clients = ServerState.getInstance().getConnectedClients();
		String[] c = new String[clients.size()];
		int i=0;
		for(ClientConnection cl : clients)
		{
			c[i] = cl.getAddress();
			i++;
		}
		return c;
	}

	public void killThread(String name) {
		List<ClientConnection> clients = ServerState.getInstance().getConnectedClients();
		int i=0;
		for(ClientConnection c : clients)
		{
			if(name.equals(c.getName()))
			{
				c.closeSocket();
				break;
			}
		}
	}
	


}
