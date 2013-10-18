package game;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
	public static void main(String []args) {
		Connection server = new Connection("127.0.0.1");
		String r = server.recieve();
		while(r != "close"){
			System.out.println("    "+r);
			String m = "Gotcha, here's the time -> "+System.currentTimeMillis();
			System.out.println("    "+m);
			server.send(m);
			r = server.recieve();
		}
		server.send("Good bye!");
		server.close();
	}
}
