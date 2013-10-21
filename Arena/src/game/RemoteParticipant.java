package game;
import java.util.*;
import java.io.*;
import java.net.*;

public class RemoteParticipant extends Participant {

	private Socket socket;
	
	RemoteParticipant (Socket clientSocket) throws IOException {
		this.setPlayer(new Player());
		this.setSocket(clientSocket);
		this.setControllerString("No commands sent yet.");
		this.setReader(new BufferedReader(new InputStreamReader(getSocket().getInputStream())));
		this.setWriter(new PrintWriter(getSocket().getOutputStream(),true));
	}
	
	@Override
	public void updateControllerString() throws IOException {
		this.setControllerString(this.getReader().readLine()); // Should block until line received from client
	}
	
	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	
	public Socket getSocket() {
		return socket;
	}

}
