package Stannix;

public class Player {

	String phoneNumber, input;
	int points;
	boolean judge;
	
	public Player(String phoneNumber, String input){
		
		this.phoneNumber = phoneNumber;
		this.input = input;
		points = 0;
		judge = false;
		
	}
	
	public String toString(){
		
		return phoneNumber + " : " + input;
		
	}
	
}
