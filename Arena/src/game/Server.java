package game;
import java.net.*;
import java.util.*;
import java.io.*;

public class Server {

	private ServerSocket serverSocket;
	private ArrayList<Socket> clientList;
	private Socket s;
	private PrintWriter pw;
	
	public Server() {
		try {
			serverSocket = new ServerSocket(5378);
			s = serverSocket.accept();
			pw = new PrintWriter(s.getOutputStream(),true);
			pw.print("Hello World.");
			pw.close();
			s.close();
			serverSocket.close();
		}
		catch (Exception e) {
			System.out.println("Error.");
		}
	}
}
