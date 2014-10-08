/**
 * 
 */
package Stannix;

import com.twilio.sdk.TwilioRestException;

/**
 * @author derekbarrera
 *
 */

public class Stannix {

	String phoneNumber, messageContents;
	
	public Stannix(String phoneNumber, String messageContents) {
		
		this.phoneNumber = phoneNumber;
		this.messageContents = messageContents;
		
		sendText();
		
	}
	
	private void sendText(){
		
		TextService newText = new TextService(phoneNumber, messageContents);
		
		try {
			
			newText.sendText();
		
		} catch (TwilioRestException e) {
			
			System.out.println("Failed to Send Text");
			
		}
		
	}

}
