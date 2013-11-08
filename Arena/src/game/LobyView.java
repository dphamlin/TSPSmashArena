package game;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.*;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.Enumeration;

import javax.swing.*;

public class LobyView extends JFrame implements ActionListener{

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
	
	public LobyView(Arena a){
		arena = a;
	    modeTabbedPane = new JTabbedPane();
		jGo = new JButton("Go!");
		hGo = new JButton("Go!");
		jGo.addActionListener(this);
		hGo.addActionListener(this);
		GridLayout hostGrid = new GridLayout(4,1);
		GridLayout joinGrid = new GridLayout(4,1);
		
		host = new JPanel();
		host.setPreferredSize(new Dimension(250,100));
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
		
		JPanel modePane = new JPanel();
		modePane.add(modeTabbedPane);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(300, 200);
		this.add(modePane);
		this.setTitle("Arena");
		this.setVisible(true);
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
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == jGo){
			this.setVisible(false);
			String ip = ipField.getText();
			if(ip != null){
				arena.join(ip, 5379);
			}
		}
		
		else if(e.getSource() == hGo){
			this.setVisible(false);
			int numPlayers =  Integer.parseInt(numPlayersField.getText());
			if(numPlayers < 5 && numPlayers > 0){
				arena.host(numPlayers, 5379);
			}
		}
	}
}
