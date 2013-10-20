package game;
import java.util.*;
import java.io.*;
import java.net.*;

public class Arena {
	public static void main(String[] args){
		
		Server theServer = null;
		Client theClient = null;
		int serverPort = 5379; // default port
		
		Thread serverThread = null;

		int choice = 0;
		Scanner inputScanner = new Scanner(System.in);

		System.out.println("Welcome.  Enter 0 if you wish to connect to loopback; otherwise, enter 1 to choose IP address and port.\n");
		choice = inputScanner.nextInt();
		
		/*
		if (choice == 0) { // Elected to host a game
			try {
				theServer = new Server(serverPort);
			}
			catch (Exception e) {
				System.err.println("Failed to create game server. Exiting.");
				System.exit(1);
			}
			
			int numberOfPlayers = 0;
			while (numberOfPlayers < 1) {
				System.out.println("Please enter the number of players in the game:");
				numberOfPlayers = inputScanner.nextInt();
			}
			
			theServer.setNumberOfPlayers(numberOfPlayers);
			serverThread = new Thread(theServer); // Need a proper synchronized approach here
			serverThread.start();
		}
		*/
		
		/* Test code 
		while (serverThread.isAlive()){
			try {
				Thread.sleep(10);
			}
			catch (Exception e) {
				System.err.println("Can't sleep. Clown will eat me.");
			}
			System.out.println("Main Arena thread.");
		}
		*/
		// The client section begins here

		InetAddress serverAddr = InetAddress.getLoopbackAddress(); // default IP address
		serverPort = 5379; // default port
	
		try {
			if (choice == 0)  // If host, no prompt; just connect to loopback
				;
			else { // Otherwise, prompt for IP address and port, interpret, then connect
				inputScanner = new Scanner(System.in);
				System.out.println("Please enter the IP address of the server (in the typical format):");
				String addrString = inputScanner.nextLine();
				serverAddr = InetAddress.getByName(addrString);
				System.out.println("Please enter the port number at which the server is listening:");
				serverPort = inputScanner.nextInt();
			}
			theClient = new Client(serverAddr,serverPort);
		}
		catch (UnknownHostException uhe) {
			System.out.println("Could not connect to server.  Unknown host.");
			System.exit(1);
		}
		catch (Exception e) {
			System.out.println("Could not connect to server.  Unspecified error.");
			System.exit(1);
		}
			
		// Client should be connected; begin communication cycle
		while (!theClient.getSocket().isClosed()) {
			
			inputScanner = new Scanner(System.in);
			
			System.out.println("Enter a line to send to the server.");
			String nextLine = inputScanner.nextLine();
			theClient.writeToServer(nextLine);
			
			try {
				theClient.updateStateString(); // Have the client read the response from the server.
			}
			catch (Exception e) {
				System.err.println("Failed to update state string from server.");
				theClient.setStateString("Nothing received.");
			}
			System.out.println("The server returned the following state:");
			System.out.println(theClient.getStateString());
			
		}
		System.out.println("Thanks for playing.  Cheers.");
		System.exit(0);
	}
}