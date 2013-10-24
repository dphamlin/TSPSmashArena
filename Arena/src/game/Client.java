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
	private Controller controller;
	private View view;
	private StopWatch timer;
	
	Client (InetAddress addr, int port) throws IOException { 
		setSocket(new Socket(addr,port)); // Establish connection
		setStateString("Awaiting state from server.\n");
		setReader(new BufferedReader(new InputStreamReader(getSocket().getInputStream())));
		setWriter(new PrintWriter(getSocket().getOutputStream(),true));
		setController(new Controller());
		setView(new View());
		setTimer(new StopWatch(20));
		view.attachController(controller);
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
	
	public ClientGameState getState(){
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

	public View getView() {
		return view;
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
	
	// Imitate Arena test code but with Controllers.
	public static void main (String []args) {
		
		Client theClient = null;
		
		Scanner inputScanner = new Scanner(System.in);
		int choice = 0;
		System.out.println("Welcome.  Enter 0 if you wish to connect to loopback; otherwise, enter 1 to choose IP address and port.\n");
		choice = inputScanner.nextInt();
		
		InetAddress serverAddr = InetAddress.getLoopbackAddress(); // default IP address
		int serverPort = 5379; // default port
	
		try {
			if (choice != 0){ // Otherwise, prompt for IP address and port, interpret, then connect
				inputScanner = new Scanner(System.in);
				System.out.println("Please enter the IP address of the server (in the typical format):");
				String addrString = inputScanner.nextLine();
				serverAddr = InetAddress.getByName(addrString);
				//System.out.println("Please enter the port number at which the server is listening:");
				//serverPort = inputScanner.nextInt();
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
		
		// Client should be connected; begin communication cycle. Consider reordering or adding initial send/receives
		while (!theClient.getSocket().isClosed()) {
			
			theClient.getTimer().loopStart(); // Start the loop
			
			theClient.updateController(); // Update controller
			
			theClient.writeController(); // Write controller to the server
			
			try {
				theClient.readGameState(); // Read the game state from the server and update the current game state
			}
			catch (Exception e) {
				System.err.println("Failed to receive the game state from the server.");
			}
			
			theClient.getView().reDraw(theClient.getState());// Client draws game state here!
			
			theClient.getTimer().loopRest();// Rest for the rest of the loop
			
		}
		System.out.println("Game over. Thanks for playing!");
		System.exit(0); //super quit
	}
}
