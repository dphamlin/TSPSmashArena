package game;
import java.util.*;
import java.io.*;
import java.net.*;

import com.google.gson.*;


public class Client {
	
	private Socket socket;
	private String stateString;
	private BufferedReader reader;
	private BufferedWriter writer;
	private ClientGameState game;
	private Gson json;
	private Controller controller;
	private View view;
	private StopWatch timer;
	private static int port = 5379;
	private Message messageFromServer;
	private Message messageToServer;
	private GameResults gameResults;
	
	Client (InetAddress addr, int port, View v) throws IOException { 
		setSocket(new Socket(addr,port)); // Establish connection
		setStateString("Awaiting state from server.\n");
		setReader(new BufferedReader(new InputStreamReader(getSocket().getInputStream())));
		setWriter(new BufferedWriter(new OutputStreamWriter(getSocket().getOutputStream())));
		setController(new Controller());
		setView(v);
		setTimer(new StopWatch(20));
		setMessageFromServer(new Message(0,null));
		setMessageToServer(new Message(0,null));
		setGameResults(null);
		
		view.attachController(controller);
		json = new Gson();
	}
	
	public void readStateString() throws IOException {
		setStateString(getReader().readLine()); // Should block until line received from server
	}
	
	public void setState(GameState g) throws Exception {
		if (g == null)
			throw new Exception("Null game state.");
		
		this.game = (ClientGameState) g; // Will be casting (ServerGameState) objects to ClientGameState
	}
	
	public ClientGameState getState(){
		return this.game;
	}
	
	public void readGameState() throws IOException, Exception {
		setState(json.fromJson(getReader().readLine(), ClientGameState.class));
	}
	
	public void readMessageFromServer() throws IOException, Exception {
		setMessageFromServer(json.fromJson(getReader().readLine(), Message.class));
	}
	
	public Message getMessageFromServer() {
		return this.messageFromServer;
	}
	
	public Message getMessageToServer() {
		return this.messageToServer;
	}
	
	public void handleMessageFromServer() throws Exception {
		if (getMessageFromServer().getNumber() == 0) {
			setState(json.fromJson(getMessageFromServer().getMessage(), ClientGameState.class));
		}
		if (getMessageFromServer().getNumber() == 1) {
			setGameResults(json.fromJson(getMessageFromServer().getMessage(),GameResults.class));
			System.out.println("Game results received: " + getGameResults());
		}
	}
	
	public void setGameResults(GameResults gameResults) {
		this.gameResults = gameResults;
	}
	
	public GameResults getGameResults() {
		return this.gameResults;
	}
	
	public void writeToServer(String outbound) throws IOException {
		getWriter().write(outbound);
		getWriter().newLine();
		getWriter().flush();
	}
	
	public void setWriter(BufferedWriter writer) {
		this.writer = writer;
	}
	
	public BufferedWriter getWriter() {
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
 	
	public void writeController() throws IOException {
		writeToServer(json.toJson(getController())); // There is also a Gson method to directly write to a Writer
	}

	public View getView() {
		return view;
	}

	public void setMessageFromServer(Message messageFromServer) {
		this.messageFromServer = messageFromServer;
	}
	
	public void setMessageToServer(Message messageToServer) {
		this.messageToServer = messageToServer;
	}
	
	public void setView(View view) {
		this.view = view;
	}
	
	public void updateController() {
		getController().update();
	}
	public StopWatch getTimer() {
		return timer;
	}
	public void setTimer(StopWatch timer) {
		this.timer = timer;
	}
	
	public void play() throws Exception {
		while (getSocket().isConnected() && getView().isVisible()) {
			
			getTimer().loopStart(); // Start the loop
			
			updateController(); // Update controller
			
			writeController(); // Write controller to the server
			readMessageFromServer();
			handleMessageFromServer();
			//readGameState(); // Read the game state from the server and update the current game state

			getView().reDraw(getState());// Client draws game state here!
			
			getTimer().loopRest();// Rest for the rest of the loop
		}
	}
}
