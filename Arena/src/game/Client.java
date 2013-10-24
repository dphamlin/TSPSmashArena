package game;
import java.util.*;
import java.io.*;
import java.net.*;
import com.google.gson.*;


public class Client {
	
	private Socket socket;
	private String stateString;
	private BufferedReader reader;
	private PrintWriter writer;
	private ClientGameState game;
	private Gson json;
	private Controller controller = null;
	
	Client (InetAddress addr, int port) throws IOException { 
		setSocket(new Socket(addr,port)); // Establish connection
		setStateString("Awaiting state from server.\n");
		setReader(new BufferedReader(new InputStreamReader(getSocket().getInputStream())));
		setWriter(new PrintWriter(getSocket().getOutputStream(),true));
		setController(new Controller());
		json = new Gson();
	}
	
	public void readStateString() throws IOException {
		setStateString(getReader().readLine()); // Should block until line received from server
	}
	
	public void setState(GameState g) {
		this.game = (ClientGameState) g; // Will be casting (ServerGameState) objects to ClientGameState
		// May need to instead 'update' current game state with information from read game state
		// in case client-side information is lost by the cast.
	}
	
	public GameState getState(){
		return this.game;
	}
	
	public void readGameState() throws IOException {
		setState(json.fromJson(getReader().readLine(), ClientGameState.class));
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
	
	public Controller getController() {
		return controller;
	}
	
	public void setController(Controller c) {
		this.controller = c;
	}
 	
	public void writeController() {
		writeToServer(json.toJson(getController())); // There is also a Gson method to directly write to a Writer
	}
}
