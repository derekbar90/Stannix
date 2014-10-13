/**
 * 
 */
package Stannix;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import com.twilio.sdk.TwilioRestException;

/**
 * @author derekbarrera
 *
 */

public class Stannix {

	String phoneNumber, messageContents, currentDateStamp;
	TextService newText, messageList;
	
	public Stannix(String phoneNumber, String messageContents) {
		
		this.phoneNumber = phoneNumber;
		this.messageContents = messageContents;
		
		sendText();
		
	}
	
	public Stannix(){}
	
	private void sendText(){
		
		newText = new TextService(phoneNumber, messageContents);
		
		try {
			
			newText.sendText();
		
		} catch (TwilioRestException e) {
			
			System.out.println("Failed to Send Text");
			
		}
		
	}
	
	public String getInfo(){
		
		messageList = new TextService();
		return messageList.getMessageList(setTimeStamp());
	
	}
	
	public String displaySessionId(){
		
		Random rand = new Random();
		return String.valueOf(rand.nextInt((9999 - 1001) + 1) + 1001);
		
		
	}
	
	public String setTimeStamp(){
		
		SimpleDateFormat date = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
		Date today = new Date();
		currentDateStamp = date.format(today);
		
		return currentDateStamp;
		
	}

}
