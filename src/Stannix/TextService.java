package Stannix;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;
import com.twilio.sdk.resource.list.MessageList;


public class TextService {

	//Class Variables
	
		//Twilio API Strings
		final String ACCOUNT_SID = "ACae1f2a7a2d9403d1247a099a041bd68c"; 
		final String AUTH_TOKEN = "3492f4493c8225c71ec7a744cb0fffee"; 
		
		String phoneNumber, messageContents;

		public TextService(String phoneNumber, String messageContents){
			
			this.phoneNumber = phoneNumber;
			this.messageContents = messageContents;
			
			
		}
		
		public void sendText() throws TwilioRestException{
			
			// Find your Account Sid and Token at twilio.com/user/account 
			TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN); 
					 
			// Build the parameters 
			List<NameValuePair> params = new ArrayList<NameValuePair>();  
			params.add(new BasicNameValuePair("To", phoneNumber)); 
			params.add(new BasicNameValuePair("From", "+17039914800")); 
			params.add(new BasicNameValuePair("Body", messageContents));   
			 
			MessageFactory messageFactory = client.getAccount().getMessageFactory(); 
			Message message = messageFactory.create(params); 
			System.out.println(message.getSid()); 
			
		}
		
		public String getMessageList(){
			
			 String messageList = "";  
			 
			 TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN); 
			 
			 // Build the parameters 
			 final Map<String, String> params = new HashMap<String, String>();
			 params.put("DateSent", "2014-10-08");
		 
			 MessageList messages = client.getAccount().getMessages(params); 
			 
			 for (Message message : messages) { 
				 			 
					 messageList += message.getBody() + "\t" + message.getDateSent() + "\n";
					  
			 } 
			
			return messageList;
		}
		
		
}
