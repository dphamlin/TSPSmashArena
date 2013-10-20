package game;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Scanner;

public class Server {
	public static void main(String []args) {
		ServerSocket serv = null;
		try {
			serv = new ServerSocket(5379);
			Connection con = new Connection(serv);
			System.out.println("What to send?");
			Scanner input = new Scanner(System.in);
			String in = " ";
			while(in.compareTo("close\n") != 0){
				in = input.nextLine();
				in = in + "\n";
				con.send(in);
				System.out.println("     "+con.recieve());
			}
			con.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
