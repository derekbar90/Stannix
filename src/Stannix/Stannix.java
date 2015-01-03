/**
 * 
 */
package Stannix;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Random;

import org.apache.commons.lang.math.NumberUtils;

import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.instance.Message;

/**
 * Provides Stannix main game functions
 * 
 * @author derekbarrera
 */

public class Stannix {

	final   int  				MAX_SCORE = 5; 
	private Date				startTimeStamp, endTimeStamp;
	private String 				messageListString = "";
	private String 				submissionListString = "";
	private String 				question = "";
	private String 				winnerPhone = "";
	private String 				winnerEntry = "";
	private TextService 		messageList = new TextService();
	public  ArrayList<Player> 	playerList = new ArrayList<Player>();
	private int 				playerCount = 0;
	private int 				judgeCount = 0;
	private String 				blank = "_____";
	private ArrayList<String> 	questionList;

	/**
	 * Provides object creation for the functions of a web crawler
	 * 
	 */
	public Stannix(){

		questionList = new ArrayList<String>();
		questionList.add("Grandma smells like " + blank + " during the holidays");
		questionList.add("When i'm at Walmart I think about " + blank + ".");
		questionList.add("Radford smells like " + blank + " in the morning.");
		questionList.add("Fidel Castro and Barrak Obama went on a trip where they " + blank + ".");
		questionList.add(blank + " is the best ITEC teacher.");
		questionList.add("Sometimes " + blank + " is the only thing keeping me going in college.");
		questionList.add(blank + " it's whats for homework.");
		questionList.add("Instead of programming during the day, I " + blank + ".");
		questionList.add("Instead of rent, I spend my money on " + blank + ".");
		questionList.add("One time at band camp... " + blank + ".");
		questionList.add("I visited a foreign nation and I brought back " + blank + ".");
		questionList.add("When I close my eyes I see " + blank + ".");
		questionList.add("The most recent budget cuts at RU made sure our deparment didn't get any " + blank + ".");
		questionList.add("MTV's new reality show features eight washed-up celebrities living with" + blank + ".");
		questionList.add("What's the most emo");
		questionList.add("I'm sorry Professor, but I couldn't complete my homework because of" + blank + ".");
		questionList.add("What's a girl's best friend?");
		questionList.add(blank + ": kid tested, mother-approved.");
		questionList.add("What's my anti-drug");
		questionList.add("It's a pity that kids these days are all getting involved with " + blank);
		questionList.add("Introducing x-treme Baseball! It's like baseball but with " + blank + "!");
		questionList.add("What is Batman's guilty pleasure?");
		questionList.add("Next fom J.K. Rowling Harry Potter and the Chamber of " + blank);
		questionList.add("I'm sorry Professor, but I couldn't complete my homework because of " + blank + ".");
		questionList.add("TSA guidelines now prohibit " + blank + " on airplanes.");


	}

	/**
	 * Basic setter functionality for playerCount
	 * 
	 * @param count	Current player count
	 * @return	player count
	 */
	
	public int playerCount(int count){

		this.playerCount = count;

		return playerCount;
	}

	/**
	 *  Sets the endTimeStamp variable and then uses the dialog start and stop time to retrieve messages,
	 *  which are returned as an ArrayList of Player objects
	 * 
	 * @return ArrayList of Players
	 */
	
	public ArrayList<Player> getMessages(){

		endTimeStamp = stopTimeStamp();
		playerList = messageList.getMessageList(startTimeStamp, endTimeStamp, playerList);
		return playerList;

	}

	/**
	 * Constructs two different strings to be pushed to both the Judge and the current players
	 * 
	 * @return Judge response options
	 */
	
	public String printMessages(){

		int count = 0;
		messageListString = "";
		submissionListString = "";
		playerList = getMessages();

		messageListString += question + "\n\n";
		submissionListString += "Submissions for: " + question + "\n\n";

		for(Player player : playerList){

			if(!player.judge){

				count++;
				player.playerNumber = count;

				messageListString += "Text " + count + " for: " + player.input + "\n";
				submissionListString += player.input + "\n";

			}

		}

		for(Player player : playerList){

			if(player.judge == true){

				try {
					messageList.sendText(player.phoneNumber, messageListString);

				} catch (TwilioRestException e) {

					System.out.println("Failed to alert judge of responses.");

				}

			}else{

				try {

					messageList.sendText(player.phoneNumber, submissionListString);

				} catch (TwilioRestException e) {

					System.out.println("Failed to tell player " + player.phoneNumber + " responses.");

				}

			}

		}

		startTimeStamp = startTimeStamp();
		return messageListString;

	}

	/**
	 * Iterates through initial text messages and creates users who are joining the given game ID
	 * 
	 * @param   gameID String containing unique Game ID
	 * @return	Current player count
	 * @throws  InterruptedException
	 */
	
	public int createPlayers(String gameID) throws InterruptedException{


		endTimeStamp = stopTimeStamp();
		ArrayList<Message> messages = messageList.createPlayers(startTimeStamp, endTimeStamp);

		for (Message message : messages) { 

			if(message.getBody().equals(gameID)){

				playerList.add(new Player(message.getFrom(), message.getBody()));

				try {

					messageList.sendText(message.getFrom(), "Welcome to Stannix. You are now added to the game.");

				} catch (TwilioRestException e) {

					System.out.println("Failed to send player creation message");

				}

			}

		}

		for( Player player : playerList){

			System.out.println(player.input + " " + player.phoneNumber);

		}
		return playerCount(playerList.size());

	}


