/*
 COMP90015
 Name:Boyu Li
 Student ID:878890
 Email address:boyul@student.unimelb.edu.au
 Tutor: Xunyun Liu
 */
package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class Client {
	public Socket socket;

	
	public ArrayList<String> getMes(int commandNum,ArrayList<String> arg) {
		ArrayList<String> response = new ArrayList();
		JSONParser parser = new JSONParser();
		try {
			DataInputStream input = new DataInputStream(socket.getInputStream());
			DataOutputStream output = new DataOutputStream(socket.getOutputStream());
			JSONObject msg = new JSONObject();
			switch(commandNum)
			{
			case 1:
				msg.put("command", "query");
				msg.put("word", arg.get(0));
				break;
			case 2:
				msg.put("command", "add");
				msg.put("word", arg.get(0));
				msg.put("meaning", arg.get(1));
				
				break;
			case 3:
				msg.put("command", "remove");
				msg.put("word", arg.get(0));
				break;
			case 4:
				msg.put("command", "disconnect");
				this.disconnect();
				break;
			default: break;
			}
			output.writeUTF(msg.toJSONString());
			output.flush();
			JSONObject res = (JSONObject) parser.parse(input.readUTF());
			if(res.containsKey("state"))
				response.add((String) res.get("state")); 
			if(res.containsKey("result"))
				response.add((String) res.get("result"));
			else
				response.add("");
		} catch (IOException e) {
			response.clear();
			response.add("Transmission error!");
			response.add("");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			response.clear();
			response.add("Data error!");
			response.add("");
		}
		return response;
	}
	
	public int connect(String host,int port) {
		try {
			socket = new Socket(host, port);
		} catch (UnknownHostException e) {
			return 2;
		} catch (IOException e) {
			return 3;
		}
		System.out.println("Connection with server established");
		return 1;
	}
	
	public void disconnect() {
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
