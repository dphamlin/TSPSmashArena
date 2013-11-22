package game;

public class ServerInfo { // Binds together server process with its initial information for listing purposes
	private int numberOfPlayers;
	private int port;
	private int gameNumber;
	private Process process;
	
	public ServerInfo(int numberOfPlayers, int port, int gameNumber, Process process) {
		setNumberOfPlayers(numberOfPlayers);
		setPort(port);
		setProcess(process);
		setGameNumber(gameNumber);
	}
	
	public int getGameNumber() {
		return gameNumber;
	}

	public void setGameNumber(int gameNumber) {
		this.gameNumber = gameNumber;
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