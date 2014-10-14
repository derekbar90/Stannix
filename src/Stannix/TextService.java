package Stannix;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		final String ACCOUNT_SID = "ACae1f2a7a2d9403d1247a099a041bd68c";  //$NON-NLS-1$
		final String AUTH_TOKEN = Messages.getString("TextService.1");  //$NON-NLS-1$
		
		String phoneNumber, messageContents;

		public TextService(String phoneNumber, String messageContents){
			
			this.phoneNumber = phoneNumber;
			this.messageContents = messageContents;
			
		}
		
		//Empty Contrustor
		public TextService(){}
		
		public void sendText() throws TwilioRestException{
			
			// Find your Account Sid and Token at twilio.com/user/account 
			TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN); 
					 
			// Build the parameters 
			List<NameValuePair> params = new ArrayList<NameValuePair>();  
			params.add(new BasicNameValuePair("To", phoneNumber));  //$NON-NLS-1$
			params.add(new BasicNameValuePair("From", "+17039914800"));  //$NON-NLS-1$ //$NON-NLS-2$
			params.add(new BasicNameValuePair("Body", messageContents));    //$NON-NLS-1$
			 
			MessageFactory messageFactory = client.getAccount().getMessageFactory(); 
			Message message = messageFactory.create(params); 
			System.out.println(message.getSid()); 
			
			
		}
		
		private String getDate(){
			
			SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd"); //$NON-NLS-1$
			Date today = new Date();
			return date.format(today);
			
		}
		
		private String regex(String string){
			
			String result = "";  //$NON-NLS-1$
			String patternString = "[0-9][0-9]:[0-9][0-9]:[0-9][0-9]"; //$NON-NLS-1$
			
			Pattern pattern = Pattern.compile(patternString);
			Matcher matches = pattern.matcher(string);
			
			if(matches.find()){
				
				result = matches.group(0);
				
			}
			return result;
		}
		
		public ArrayList<Message> getMessageList(String time){
			
			 ArrayList<Message> messageList = new ArrayList<Message>();
			 
			 TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN); 
			 
			 // Build the parameters 
			 final Map<String, String> params = new HashMap<String, String>();
			 params.put("DateSent", getDate()); //$NON-NLS-1$
		 
			 MessageList messages = client.getAccount().getMessages(params); 
			 
			 for (Message message : messages) { 
				 			 
				 	if(message.getDirection().equals("inbound")){ //$NON-NLS-1$
					 
				 		messageList.add(message);
					 
				 	}
					  
			 } 
			
			return messageList;
		}
		
}
