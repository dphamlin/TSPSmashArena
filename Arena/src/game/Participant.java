package game;
import java.io.*;
import com.google.gson.*;

public abstract class Participant {

	protected Actor thePlayer;
	protected String controllerString;
	protected Controller controller;
	protected BufferedReader reader;
	protected BufferedWriter writer;
	protected Gson json;
	protected Boolean active;
	
	public Actor getPlayer() {
		return this.thePlayer;
	}
	
	public void setPlayer(Actor player) {
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
	
	public void writeToClient(String outbound) throws IOException {
		this.getWriter().write(outbound);
		this.getWriter().newLine();
		this.getWriter().flush();
	}
	
	public void setWriter(BufferedWriter writer) {
		this.writer = writer;
	}
	
	public BufferedWriter getWriter() {
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
	
	public void setActive(Boolean bool) {
		active = bool;
	}
	
	public Boolean isActive() {
		return active;
	}
	
	public abstract void readControllerString() throws IOException;
}
