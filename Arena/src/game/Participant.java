package game;
import java.io.*;

public abstract class Participant {

	protected Player thePlayer;
	protected String controllerString;
	protected Controller controller;
	protected BufferedReader reader;
	protected PrintWriter writer;
	
	public Player getPlayer() {
		return this.thePlayer;
	}
	
	public void setPlayer(Player player) {
		this.thePlayer = player;
	}
	
	public void setControllerString(String comm) {
		this.controllerString = comm;
	}
	
	public String getControllerString() {
		return this.controllerString;
	}

	public Controller getController() { // For a human player, the socket will be read; for AI, no socket will be involved.
		return this.controller;
	}
	
	public void setControler(Controller c){
		this.controller = c;
	}
	
	public void writeToClient(String outbound) {
		this.getWriter().println(outbound);
	}
	
	public void setWriter(PrintWriter printWriter) {
		this.writer = printWriter;
	}
	
	public PrintWriter getWriter() {
		return this.writer;
	}
	
	public void setReader(BufferedReader bufferedReader) {
		this.reader = bufferedReader;
	}
	
	public BufferedReader getReader() {
		return this.reader;
	}
	public void updateController(Controller c){
		this.controller = c;
	}
	
	public abstract void updateControllerString() throws IOException;
		// TODO Auto-generated method stub
		
	
}
