package game;
/*temp class to test json*/
public class Message {
	private String m;
	private int n;
	
	public Message(String message, int num){
		this.m = message;
		this.n = num;
	}
	
	public String getMessage(){
		return m;
	}
	
	public int getNumber(){
		return n;
	}
}
