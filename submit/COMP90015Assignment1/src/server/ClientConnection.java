/*
 COMP90015
 Name:Boyu Li
 Student ID:878890
 Email address:boyul@student.unimelb.edu.au
 Tutor: Xunyun Liu
 */
package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.Socket;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class ClientConnection extends Thread {

	private Socket clientSocket;
	private DataInputStream reader; 
	private DataOutputStream writer; 
	private int clientNum;
	private JSONObject dic;
	private String Path;
	private String address;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public ClientConnection(Socket clientSocket, int clientNum, String filePath, Server s) {
		this.Path= filePath;
		try {
			this.clientSocket = clientSocket;
			reader = new DataInputStream(clientSocket.getInputStream());
			// Output Stream
			writer = new DataOutputStream(clientSocket.getOutputStream());
			this.clientNum = clientNum;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		this.loadFile();
		JSONParser parser = new JSONParser();
		try {
			System.out.println(Thread.currentThread().getName() 
					+ " - Reading messages from client's " + clientNum + " connection");
			
			String command="";
			while(true)
			{
				JSONObject msg = (JSONObject) parser.parse(reader.readUTF());
				this.loadFile();
				command = (String) msg.get("command");
				String word = (String) msg.get("word");
				if(command.equals("query"))
					this.query(word);
				else if(command.equals("add"))
				{
					String meaning = (String) msg.get("meaning");
					this.add(word,meaning);
				}
				else if(command.equals("remove"))
					this.remove(word);
				else if(command.equals("disconnect"))
					break;
				else
				{
					this.disconnect("command not found!");
					break;
				}
			}
		} catch (ParseException e) {
			this.disconnect("data error!");
			System.out.println("data error!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Disconnect Successfully!");
		}
		this.updateFile();
		this.closeSocket();
	}
	
	
	public void closeSocket() {
		try {
			clientSocket.close();
		} catch (IOException e) {
			System.out.println("Socket lost!");
		}
		ServerState.getInstance().clientDisconnected(this);
		System.out.println(Thread.currentThread().getName()+ " - Client " + clientNum + " disconnected");
	}
	
	public void disconnect(String error) {
		JSONObject response = new JSONObject();
		try {
			response.put("state",error);
			writer.writeUTF(response.toJSONString());
			writer.flush();
			this.updateFile();
		} catch (IOException e) {
			System.out.println("transmission error!");
		}
		this.closeSocket();
	}

	public void query(String word) {
		try {
			JSONObject response = new JSONObject();
			if(dic.containsKey(word))
			{
				response.put("result",dic.get(word));
				response.put("state","Query Successfully!");
			}
			else
				response.put("state","The word doesn't exist!");
    		writer.writeUTF(response.toJSONString());
			writer.flush();
			System.out.println(Thread.currentThread().getName() + " - Message sent to client " + clientNum);
		} catch (IOException e) {
			System.out.println("Transmittion error!");
		}
	}
	
	public synchronized void add(String word, String meaning) {
		try {
			JSONObject response = new JSONObject();
			if(dic.containsKey(word)) {
				response.put("state","The word already exists!");
			}
			else {
				dic.put(word, meaning);
				response.put("state","Add successfully!");
				this.updateFile();
			}
    		writer.writeUTF(response.toJSONString());
			writer.flush();
			System.out.println(Thread.currentThread().getName() + " - Message sent to client " + clientNum);
		} catch (IOException e) {
			System.out.println("Transmittion error!");
		}
	}
	
	
	//Needs to be synchronized because multiple threads can me invoking this method at the same
	//time
	public synchronized void remove(String word) {
		try {
			JSONObject response = new JSONObject();
			if(dic.containsKey(word)) {
				dic.remove(word);
				response.put("state","Remove successfully!");
				this.updateFile();
			}
			else {
				response.put("state","The word doesn't exist!");
			}
    		writer.writeUTF(response.toJSONString());
			writer.flush();
			System.out.println(Thread.currentThread().getName() + " - Message sent to client " + clientNum);
		} catch (IOException e) {
			System.out.println("Transmittion error!");
		}
	}
	
	public void loadFile() {
		
		JSONParser parser = new JSONParser();
		
		String fileName =Path;
		File file = new File(fileName);
		try {
			if (!file.exists())
			{
				file.createNewFile();
				this.dic = new JSONObject();
			}
			else
			{
				Reader in = new FileReader(file);
				if(file.length()!=0)
					this.dic = (JSONObject) parser.parse(in);
				else
					this.dic = new JSONObject();
				in.close();
			}
		} catch (FileNotFoundException e1) {
			this.disconnect("File not found!");
		} catch (IOException e1) {
			this.disconnect("file read error!");
		} catch (ParseException e1) {
			this.disconnect("data error!");
		}
	}
	
	public synchronized void updateFile() {
		JSONParser parser = new JSONParser();
		
		String fileName = Path;
		File file1 = new File(fileName);
		FileWriter file;
		try {
			if (!file1.exists())
			{
				file1.createNewFile();
			}
				
			file = new FileWriter(file1);
			String JSONText = this.dic.toJSONString();
			file.write(JSONText);
			file.flush();
			System.out.println("JSON file updated");
			file.close();
		} catch (IOException e) {
			this.disconnect("File update error!");
		}
	}

}
