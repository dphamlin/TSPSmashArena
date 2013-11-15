package game;

import java.util.*;
import java.io.*;
import java.nio.file.Paths;

public class Metaserver {
	
	private Metaserver theMetaserver; // The metaserver itself
	private int first; // The first port in the range
	private int last; // The last port in the range
	private int[] portStatusArray; // Tracks whether port is taken yet
	private ArrayList<ServerInfo> serverInfoList;
	
	
	
	public Metaserver(int first, int last) {
		setFirst(first);
		setLast(last);
		initializePortStatusArray(last-first+1);
		setServerInfoList(new ArrayList<ServerInfo>());
	}
	
	public void setServerInfoList(ArrayList<ServerInfo> serverInfoList) {
		this.serverInfoList = serverInfoList;
	}
	
	public ArrayList<ServerInfo> getServerInfoList() {
		return this.serverInfoList;
	}
	
	public void initializePortStatusArray(int length) {
		portStatusArray = new int[length]; // Default values of each element are 0
	}
	
	public void setFirst(int first) {
		this.first = first;
	}
	
	public void setLast(int last) {
		this.last = last;
	}
	
	public int[] getPortStatusArray() {
		return portStatusArray;
	}
	
	public int getFirst() {
		return first;
	}
	
	public int getLast() {
		return last;
	}
	
	public int getAvailablePort() {
		int[] portStatusArray = getPortStatusArray(); // Get reference to portArray
		for (int i=0;i<portStatusArray.length;i++) {
			if (getPortStatusArray()[i] == 0)
				return first+i; // Return the port number of the first available port found
		}
		return -1; // No available ports found; return -1 indicating error
	}
	
	public String newServer() throws Exception{
		cleanList();
		int numberOfPlayers = 4;
		BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
		
		// Get number of players for new Server
		System.out.println("Please enter the number of players (maximum 4, default 4): ");
		try {
			numberOfPlayers = Integer.parseInt(inputReader.readLine());
			if (numberOfPlayers <= 0 || numberOfPlayers > 4)  // Must be between 1 and 4
				throw new Exception("Only 1 to 4 players allowed");
		}
		catch (Exception e) {
			System.out.println("Invalid number of players: " + e.getMessage() + ".  Using default value (4 players).");
			numberOfPlayers = 4;
		}
		
		int port = getAvailablePort(); // Get available port in range
		if (port == -1) {
			throw new Exception("No ports available in range.  Please try again later.");
		}
		
		// Now spawn a server with specified number of players at specified port
		Process serverProcess = null;
		String currentPath = Paths.get("").toAbsolutePath().toString();
		String[] commandArgs = {"java","-cp",currentPath + File.pathSeparator + currentPath + 
				"/lib/gson-2.2.4.jar" + File.pathSeparator + currentPath + "/bin" + File.pathSeparator + 
				currentPath + "/metaserver.jar","game.Server",String.valueOf(numberOfPlayers),Integer.toString(port)};
		
		try {
			ProcessBuilder processBuilder = new ProcessBuilder(commandArgs);
			processBuilder.redirectErrorStream(true);
			processBuilder.redirectOutput(new File("server" + port + ".txt"));
			serverProcess = processBuilder.start();
		}
		catch (IOException ioe) {
			throw new Exception("IOException attempting to start the server.");
		}
		
		if (serverProcess == null) {
			throw new Exception("Failed to execute the server.");
		}
		
		// Add info about new process to the serverInfoList and mark port as used
		getServerInfoList().add(new ServerInfo(numberOfPlayers,port,serverProcess));
		getPortStatusArray()[port-getFirst()] = 1;
		String outputStr = numberOfPlayers + "-player server started on port " + port + ".";
		return outputStr;
	}
	
	public String listServers() {
		cleanList();
		ArrayList<ServerInfo> serverInfoList = getServerInfoList();
		String outputStr = "";
		outputStr += "List of " + serverInfoList.size() + " servers:\n";
		outputStr += "SERVER\tPORT\tPLAYERS\n";
		for (ServerInfo info: serverInfoList) {
			outputStr += (serverInfoList.indexOf(info)+1) + "\t" + info.getPort() + "\t" + info.getNumberOfPlayers() + "\n";
		}
		return outputStr;
	}
	
