/**
 * 
 */
package Stannix;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.instance.Message;

/**
 * @author derekbarrera
 *
 */

public class Stannix {

	String phoneNumber, messageContents, currentDateStamp;
	TextService newText, messageList;
	String messageListSring = "";
	
	public Stannix(String phoneNumber, String messageContents) {
		
		this.phoneNumber = phoneNumber;
		this.messageContents = messageContents;
		
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
	
	public ArrayList<Message> getMessages(){
		
		messageList = new TextService();
		return messageList.getMessageList(setTimeStamp());
	
	}
	
	public String printMessages(ArrayList<Message> messageList){
		
		for(Message message : messageList){
			
		 	if(message.getDirection().equals("inbound")){
				 
		 		messageListSring += message.getBody() + " " + message.getDateSent() + "\n";
		 	
		 	}
		 	
			
		}
		
		return messageListSring;
		
	}
	
	public String gameInitialization(){
		
		Random rand = new Random();
		setTimeStamp();
		return String.valueOf(rand.nextInt((9999 - 1001) + 1) + 1001);
		
	}
	
	public String setTimeStamp(){
		
		SimpleDateFormat date = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
		Date today = new Date();
		currentDateStamp = date.format(today);
		
		return currentDateStamp;
		
	}

}
