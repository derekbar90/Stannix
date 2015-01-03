package Stannix;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;
import com.twilio.sdk.resource.list.MessageList;


/**
 * Provides texting service functionality
 * 
 * @author derekbarrera
 */

public class TextService {

	//Class Variables
	String phoneNumber, messageContents;
	
	
	//Twilio API Strings
	final String ACCOUNT_SID = "ACae1f2a7a2d9403d1247a099a041bd68c"; 
	final String AUTH_TOKEN = Messages.getString("TextService.1");
	ArrayList<Message> messageList, judgeList;

	/**
	 * Object creation for TextService
	 * 
	 * @param phoneNumber player phone number
	 * @param messageContents	player's last input
	 */
	
	public TextService(String phoneNumber, String messageContents){

		this.phoneNumber = phoneNumber;
		this.messageContents = messageContents;

	}

	/**
	 * Empty constructor to get access to class methods
	 * 
	 */
	public TextService(){}

	/**
	 * Sends a text to a specified number with a specified message
	 * 
	 * @param phoneNumber 		phone number to be messages
	 * @param messageContents	message to be sent
	 * @throws TwilioRestException
	 */
	public void sendText(String phoneNumber, String messageContents) throws TwilioRestException{

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

	/**
	 * Alerts the new Judge of their role
	 * 
	 * @param phoneNumber player phone number
	 * @param messageContents message to be sent
	 * @throws TwilioRestException
	 */
	public void sendJudgeAlert(String phoneNumber, String messageContents) throws TwilioRestException{

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

	/**
	 * Retrieves the current date in yyyy-MM-dd format
	 * 
	 * @return date in yyyy-MM-dd
	 */
	private String getDate(){

		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		date.setTimeZone(TimeZone.getTimeZone("UTC"));
		Date today = new Date();
		return date.format(today);

	}

	/**
	 * Iterates through an ArrayList of messages, sending back the ones which 
	 * were sent during player initialization
	 * 
	 * @param start Date object of startTime
	 * @param end Date object of endTime
	 * @return list of messages from users
	 */
	public ArrayList<Message> createPlayers(Date start, Date end){

		messageList = new ArrayList<Message>();

		TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN); 

		// Build the parameters 
		final Map<String, String> params = new HashMap<String, String>();
		params.put("DateSent", getDate()); 

		MessageList messages = client.getAccount().getMessages(params); 
		System.out.println(start + " " + end);
		for (Message message : messages) { 

			if(message.getDirection().equals("inbound")){ 

				if(start.compareTo(message.getDateSent()) < 0 && end.compareTo(message.getDateSent()) > 0){

					System.out.println("Incoming Message: " + message.getBody());
					messageList.add(message);

				}

			}

		} 

		return messageList;
	}

	/**
	 * Retrieves messages sent by users during time period, setting the Player objects properties as needed
	 * 
	 * @param start Date object of startTime
	 * @param end Date object of endTime
	 * @param playerList ArrayList of players currently in game.
	 * @return ArrayList of currentPlayers
	 */
	public ArrayList<Player> getMessageList(Date start, Date end, ArrayList<Player> playerList){

		judgeList = new ArrayList<Message>();
		MessageList messages = null;

		TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN); 

		// Build the parameters 
		final Map<String, String> params = new HashMap<String, String>();

		params.put("DateSent", getDate()); 

		System.out.println("New Request Sent Starting at " + start.toString() + " and ending at " + end.toString());

		try{

			messages = client.getAccount().getMessages(params);

		}catch(Exception e){

			System.out.println("Couldn't make request, Socket Timeout.");

		} 

		if(messages != null){
			
			for (Message message : messages) { 

				if(message.getDirection().equals("inbound")){ 

					if(start.compareTo(message.getDateSent()) < 0 && end.compareTo(message.getDateSent()) > 0){

						for(Player player : playerList){

							if(player.phoneNumber.equals(message.getFrom())){

								player.input = message.getBody();

							}

						}

					}

				}

			} 
			
		}

		return playerList;
	}

	
	/**
	 * Retrieves messages sent by Judge during time period, setting the Judge objects properties as needed
	 * 
	 * @param start Date object of startTime
	 * @param end Date object of endTime
	 * @param playerList ArrayList of players currently in game.
	 * @return ArrayList of currentPlayers
	 */
	public ArrayList<Player> getJudgeVote(Date start, Date end, ArrayList<Player> playerList){

		TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN); 

		// Build the parameters 
		final Map<String, String> params = new HashMap<String, String>();

		params.put("DateSent", getDate()); 

		MessageList messages = client.getAccount().getMessages(params); 

		for (Message message : messages) { 

			if(message.getDirection().equals("inbound")){ 

				if(start.compareTo(message.getDateSent()) < 0 && end.compareTo(message.getDateSent()) > 0){

					for(Player player : playerList){

						if(player.phoneNumber.equals(message.getFrom()) && player.judge){

							player.input = message.getBody();

						}

					}

				}

			}

		} 

		return playerList;
	}

}