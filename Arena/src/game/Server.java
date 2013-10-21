package game;

import java.util.*;
import java.io.*;
import java.net.*;
import com.google.gson.*;

public class Server {
	
	private Gson jsonGen;
	private ServerSocket serverSocket;
	private ArrayList<Participant> participantList;
	private int numberOfPlayers = 0;
	private String stateString = null;
	private GameState game;
	
	Server() throws IOException {
		serverSocket = new ServerSocket(5379);
		participantList = new ArrayList<Participant>();
	}
	
	Server(int port) throws IOException {
		serverSocket = new ServerSocket(port);
		participantList = new ArrayList<Participant>();
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
	
	public void setStateString(String newStateString) {
		stateString = newStateString;
	}
	
	public String getStateString() {
		return stateString;
	}
	
	public void run(int numberOfPlayers) {
		setNumberOfPlayers(numberOfPlayers);
		run();
	}
	
	public void run() {
		
		/* Test code
		for (int i=0;i<100;i++) {
			System.out.println(i + " Server: in its own thread!");
			try {
				Thread.sleep(15);
			}
			catch (Exception e) {
				System.out.println("Can't sleep. Clown will eat me.");
			}
		}
		Thread.currentThread().stop(); // Deprecated and bad, but I need results now
		*/
		jsonGen = new Gson();
		Scanner inputScanner = new Scanner(System.in);
		// Connect clients and adds them to the clientList
		Socket s = null;
		for (int i=0;i<getNumberOfPlayers();i++) { 
			try {
				s = getServerSocket().accept();
			}
			catch (Exception e) {
				System.err.println("Error connecting client to server. Exiting.\n");
				s = null;
			}
			
			if (s != null) {
				try {
					participantList.add(new RemoteParticipant(s));
				}
				catch(Exception e) {
					System.err.println("Failed to create a remote participant.");
				}
			}	
		}
		
		// All participants should connected; begin communication cycle
		for (int i=0;i<100;i++) { // A round of One Hundred exchanges for testing purposes
			
			for (Participant p: participantList) {
				try {
					p.updateControllerString();
					//when controller objs are sent.
					//p.updateController(jsonGen.fromJson(p.getControllerString(), Controller.class));
				}
				catch (Exception e) {
					System.err.println("Could not update controller string in a participant.");
				}
			}
			
			// All controller strings should be updated; create updated state string from them
			String newStateString = "";
			for (int j=1;j<=participantList.size();j++) {
				Message response = jsonGen.fromJson(participantList.get(j-1).getControllerString(), Message.class);
				newStateString = newStateString + "Player " + j + " sent: "+ "#:"+response.getNumber()+": "+response.getMessage() + ", ";
			}
			System.out.println(newStateString);
			System.out.println("Enter a line to send to the clients.");
			String nextLine = inputScanner.nextLine();
			
			//setStateString(jsonGen.toJson(game/*GameState*/));
			Message theMessage = new Message(nextLine+newStateString, i);
			setStateString(jsonGen.toJson(theMessage));
			
			
			// State string is ready; send to all participants
			for (Participant p: participantList) {
				p.writeToClient(getStateString());
			}
		}
		
		// Done with test round; stop thread
		System.out.println("Done with test round; server going offline.");
	}
	
	public static void main(String []args) {

		// Couldn't get the thread attempt to work, so hacked together this stand-alone approach
		
		Server thisServer = null;
		Scanner inputScanner = new Scanner(System.in);
		
		try {
			thisServer = new Server();
		}
		catch (Exception e) {
			System.out.println("Could not start the server.");
			System.exit(1);
		}
		
		if (thisServer != null) {
			System.out.println("Please enter the number of players:");
			thisServer.setNumberOfPlayers(inputScanner.nextInt());
			
			thisServer.run();
		}
		else
			System.exit(1);
	}
}
