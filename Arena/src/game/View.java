package game;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.Enumeration;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Class for the GUI
 * 
 * @author James Hannah, Jacob Charles, Dean Hamlin
 *
 */

public class View extends JFrame {

	private JPanel draw;
	private JPanel cardPane;
	private JTabbedPane modeTabbedPane;
	private JPanel host;
	private JPanel join;
	private JPanel result;
	private JPanel spectate;
	private JButton jGo;
	private JButton hGo;
	private JLabel ipLabel;
	private JLabel yourIP;
	private JTextField ipField;
	private JTextField hostPlayerField;
	private JTextField joinPlayerField;
	private JTextField hostPortField;
	private JTextField joinPortField;
	private JLabel hostPlayerLabel;
	private JLabel joinPlayerLabel;
	private JLabel hostPortLabel;
	private JLabel joinPortLabel;
	private JLabel numPlayersLabel;
	private JLabel welcomeLabel;
	private JTextField numPlayersField;
	private Arena arena;
	private CardLayout cl;
	private ControlListener control;
	private String ip;

	/**
	 * Standard constructor
	 */
	public View(Arena a) {
		super();
		draw = new JPanel();
		draw.setPreferredSize(new Dimension(640, 480));
		this.setSize(640, 480);

		arena = a;
		control = new ControlListener(this);
		modeTabbedPane = new JTabbedPane();
		jGo = new JButton("Join!");
		hGo = new JButton("Host!");
		jGo.addActionListener(control);
		hGo.addActionListener(control);
		
		GridBagLayout hostGrid = new GridBagLayout();
		GridBagLayout joinGrid = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();

		c.gridwidth = 3;
		c.gridheight = 1;
		c.gridy = 1;
		c.gridx = 1;
		c.insets = new Insets(20,0,20,0);
		host = new JPanel();
		host.setLayout(hostGrid);
		this.ip = null;
		yourIP = new JLabel("Your IP is: "+getMyIP());
		numPlayersLabel = new JLabel("Enter the number of players 1-4: ");
		hostPlayerLabel = new JLabel("Enter your player name:");
		hostPortLabel = new JLabel("Port to listen on > 1024: ");
		numPlayersField = new JTextField();
		hostPlayerField = new JTextField();
		hostPortField = new JTextField();
		hostPortField.setText("5379");
		numPlayersField.setPreferredSize(new Dimension(25,25));
		hostPortField.setPreferredSize(new Dimension(43,25));
		hostPlayerField.setPreferredSize(new Dimension(75,25));
		
		host.add(yourIP,c);
		c.gridy = 2;
		c.gridwidth = 2;
		host.add(hostPlayerLabel,c);
		c.gridwidth = 1;
		c.gridx = 3;
		host.add(hostPlayerField,c);
		c.gridwidth = 2;
		c.gridx = 1;
		c.gridy = 3;
		host.add(hostPortLabel,c);
		c.gridwidth = 1;
		c.gridx = 3;
		host.add(hostPortField,c);
		c.gridwidth = 2;
		c.gridx = 1;
		c.gridy = 4;
		host.add(numPlayersLabel,c);
		c.gridwidth = 1;
		c.gridx = 3;
		host.add(numPlayersField,c);
		c.gridwidth = 2;
		c.gridy = 5;
		c.gridx = 2;
		host.add(hGo,c);

		c.gridwidth = 3;
		c.gridy = 1;
		c.gridx = 1;
		join = new JPanel();
		join.setLayout(joinGrid);
		welcomeLabel = new JLabel("Welcome to TSP-Arena!");
		ipLabel = new JLabel("IP/Hostname to connect to: ");
		joinPortLabel = new JLabel("Port to connect to > 1024: ");
		joinPlayerLabel = new JLabel("Enter your player name: ");
		ipField = new JTextField();
		joinPortField = new JTextField();
		joinPlayerField = new JTextField();
		joinPortField.setText("5379");
		ipField.setPreferredSize(new Dimension(125,25));
		joinPortField.setPreferredSize(new Dimension(43,25));
		joinPlayerField.setPreferredSize(new Dimension(75,25));
		
		join.add(welcomeLabel,c);
		c.gridwidth = 2;
		c.gridy = 2;
		join.add(joinPlayerLabel,c);
		c.gridwidth = 1;
		c.gridx = 3;
		join.add(joinPlayerField,c);
		c.gridwidth = 2;
		c.gridx = 1;
		c.gridy = 3;
		join.add(ipLabel,c);
		c.gridwidth = 1;
		c.gridx = 3;
		join.add(ipField,c);
		c.gridwidth = 2;
		c.gridx = 1;
		c.gridy = 4;
		join.add(joinPortLabel,c);
		c.gridwidth = 1;
		c.gridx = 3;
		join.add(joinPortField,c);
		c.gridwidth = 2;
		c.gridy = 5;
		c.gridx = 2;
		join.add(jGo, c);
		
		modeTabbedPane.addTab("Join", join);
		modeTabbedPane.addTab("Host", host);

		cl = new CardLayout();
		cardPane = new JPanel(cl);
		cardPane.add(modeTabbedPane, "mode");
		cardPane.add(draw, "draw");
		this.add(cardPane);
		this.setTitle("Arena: Lobby");
		cl.show(cardPane, "mode");

		setResizable(false);
		setVisible(true);
		pack();
		toFront();

		Wardrobe.init();//Load images and sounds
		SoundBank.init();

		this.addWindowListener(new WindowAdapter() {// Closing the window gracefully closes the game
			public void windowClosing(WindowEvent e) {
				if(getArena().getTheClient() != null){//Close the socket and catch output.
					System.out.println("Closing connection.");
					try {getArena().getTheClient().getSocket().close();}
					catch (IOException e1) {}
				}
				if(getArena().getServerProcess() != null){//Destroy server if it exists.
					System.out.println("Terminating the server.");
					getArena().getServerProcess().destroy();
				}
				System.exit(0);//Close the program.
			}
		});
	}

