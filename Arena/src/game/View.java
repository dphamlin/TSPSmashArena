package game;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
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

/**
 * Class for the GUI
 * 
 * @author Jacob Charles, Dean Hamlin, James Hannah
 *
 */

public class View extends JFrame {

	private JPanel draw;
	private JPanel cardPane;
	private JTabbedPane modeTabbedPane;
	private JPanel host;
	private JPanel join;
	private JPanel result;
	private JButton jGo;
	private JButton hGo;
	private JLabel ipLabel;
	private JLabel yourIP;
	private JTextField ipField;
	private JLabel numPlayersLabel;
	private JTextField numPlayersField;
	private Arena arena;
	private CardLayout cl;
	private ControlListener control;
	
	/**
	 * Standard constructor
	 */
	public View(Arena a) {
		super();
		setTitle("TSPArena");
		draw = new JPanel();
		draw.setPreferredSize(new Dimension(640, 480));
		this.setSize(640, 480);	
		
		arena = a;
		control = new ControlListener(this);
		this.addKeyListener(control);
	    modeTabbedPane = new JTabbedPane();
		jGo = new JButton("Go!");
		hGo = new JButton("Go!");
		jGo.addActionListener(control);
		hGo.addActionListener(control);
		GridLayout hostGrid = new GridLayout(4,1);
		GridLayout joinGrid = new GridLayout(4,1);
		
		host = new JPanel();
		host.setPreferredSize(new Dimension(260,120));
		host.setLayout(hostGrid);
		yourIP = new JLabel("Your IP is: "+getMyIp());
		numPlayersLabel = new JLabel("Number of players (1-4) :");
		numPlayersField = new JTextField();
		host.add(yourIP);
		host.add(numPlayersLabel);
		host.add(numPlayersField);
		host.add(hGo);
		
		join = new JPanel();
		join.setPreferredSize(new Dimension(260,120));
		join.setLayout(joinGrid);
		ipLabel = new JLabel("Enter an IP to connect to :");
		ipField = new JTextField();
		join.add(new JLabel("Welcome to TSP-Arena!"));
		join.add(ipLabel);
		join.add(ipField);
		join.add(jGo);
		
		modeTabbedPane.addTab("Join", join);
		modeTabbedPane.addTab("Host", host);
		
		cl = new CardLayout();
		cardPane = new JPanel(cl);
		cardPane.add(modeTabbedPane, "mode");
		cardPane.add(draw, "draw");
		this.add(cardPane);
		this.setTitle("Arena: Loby");
		cl.show(cardPane, "mode");
		
		setResizable(false);
		setVisible(true);
		pack(); //FORCE it to be 640 x 480, this has given me grief
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE); // Closing the window closes the game
		toFront();
	}

	public String getMyIp(){
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
								return inet;
							}
						}
					}
				} catch (SocketException e) {
					System.err.println("Unable to get local address server is utilizing.");
				}
			}
		}
		return "Unknown.Address";
	}

	/**
	 * Connects a controller to the screen
	 * 
	 * @param c
	 * 		the controller object to be connected
	 */
	public void attachController(Controller c) {
		draw.getGraphics().drawString("Waiting for game to start...", 340-75, 240+5); //pre-join text
		control.setContoller(c);
	}

	/**
	 * Draw a game state (double buffered)
	 * 
	 * @param state
	 * 		game state to draw
	 */
	public void reDraw(ClientGameState state){
		if (!this.getTitle().equals("TSPArena: "+state.getMapName())) {
			this.setTitle("TSPArena: "+state.getMapName());
		}
		Image backBuffer = createImage(640, 480);
		state.draw(backBuffer.getGraphics());
		draw.getGraphics().drawImage(backBuffer, 0, 0, null);
	}
	
	public void gameDone(){
		cl.show(cardPane, "mode");
	}
	
	/**
	 * @return the cardPane
	 */
	public JPanel getCardPane() {
		return cardPane;
	}

	/**
	 * @param cardPane the cardPane to set
	 */
	public void setCardPane(JPanel cardPane) {
		this.cardPane = cardPane;
	}

	/**
	 * @return the ipField
	 */
	public JTextField getIpField() {
		return ipField;
	}

	/**
	 * @param ipField the ipField to set
	 */
	public void setIpField(JTextField ipField) {
		this.ipField = ipField;
	}

	/**
	 * @return the numPlayersField
	 */
	public JTextField getNumPlayersField() {
		return numPlayersField;
	}

	/**
	 * @param numPlayersField the numPlayersField to set
	 */
	public void setNumPlayersField(JTextField numPlayersField) {
		this.numPlayersField = numPlayersField;
	}

	/**
	 * @return the jGo
	 */
	public JButton getjGo() {
		return jGo;
	}

	/**
	 * @param jGo the jGo to set
	 */
	public void setjGo(JButton jGo) {
		this.jGo = jGo;
	}

	/**
	 * @return the hGo
	 */
	public JButton gethGo() {
		return hGo;
	}

	/**
	 * @param hGo the hGo to set
	 */
	public void sethGo(JButton hGo) {
		this.hGo = hGo;
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
	 * @param cl the cl to set
	 */
	public void setCl(CardLayout cl) {
		this.cl = cl;
	}
	
	/**
	 * @return the draw
	 */
	public JPanel getDraw() {
		return draw;
	}

	/**
	 * @param draw the draw to set
	 */
	public void setDraw(JPanel draw) {
		this.draw = draw;
	}
	
}

