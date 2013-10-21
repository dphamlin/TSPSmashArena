package game;
import java.util.*;
import java.io.*;
import java.net.*;


public class Client {
	
	private Socket socket;
	private String stateString;
	private BufferedReader reader;
	private PrintWriter writer;
	private GameState game;
	
	Client (InetAddress addr, int port) throws IOException { 
		setSocket(new Socket(addr,port)); // Establish connection
		setStateString("Awaiting state from server.\n");
		setReader(new BufferedReader(new InputStreamReader(getSocket().getInputStream())));
		setWriter(new PrintWriter(getSocket().getOutputStream(),true));
	}
	
	public void updateStateString() throws IOException {
		setStateString(getReader().readLine()); // Should block until line received from server
	}
	
	public void setState(GameState g){
		this.game = g;
	}
	
	public GameState getState(){
		return this.game;
	}
	
	public void writeToServer(String outbound) {
		this.getWriter().println(outbound);
	}
	
	public void setWriter(PrintWriter printWriter) {
		this.writer = printWriter;
	}
	
	public PrintWriter getWriter() {
		return this.writer;
	}
	public void setReader(BufferedReader bufferedReader) {
		this.reader = bufferedReader;
	}
	
	public BufferedReader getReader() {
		return this.reader;
	}
	
	public String getStateString() {
		return this.stateString;
	}
	
	public void setStateString(String string) {
		this.stateString = string;
	}
	
	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	
	public Socket getSocket() {
		return this.socket;
	}
}
