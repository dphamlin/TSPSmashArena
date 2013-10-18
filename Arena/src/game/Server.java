package game;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Scanner;

public class Server {
	public static void main(String []args) {
		ServerSocket serv = null;
		try {
			serv = new ServerSocket(5379);
			Connection cliant0 = new Connection(serv);
			System.out.println("What to send?");
			Scanner input = new Scanner(System.in);
			String in = " ";
			while(in != "close"){
				in = input.nextLine();
				cliant0.send(in);
				System.out.println("     "+cliant0.recieve());
			}
			cliant0.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
