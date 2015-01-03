package Stannix;

/**
 * Provides Player object creation
 * 
 * @author derekbarrera
 */
public class Player {

	String phoneNumber, input;
	int points, playerNumber;
	boolean judge, messageRecieved;

	/**
	 * Defines player object
	 * 
	 * @param phoneNumber Player phone number
	 * @param input Player's last input
	 */
	public Player(String phoneNumber, String input){

		this.phoneNumber = phoneNumber;
		this.input = input;
		playerNumber = 0;
		points = 0;
		judge = false;
		messageRecieved = false;

	}

	/**
	 * Basic toString override function
	 * 
	 * @retrn phone number and input
	 */
	public String toString(){

		return phoneNumber + " : " + input;

	}

}