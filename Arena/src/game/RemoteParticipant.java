package game;
import java.util.*;
import java.io.*;
import java.net.*;

public class RemoteParticipant extends Participant {

	private Socket socket;
	
	RemoteParticipant (Socket clientSocket) throws IOException {
		setPlayer(new Player());
		setSocket(clientSocket);
		setControllerString("No commands sent yet.");
		setReader(new BufferedReader(new InputStreamReader(getSocket().getInputStream())));
		setWriter(new PrintWriter(getSocket().getOutputStream(),true));
	}
	
	@Override
	public void updateControllerString() throws IOException {
		setControllerString(getReader().readLine()); // Should block until line received from client
	}
	
	@Override
	public void updateController() {
		return;
	}
	
	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	
	public Socket getSocket() {
		return socket;
	}
}