	public String cleanList() {
		ArrayList<ServerInfo> newServerInfoList = new ArrayList<ServerInfo>();
		
		for (ServerInfo info: getServerInfoList()) {
			try {
				info.getProcess().exitValue(); // Throws an exception only if process is still alive
				getPortStatusArray()[info.getPort()-getFirst()] = 0; // Process is dead; mark port as free
				// info.getProcess().waitFor(); // This may be necessary
			}
			catch (IllegalThreadStateException e) {
				newServerInfoList.add(info); // Only add to revised list if still alive
			} 
		}
		setServerInfoList(newServerInfoList); // Use the revised server info list
		String outputStr = "Terminated servers removed from the list.";
		return outputStr;
	}
	
	public String killAll() {
		cleanList();
		for (ServerInfo info: getServerInfoList()) {
			info.getProcess().destroy(); // TO DO: Research effects of attempting destroy on terminated process
			try {
				info.getProcess().waitFor();
			}
			catch (InterruptedException e) {
				System.err.println(e.getMessage());
			}
		}
		cleanList();
		String outputStr = "All servers terminated.";
		return outputStr;
	}
	
	// Version that prompts for server number
	public String killServer() throws Exception{
		BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Please enter the number of the server to terminate (from list)");
		int target = -1;
		try {
			target = Integer.parseInt(inputReader.readLine());
			return killServer(target);
		}
		catch (Exception e) {
			throw new Exception("Invalid server number. No action taken");
		}
	}
	
	// Kill a specific server
	public String killServer(int serverNumber) throws Exception{
		int index = serverNumber-1;
		ArrayList<ServerInfo> serverInfoList = getServerInfoList();
		if (index < 0 || index >= serverInfoList.size()) { // Index needs to be valid
			throw new Exception("Not a valid server.");
		}
		serverInfoList.get(index).getProcess().destroy();
		try {
			serverInfoList.get(index).getProcess().waitFor();
		}
		catch (InterruptedException e) {
			System.err.println(e.getMessage());
		}
		String outputStr = serverInfoList.get(index).getNumberOfPlayers() + "-player game on port " + serverInfoList.get(index).getPort() + " terminated.";
		cleanList();
		return outputStr;
	}
	
	public static void main(String[] args) {
		
		BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in)); // Read from terminal input
		String command = "";
		
		System.out.println("Welcome to the metaserver.");
		
		// Default range from 5380 to 5389
		int first = 5380;
		int last = 5389;
		
		// args[0] should be first port, args[1] should be last port
		if (args.length == 2) { // Only allow customized range if two values provided.
			// Parse as ints, check validity 
			try {
				first = Integer.parseInt(args[0]);
				last = Integer.parseInt(args[1]);
				if (first<=1000 || last <= 1000) // Don't even try to use well-known ports
					throw new Exception("Negative-numbered or well-known ports cannot be used");
				if (last < first) { // Be nice and swap out of order ports
					int tmp = last;
					last = first;
					first = tmp;
				}
			}
			catch (Exception e) {
				System.out.println("Invalid command-line arguments: " + e.getMessage() + ".  Using default port range (5380 - 5389).");
				first = 5380;
				last = 5389;
			}

		}
		
		Metaserver metaserver = new Metaserver(first, last);
		
		while (command.compareToIgnoreCase("q") != 0 && command.compareToIgnoreCase("quit") != 0) {
			System.out.println("Please enter a command:");
		
			try {
				command = inputReader.readLine();
			}
			catch(IOException e) {
				System.out.println("Error reading line: " + e.getMessage());
			}
			
			// Create new server
			if (command.compareToIgnoreCase("n") == 0 || command.compareToIgnoreCase("new") == 0) {
				try {
					System.out.println(metaserver.newServer());
				}
				catch (Exception e) {
					System.err.println(e.getMessage());
				}
			}
			// List servers
			else if (command.compareToIgnoreCase("l") == 0 || command.compareToIgnoreCase("list") == 0) {
				System.out.print(metaserver.listServers());
			}
			// Kill all
			else if (command.compareToIgnoreCase("ka") == 0 || command.compareToIgnoreCase("killall") == 0) {
				System.out.println(metaserver.killAll());
			}
			// Kill specific server 
			else if (command.compareToIgnoreCase("k") == 0 || command.compareToIgnoreCase("kill") == 0) {
				try {
					System.out.println(metaserver.killServer());
				}
				catch(Exception e) {
					System.err.println(e.getMessage());
				}
			}
			// Clean list
			else if (command.compareToIgnoreCase("c") == 0 || command.compareToIgnoreCase("clean") == 0) {
				System.out.println(metaserver.cleanList());
			}
		}
		System.out.println("Metaserver terminating.  Killing all servers.");
		System.out.println(metaserver.killAll());
		System.exit(0);
	}
}