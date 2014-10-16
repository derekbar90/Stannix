/**
 * 
 */
package Stannix;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import com.twilio.sdk.resource.instance.Message;

/**
 * @author derekbarrera
 *
 */

public class Stannix {

	private Date startTimeStamp, endTimeStamp;
	private String messageListSring = "";
	private TextService messageList = new TextService();
	public ArrayList<Player> playerList = new ArrayList<Player>();
	int playerCount = 0;
	
	public Stannix(){}
	
	public int playerCount(int count){
		
		this.playerCount = count;
		
		return playerCount;
	}
	
	public ArrayList<Message> getMessages(){
				
		return messageList.getMessageList(startTimeStamp, endTimeStamp);
	
	}
	
	public String printMessages(ArrayList<Message> messageList){
		
		for(Message message : messageList){
			
		 	if(message.getDirection().equals("inbound")){
				 
		 		messageListSring += message.getBody() + " " + message.getDateSent() + "\n";
		 	
		 	}
		 	
			
		}
		
		return messageListSring;
		
	}
	
	public int createPlayers(String gameID) throws InterruptedException{
		
		
		int count = 0;
		
		endTimeStamp = stopTimeStamp();
		ArrayList<Message> messages = messageList.getMessageList(startTimeStamp, endTimeStamp);
		for (Message message : messages) { 

			if(message.getBody().equals(gameID)){
				
				playerList.add(new Player(message.getFrom(), message.getBody()));
				count++;
				
			}
		 
		}
		
		for(int i = 0; i < playerList.size(); i++){
		
			System.out.println(playerList.get(i));
			
		}
		
		return count;
	
	}
	

	public String gameInitialization(){
		
		startTimeStamp = startTimeStamp();
		Random rand = new Random();
		return String.valueOf(rand.nextInt((9999 - 1001) + 1) + 1001);
		
	}
	
	public Date startTimeStamp(){
		
		Date startTimeStamps = new Date();
		
		return startTimeStamps;
		
	}
	
	public Date stopTimeStamp(){
		
		Date endTimeStamps = new Date();
		
		return endTimeStamps;
		
	}

}
