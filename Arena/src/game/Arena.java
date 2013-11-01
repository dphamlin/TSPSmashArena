package game;
import java.util.*;
import java.io.*;
import java.net.*;
import java.nio.file.Paths;

import com.google.gson.*;

public class Arena {
	
	private static int port = 5379;
	
	public static void main(String[] args){
		
		String selection = "0";
		int choice = -1;
		Scanner inputScanner = new Scanner(System.in);

		System.out.println("Welcome. " +
				"Enter 0 if you wish to host a game; otherwise, enter the IP address of the game you wish to join.\n");
		selection = inputScanner.nextLine();
		try {
			choice = Integer.parseInt(selection);
		}
		catch (NumberFormatException nfe) {
			choice = -1;
		}
		
		Process serverProcess = null;
		if (choice == 0) { // Host and connect on loopback

			inputScanner = new Scanner(System.in); // Crudely reset scanner
			
			int numberOfPlayers = 0;
			while (numberOfPlayers < 1 || numberOfPlayers > 4) {
				System.out.println("Please enter the number of players (maximum 4) in the game:");
				numberOfPlayers = inputScanner.nextInt();
			}
			
			String currentPath = Paths.get("").toAbsolutePath().toString();
			String[] commandArgs = {"java","-cp",currentPath + File.pathSeparator + currentPath + "/lib/gson-2.2.4.jar" + File.pathSeparator + currentPath + "/bin","game.Server",String.valueOf(numberOfPlayers)};
			
			try {
				ProcessBuilder processBuilder = new ProcessBuilder(commandArgs);
				processBuilder.redirectErrorStream(true);
				processBuilder.redirectOutput(new File("server_output.txt"));
				serverProcess = processBuilder.start();
			}
			catch (IOException ioe) {
				System.err.println("IOException attempting to start the server.");
				System.exit(1);
			}
			
			if (serverProcess == null) {
				System.err.println("Failed to execute the server.");
				System.exit(1);
			}
		}
		inputScanner.close();
		
		InetAddress serverAddr = InetAddress.getLoopbackAddress(); // default IP address
		int serverPort = port;
	
		Client theClient = null;
		if (choice != 0) {
			try {
				serverAddr = InetAddress.getByName(selection);
			}
			catch (UnknownHostException uhe) {
			System.out.println("Could not connect to server.  Unknown host.");
			System.exit(1);
			}
			catch (Exception e) {
			System.out.println("Could not connect to server.  Unspecified error.");
			System.exit(1);
			}
		}

		try {
			theClient = new Client(serverAddr,serverPort);
		}
		catch (IOException e) {
			System.err.println("Failed to create game client. " + e.getMessage());
			System.exit(1);
		}
		
		
		// Client should be connected; begin communication cycle. Consider reordering or adding initial send/receives
		while (theClient.getSocket().isConnected()) {
			
			theClient.getTimer().loopStart(); // Start the loop
			
			theClient.updateController(); // Update controller
			
			theClient.writeController(); // Write controller to the server
			
			try {
				theClient.readGameState(); // Read the game state from the server and update the current game state
			}
			catch (Exception e) {
				// System.err.println("Failed to receive the game state from the server.");
				System.out.println("Game over. Thanks for playing!");
				theClient.getView().setVisible(false);
				System.exit(0);
			}
			
			theClient.getView().reDraw(theClient.getState());// Client draws game state here!
			
			theClient.getTimer().loopRest();// Rest for the rest of the loop
		}
		
		if (serverProcess != null) {
			try {
				serverProcess.waitFor();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			
		System.out.println("Game over. Thanks for playing!");
		System.exit(0); //super quit
	}
}