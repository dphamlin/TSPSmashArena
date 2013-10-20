package game;
import java.io.*;

public abstract class Participant {

	protected Player thePlayer;
	protected String controllerString;
	protected Controller controller;
	protected BufferedReader reader;
	protected PrintWriter writer;
	
	public Player getPlayer() {
		return thePlayer;
	}
	
	public void setPlayer(Player player) {
		thePlayer = player;
	}
	
	public void setControllerString(String comm) {
		controllerString = comm;
	}
	
	public String getControllerString() {
		return controllerString;
	}

	public Controller getController() { // For a human player, the socket will be read; for AI, no socket will be involved.
		return controller;
	}
	
	public void writeToClient(String outbound) {
		getWriter().println(outbound);
	}
	
	public void setWriter(PrintWriter printWriter) {
		writer = printWriter;
	}
	
	public PrintWriter getWriter() {
		return writer;
	}
	
	public void setReader(BufferedReader bufferedReader) {
		reader = bufferedReader;
	}
	
	public BufferedReader getReader() {
		return reader;
	}
	
	public abstract void updateController() throws IOException;
	public abstract void updateControllerString() throws IOException;
	
}