	/**
	 * Finds the IP of the computer running arena.
	 * @return String the IP
	 */
	public String getMyIP(){
		if(ip == null){
			Enumeration<NetworkInterface> nets = null;
			try {
				nets = NetworkInterface.getNetworkInterfaces();//Get list of all network interfaces
			} catch (SocketException e) {
				nets = null;
			}
			if(nets != null){
				for (NetworkInterface netint : Collections.list(nets)){
					try {//Find the interface that is active and host is communicating on
						if(netint.isUp() && !netint.isPointToPoint() && !netint.isVirtual() && !netint.isLoopback()){
							Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
							for (InetAddress inetAddress : Collections.list(inetAddresses)) {
								String inet = inetAddress.toString();
								inet = inet.substring(1);//Find the address on the interface we want that is "real" and IPv4.
								if(inet.substring(0,7).compareTo("169.254") != 0 && !inet.contains(":")){
									this.ip = inet;
								}
							}
						}
					} catch (SocketException e) {
						System.err.println("Unable to get local address server is utilizing.");
					}
				}
			}
			else{
				this.ip = "Unknown.Address";
			}
		}
		return this.ip;
	}

	/**
	 * Connects a controller to the screen
	 * 
	 * @param c
	 * 		the controller object to be connected
	 */
	public void attachController(Controller c) {
		String greet = "            Waiting for game to start...";
		if(getArena().isHost()){
			greet = "Waiting for players... Your IP: "+getMyIP();
		}
		draw.getGraphics().drawString(greet, 180, 245); //pre-join text
		control.setContoller(c);
	}

	/**
	 * Draw a game state (double buffered)
	 * 
	 * @param state
	 * 		game state to draw
	 */
	public void reDraw(ClientGameState state){
		if (!this.getTitle().equals("Arena: "+state.getMapName())) {
			this.setTitle("Arena: "+state.getMapName());
		}
		Image backBuffer = createImage(640, 480);
		state.draw(backBuffer.getGraphics());
		draw.getGraphics().drawImage(backBuffer, 0, 0, null);
	}

	/**
	 * Reset the view after a game has been completed.
	 */
	public void gameDone(){
		this.setTitle("Arena: Lobby");
		cl.show(cardPane, "mode");
	}
	
	/**
	 * @return the hostPlayerField
	 */
	public JTextField getHostPlayerField() {
		return hostPlayerField;
	}

	/**
	 * @return the joinPlayerField
	 */
	public JTextField getJoinPlayerField() {
		return joinPlayerField;
	}

	/**
	 * @return the hostPortField
	 */
	public JTextField getHostPortField() {
		return hostPortField;
	}

	/**
	 * @return the joinPortField
	 */
	public JTextField getJoinPortField() {
		return joinPortField;
	}

	/**
	 * @return the cardPane
	 */
	public JPanel getCardPane() {
		return cardPane;
	}

	/**
	 * @return the ipField
	 */
	public JTextField getIpField() {
		return ipField;
	}
	
	/**
	 * @return the numPlayersField
	 */
	public JTextField getNumPlayersField() {
		return numPlayersField;
	}

	/**
	 * @return the jGo
	 */
	public JButton getjGo() {
		return jGo;
	}

	/**
	 * @return the hGo
	 */
	public JButton gethGo() {
		return hGo;
	}

	/**
	 * @return the arena
	 */
	public Arena getArena() {
		return arena;
	}

	/**
	 * @param arena the arena to set
	 */
	public void setArena(Arena arena) {
		this.arena = arena;
	}

	/**
	 * @return the cl
	 */
	public CardLayout getCl() {
		return cl;
	}

	/**
	 * @return the draw
	 */
	public JPanel getDraw() {
		return draw;
	}

}

