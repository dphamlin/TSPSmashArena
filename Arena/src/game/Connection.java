package game;
import java.net.*;
import java.util.*;
import java.io.*;

public class Connection {

	private ServerSocket serverSocket;
	private ArrayList<Socket> clientList;
	private Socket s;
	private PrintWriter out;
	private BufferedReader in;
	private InetAddress addr;
	
	public Connection(ServerSocket srv) {
		try {
			this.serverSocket = srv;
			this.s = serverSocket.accept();
			System.out.println("Connected, type \"close\" to quit.");
		}
		catch (Exception e) {
			System.out.println(e.getStackTrace());
		}
		this.addr = null;
	}
	
	public Connection(InetAddress addr){
			this.addr = addr;
		try {
			this.s = new Socket(this.addr, 5379);
			System.out.println("Connected");
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.serverSocket = null;
	}
	
	public void send(String m){
		try {
			this.out = new PrintWriter(this.s.getOutputStream(), true);
			this.out.println(m);
			System.out.println("Sent");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String recieve(){
		String m = "Nothing";
		try {
			this.in = new BufferedReader(new InputStreamReader(this.s.getInputStream()));
			System.out.println("Received: ");
			m = this.in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return m;
	}
	
	public void close(){
		try {
			this.s.close();
			this.serverSocket.close();
			System.out.println("Disconnected");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Socket getSocket(){
		return s;
	}
	
	public ServerSocket getServerSocket() {
		return serverSocket;
	}
}
