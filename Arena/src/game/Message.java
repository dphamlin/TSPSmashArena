package game;
/*temp class to test json*/
public class Message {
	private String m;
	private int n;
	
	public Message(int num, String message) {
		this.n = num;
		this.m = message;
	}
	
	public String getMessage(){
		return m;
	}
	
	public int getNumber(){
		return n;
	}
}
