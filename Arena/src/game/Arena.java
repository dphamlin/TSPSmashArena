package game;
import java.util.*;
import java.io.*;
import java.net.*;
import java.nio.file.Paths;

import com.google.gson.*;

public class Arena {

	private static View mainView;
	private boolean host;
	private int numPlayers;
	private String ip;
	private int port;
	private Process serverProcess;
	private Client theClient;

	public static void main(String[] args){
		mainView = new View(new Arena());
	}

	public Arena(){
		serverProcess = null;
		theClient = null;
	}

	public void host(int numberOfPlayers, int port){

		String currentPath = Paths.get("").toAbsolutePath().toString();
		String[] commandArgs = {"java","-cp",currentPath + File.pathSeparator + currentPath + 
				"/lib/gson-2.2.4.jar" + File.pathSeparator + currentPath + "/bin" + File.pathSeparator + 
				currentPath + "/arena.jar","game.Server",String.valueOf(numberOfPlayers),Integer.toString(port)};

		try {
			ProcessBuilder processBuilder = new ProcessBuilder(commandArgs);
			processBuilder.redirectErrorStream(true);
			processBuilder.redirectOutput(new File("server_output.txt"));
			serverProcess = processBuilder.start();
			System.out.println("Server started!");
		}
		catch (IOException ioe) {
			System.err.println("IOException attempting to start the server.");
			System.exit(1);
		}

		if (serverProcess == null) {
			System.err.println("Failed to execute the server.");
			System.exit(1);
		}

		join("localhost", port);

		System.out.println("Terminating the server.");
		serverProcess.destroy();
		serverProcess = null;
	}

	public void join(String ip, int port){
		InetAddress serverAddr = null;
		try {
			serverAddr = InetAddress.getByName(ip);
		}
		catch (UnknownHostException uhe) {
			System.out.println("Could not resolve the server address.  Unknown host.");
		}
		catch (Exception e) {
			System.out.println("Could not aquire the server address.  Unspecified error.");
		}

		if(serverAddr != null){
			try {
				theClient = new Client(serverAddr, port, mainView);
				System.out.println("Client Started");
			}
			catch (IOException e) {
				System.err.println("Failed to create game client. " + e.getMessage());
			}
			try {
				theClient.play();
			}
			catch (Exception e) {}
			theClient = null;
		}
		//TODO: MusicBank.stop();
		mainView.gameDone();
	}

	/**
	 * @return the numPlayers
	 */
	public int getNumPlayers() {
		return numPlayers;
	}

	/**
	 * @param numPlayers the numPlayers to set
	 */
	public void setNumPlayers(int numPlayers) {
		this.numPlayers = numPlayers;
	}

	/**
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * @param ip the ip to set
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * @return the host
	 */
	public boolean isHost() {
		return host;
	}

	/**
	 * @param host the host to set
	 */
	public void setHost(boolean host) {
		this.host = host;
	}
	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @param port the port to set
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * @return the serverProcess
	 */
	public Process getServerProcess() {
		return serverProcess;
	}

	/**
	 * @return the theClient
	 */
	public Client getTheClient() {
		return theClient;
	}

}