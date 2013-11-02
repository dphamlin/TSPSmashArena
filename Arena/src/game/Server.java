package game;

import java.util.*;
import java.io.*;
import java.net.*;

import com.google.gson.*;

public class Server {
	
	private ServerSocket serverSocket;
	private ArrayList<Participant> participantList;
	private int numberOfPlayers = 1;
	// private String stateString = null;
	private ServerGameState game;
	private Gson json;
	private StopWatch timer;
	private static int port = 5379;
	private int activePlayerCount = 0;
	
	Server() throws IOException {
		serverSocket = new ServerSocket(port);
		participantList = new ArrayList<Participant>();
		game = new ServerGameState();
		json = new Gson();
		timer = new StopWatch(20);
		System.out.println("Server started!"/*+Inet4Address.getLocalHost()*/);
	}

	Server(int port) throws IOException {
		serverSocket = new ServerSocket(port);
		participantList = new ArrayList<Participant>();
		game = new ServerGameState();
		json = new Gson();
		timer = new StopWatch(20);
	}
	
	public void setNumberOfPlayers(int numberOfPlayers) { // Proper functioning only guaranteed for >=1 value.
		this.numberOfPlayers = (numberOfPlayers >= 1)? numberOfPlayers : 1;
	}
	
	public int getNumberOfPlayers() {
		return numberOfPlayers;
	}
	
	public ServerSocket getServerSocket() {
		return serverSocket;
	}
	
	/*
	public void setStateString(String newStateString) {
		stateString = newStateString;
	}
	*/
	/*
	public String getStateString() {
		return stateString;
	}
	*/
	public ArrayList<Participant> getParticipantList() {
		return participantList;
	}
	
	public void setParticipantList(ArrayList<Participant> newParticipantList) {
		participantList = newParticipantList;
	}
	
	public ServerGameState getGameState() {
		return game;
	}
	
	public StopWatch getTimer() {
		return timer;
	}

	public void setTimer(StopWatch timer) {
		this.timer = timer;
	}
	
	// Reads new Controller objects from all participants in given list
	public void readControllersFromAll(ArrayList<Participant> aParticipantList) {
		for (Participant p: aParticipantList) {
			if (p.isActive()) // Only try to read from active players; thread will be responsible for changing back to active on reconnect
				try {
					p.readController();
				}
				catch (IOException e) {
					System.err.println("Participant disconnected on reading controller. Set to inactive. " + e.getMessage());
					p.setActive(false);
					setActivePlayerCount(getActivePlayerCount()-1);
				}
		}
	}
	
	// Writes the current game state to all clients as a JSON string
	public void writeGameStateToAll(ArrayList<Participant> aParticipantList) { 
		System.out.println(json.toJson(getGameState().convert()));
		for (Participant p: aParticipantList) {
			if (p.isActive()) // Only try to write to active players; thread will be responsible for changing back to active on reconnect
				try {
					p.writeToClient(json.toJson(getGameState().convert()));
				}
				catch (IOException e) {
					System.out.println("Participant disconnected while writing game state. Set to inactive. " + e.getMessage());
					p.setActive(false);
					setActivePlayerCount(getActivePlayerCount()-1);
				}
		}
	}

	// Generate a list of participants from network connection
	public ArrayList<Participant> connectParticipants(int num) throws IOException {
		Socket s = null;
		ArrayList<Participant> newParticipantList = new ArrayList<Participant>();
		
		// Try to accept a connection; if successful, add a Participant with that connected socket
		for (int i=0;i<num;i++) { 
			try {
				s = getServerSocket().accept();
			}
			catch (IOException e) {
				s = null;
			}
			if (s != null) {
				RemoteParticipant p = new RemoteParticipant(s);
				p.setPlayer(getGameState().addPlayer());
				newParticipantList.add(p);
				System.out.println("Player: "+(i+1)+" connected.");
				setActivePlayerCount(getActivePlayerCount() + 1);
			}
		}
		setNumberOfPlayers(newParticipantList.size());
		getServerSocket().close(); // Stop accepting connections
		return newParticipantList;
	}
	
	public int getActivePlayerCount() {
		return activePlayerCount;
	}
	
	public void setActivePlayerCount(int activePlayerCount) {
		this.activePlayerCount = activePlayerCount;
	}
	
	public void applyAllControls(ArrayList<Participant> aParticipantList) {
		for (Participant p: aParticipantList) {
			if (p.isActive())
				getGameState().readControls(p);
			else
				getGameState().suspendPlayer(p);
		}
	}
		
	public static void main(String []args) {
		
		Server theServer = null;
		try {
			theServer = new Server();
		}
		catch (Exception e) {
			System.out.println("Could not start the server.");
			System.exit(1);
		}
		
		int numberOfPlayers = 2;
		if (args.length > 0)
			numberOfPlayers = Integer.parseInt(args[0]); // args[0] is not just program name in Java
		else {
			System.out.println("Please enter the number of players:");
			Scanner inputScanner = new Scanner(System.in);
			numberOfPlayers = inputScanner.nextInt();
		} 
		
		theServer.setNumberOfPlayers(numberOfPlayers);
		
		// Connect clients and adds them to the participantList
		try {
			theServer.setParticipantList(theServer.connectParticipants(theServer.getNumberOfPlayers()));
		}
		catch (Exception e) {
			System.err.println("Error connecting client to server.\n");
		}
		
		// All participants should connected; begin communication cycle
		while (theServer.getActivePlayerCount() > 0) {
			
			theServer.getTimer().loopStart(); //log start time
			
			theServer.readControllersFromAll(theServer.getParticipantList()); // Reads updated controllers into all participants

			// Controllers now ready for application to game state
			// HERE GAME LOGIC SHOULD BE UPDATED USING THE CONTROLLERS
			// My stab at it:
			theServer.applyAllControls(theServer.getParticipantList()); // Applies controls for all participants
			theServer.getGameState().update(); // updates game state using game logic
	
			// GameState is updated by this point; send it to all
			theServer.writeGameStateToAll(theServer.getParticipantList());
			
			theServer.getTimer().loopRest(); //rest until loop end	
			
		}
		System.out.println("All clients disconnected.  Server going offline.");
		System.exit(0);
		
	}
}
