package game;
import java.io.*;
import com.google.gson.*;

public abstract class Participant {

	protected Player thePlayer;
	protected String controllerString;
	protected Controller controller;
	protected BufferedReader reader;
	protected PrintWriter writer;
	protected Gson json;
	
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

	public Controller getController() {
		return this.controller;
	}
	
	public void setController(Controller c){
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
	
	// Read controller from Reader. Reader may take from a connection or from an AI source.
	public void readController() throws IOException {
		setController(json.fromJson(getReader().readLine(), Controller.class));
	}
	
	public abstract void readControllerString() throws IOException;
}
