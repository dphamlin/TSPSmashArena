package game;

import java.util.*;
import java.io.*;
import java.net.*;

public class Server /* implements Runnable */ {
	
	
	private ServerSocket serverSocket;
	private ArrayList<Participant> participantList;
	private int numberOfPlayers = 0;
	private String stateString = null;
	
	Server() throws IOException {
		serverSocket = new ServerSocket(5379);
		participantList = new ArrayList<Participant>();
	}
	
	Server(int port) throws IOException {
		serverSocket = new ServerSocket(port);
		participantList = new ArrayList<Participant>();
	}
	
	public void setNumberOfPlayers(int numberOfPlayers) { // Proper functioning only guaranteed for >=1 value.
		this.numberOfPlayers = numberOfPlayers;
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
		for (int i=0;i<10;i++) { // A round of ten exchanges for testing purposes
			
			for (Participant p: participantList) {
				try {
					p.updateControllerString();
				}
				catch (Exception e) {
					System.err.println("Could not update controller string in a participant.");
				}
			}
			
			// All controller strings should be updated; create updated state string from them
			String newStateString = "";
			for (int j=1;j<=participantList.size();j++) {
				newStateString = newStateString + "Player " + j + " sent: " + participantList.get(j-1).getControllerString() + ", ";
			}
			setStateString(newStateString);
			
			// State string is ready; send to all participants
			for (Participant p: participantList) {
				p.writeToClient(getStateString());
			}
		}
		
		// Done with test round; stop thread
		System.out.println("Done with test round; server going offline.");
		// Thread.currentThread().stop(); // Deprecated, bad, sinful, etc., but useful for testing purposes at the moment
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
		/*
		ServerSocket serv = null;
		try {
			serv = new ServerSocket(5379);
			Connection con = new Connection(serv);
			System.out.println("What to send?");
			Scanner input = new Scanner(System.in);
			String in = " ";
			while(in.compareTo("close\n") != 0){
				in = input.nextLine();
				in = in + "\n";
				con.send(in);
				System.out.println("     "+con.recieve());
			}
			con.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
	}
}
