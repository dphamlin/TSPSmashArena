package game;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
	public static void main(String []args) {
		Connection con = new Connection(InetAddress.getLoopbackAddress());
		String r = con.recieve();
		while(r.compareTo("close\n") != 0){
			System.out.println("    "+r);
			String m = "Gotcha, here's the time -> "+System.currentTimeMillis()+"\n";
			System.out.println("    "+m);
			con.send(m);
			r = con.recieve();
		}
		con.send("Good bye!\n");
		con.close();
	}
}
