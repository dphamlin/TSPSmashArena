package game;

import java.io.*;
import java.net.*;

public class Cliant {

	private Socket mySocket;
	private InetAddress loop = null;
	private BufferedReader in;
	
	public Cliant() {
		try {
			loop = InetAddress.getByName("127.0.0.1");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			mySocket = new Socket(loop, 5379);
			in = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
			System.out.print("Received: ");
			while(!in.ready()){}
			System.out.println(in.readLine());
			System.out.println();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String []args) {
		Cliant myCliant = new Cliant();
	}
}
