package game;

import java.util.*;
import java.io.*;
import java.net.*;


public class Client {
	
	private Socket socket;
	private String stateString;
	private BufferedReader reader;
	private PrintWriter writer;
	
	Client (InetAddress addr, int port) throws IOException { 
		setSocket(new Socket(addr,port)); // Establish connection
		setStateString("Awaiting state from server.\n");
		setReader(new BufferedReader(new InputStreamReader(getSocket().getInputStream())));
		setWriter(new PrintWriter(getSocket().getOutputStream(),true));
	}
	
	public void updateStateString() throws IOException {
		setStateString(getReader().readLine()); // Should block until line received from server
	}
	
	public void writeToServer(String outbound) {
		getWriter().println(outbound);
	}
	
	public void setWriter(PrintWriter printWriter) {
		writer = printWriter;
	}
	
	public PrintWriter getWriter() {
		return writer;
	}
	public void setReader(BufferedReader bufferedReader) {
		reader = bufferedReader;
	}
	
	public BufferedReader getReader() {
		return reader;
	}
	
	public String getStateString() {
		return stateString;
	}
	
	public void setStateString(String string) {
		stateString = string;
	}
	
	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	
	public Socket getSocket() {
		return socket;
	}
	
	public static void main(String []args) {
		Connection con = new Connection(InetAddress.getLoopbackAddress());
		String r = con.recieve();
		while(r.compareTo("close\n") != 0){
			System.out.println("    "+r);
			String m = "Gotcha, here's the time -> "+System.currentTimeMillis()+"\n";
			System.out.println("    "+m);
			con.send(m);
			r = con.recieve();
		}
		con.send("Good bye!\n");
		con.close();
	}
}