	/**
	 * Creates random 4 digit integer
	 * 
	 * @return unique game ID
	 */
	
	public String gameInitialization(){

		startTimeStamp = startTimeStamp();
		Random rand = new Random();
		return String.valueOf(rand.nextInt((9999 - 1001) + 1) + 1001);

	}

	/**
	 * Create a Date object to be used with startTimeStamp
	 * 
	 * @return current Date
	 */
	
	public Date startTimeStamp(){

		Date startTimeStamps = new Date();

		return startTimeStamps;

	}

	/**
	 * Create a Date object to be used with stopTimeStmap
	 * 
	 * @return current Date
	 */
	public Date stopTimeStamp(){

		Date endTimeStamps = new Date();

		return endTimeStamps;

	}

	
	/**
	 * Checks if there is a current winner, if not returns null.
	 * Gives point to winner.
	 * 
	 * @return current round winner
	 */
	
	public String winnerCheck(){	

		Integer winnerNumber = null;
		winnerPhone = "";

		playerList = getMessages();

		for(Player player : playerList){

			if(player.judge){

				if(NumberUtils.isNumber((player.input))){

					winnerNumber = Integer.parseInt(player.input);

				}

			}

			if( winnerNumber != null){
				
				if(player.playerNumber == winnerNumber){

					player.points++;
					winnerPhone = player.phoneNumber;
					winnerEntry = player.input;

				}
			
			}


		}

		return winnerPhone;

	}

	/**
	 * Sends the winning player input plus phone number to all users except the Judge
	 * 
	 * @param currentRound
	 */
	
	public void sendWinningSubmission(int currentRound){

		for(Player player : playerList){

			try {

				if(!player.judge){

					messageList.sendText( "Round: " + currentRound + "\n" +player.phoneNumber, "Player " + winnerPhone + " has won with " + winnerEntry);

				}

			} catch (TwilioRestException e) {

				System.out.println("Unable to alert " + player.phoneNumber + " of winning submission.");

			}

			player.playerNumber = 0;

		}

	}

	/**
	 * Iterates through players, settings a Judge in a round robin fashion
	 * 
	 * @throws TwilioRestException
	 */
	
	public void setJudge() throws TwilioRestException{

		startTimeStamp();

		if(playerCount != judgeCount){

			if(judgeCount != 0){

				playerList.get(judgeCount-1).judge = false;

			}

			playerList.get(judgeCount).judge = true;
			messageList.sendJudgeAlert(playerList.get(judgeCount).phoneNumber, "You are now the judge. Please wait for incoming user submissions.");
			judgeCount++;

		}else{

			playerList.get(playerCount-1).judge = false;

			judgeCount = 0;
			playerList.get(judgeCount).judge = true;
			messageList.sendJudgeAlert(playerList.get(judgeCount).phoneNumber, "You are now the judge. Please wait for incoming user submissions.");
			judgeCount++;


		}

	}

	/**
	 * Shuffles the questions list, setting the current chosen question as a string, 
	 * and removing it from the list. Resets players input, and sends a text to all players 
	 * except judge of the current question. 
	 * 
	 * @return round question
	 */
	
	public String startRound(){

		startTimeStamp = startTimeStamp();
		question = "";

		Collections.shuffle(questionList);

		try{

			question = questionList.get(0).toString();
			questionList.remove(0);

		}catch(IndexOutOfBoundsException e){

			question = "Opps no more questions!";

		};

		resetPlayers();

		for(Player player : playerList){

			try {

				if(!player.judge){

					messageList.sendText(player.phoneNumber, "Fill in the blank: " + question);

				}

			} catch (TwilioRestException e) {

				System.out.println("Couldn't send question to player " + player.phoneNumber);

			}

		}

		return question;
	}

	/**
	 * Resets player input and messageRecieved value
	 * 
	 */
	
	public void resetPlayers(){

		for(Player player : playerList){

			player.messageRecieved = false;
			player.input = "";

		}

	}

	/**
	 * Checks for current high score, if the game is still in progress the score is displayed.
	 * If not, the winner and score is displayed.
	 * 
	 * @param type 	Determines if game is over or not
	 * @return 		game score
	 */
	public String printGameStatus(String type) {

		String gameStatus = "";
		String winner = "";

		if(type.equals("winner")){

			int currentLead = 0;

			for(Player player : playerList){

				if(currentLead <= player.points){

					winner = player.phoneNumber;
					currentLead = player.points;

				} 				

			}

			gameStatus += "Winner: " + winner + "\n\n";

			for(Player player : playerList){

				gameStatus += "Player " + player.phoneNumber.substring(2, player.phoneNumber.length()) + " has " + player.points + " \n";

			}

		}else{

			for(Player player : playerList){

				gameStatus += "Player " + player.phoneNumber.substring(2, player.phoneNumber.length()) + " has " + player.points + " \n"; 

			}

		}

		return gameStatus;
	}

}