package game;

public class ServerInfo { // Binds together server process with its initial information for listing purposes
	private int numberOfPlayers;
	private int port;
	private Process process;
	
	public ServerInfo(int numberOfPlayers, int port, Process process) {
		setNumberOfPlayers(numberOfPlayers);
		setPort(port);
		setProcess(process);
	}
	
	public void setNumberOfPlayers(int numberOfPlayers) {
		this.numberOfPlayers = numberOfPlayers;
	}
	
	public void setPort(int port) {
		this.port = port;
	}
	
	public void setProcess(Process process) {
		this.process = process;
	}
	
	public int getNumberOfPlayers() {
		return this.numberOfPlayers;
	}
	
	public int getPort() {
		return this.port;
	}
	
	public Process getProcess() {
		return this.process;
	}
}