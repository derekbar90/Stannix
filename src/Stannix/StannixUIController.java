package Stannix;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import org.controlsfx.control.ButtonBar.ButtonType;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.DialogAction;
import org.controlsfx.dialog.Dialogs;

import com.twilio.sdk.TwilioRestException;

/**
 * FXML Controller class for WebCrawl
 *
 * @author derekbarrera
 */

@SuppressWarnings("deprecation")
public class StannixUIController {

	//FXML Linking Variables
	@FXML private TextArea outputWindow;
	@FXML private TextField phoneNumber, messageContents;
	@FXML private Button sendText, nextRound;

	//Variables
	private String messageContentsText;
	private String phoneNumberText;
	private String question;
	private String questionOutput;

	Stannix startGame, messageList;
	private int currentPlayerRegistrationCount;
	private Action nextButton = new DialogAction("Next", ButtonType.OTHER);
	private String gameID = "";
	private final int maxRounds = 10;
	private int currentRound = 0;
	private int minPlayersNeeded = 3;


	/**
	 * Displays unique game ID to users and initializes the player objects and overall count. 
	 * Enables the nextRound button if the current registration count is at or above needed players. 
	 * 
	 * @param event
	 * @throws InterruptedException
	 * @throws TwilioRestException
	 */

	@FXML private void startGame(ActionEvent event) throws InterruptedException, TwilioRestException{

		currentPlayerRegistrationCount = 0;
		currentRound = 0;

		startGame = new Stannix();

		gameID = startGame.gameInitialization();

		Action response = Dialogs.create().title("Waiting for players...")
				.actions(nextButton)
				.masthead("To join the curent game:")
				.message( "Text: " + gameID + " to (703) 991-4800").showConfirm();


		reponseHandler(response);

		if(currentPlayerRegistrationCount >= minPlayersNeeded){

			nextRound.setDisable(false);

		}

	}

	/**
	 * Starts a round for the game. First checking to make sure that the game will not go over the max alloted rounds.
	 * Then sets the judge and displays rounds prompt. After which, then creates text messages and sends out specified texts to 
	 * let users know of the rounds answers. Then prompts for the Judge's input, which again is handled and sent via text out to the users.
	 * Lastly, it displays the current score.
	 * 
	 * @param event
	 * @throws TwilioRestException
	 */

	@FXML private void startRound(ActionEvent event) throws TwilioRestException{

		currentRound++;
		outputWindow.clear();
		if(currentPlayerRegistrationCount >= minPlayersNeeded){

			if(currentRound != maxRounds){

				startGame.setJudge();

				//Get question for round
				question = startGame.startRound();

				//Display question notification
				Action response = Dialogs.create().title("Round " + currentRound + " Question...")
						.actions(nextButton)
						.masthead(question)
						.message( "Text your replies, when done press next.").showConfirm();

				questionHandler(response);

				Action judgement = Dialogs.create().title("Round " + currentRound + " Judging...")
						.actions(nextButton)
						.masthead("Please choose from the following entries...\n" + questionOutput + "\n")
						.message( "Text your reply, when done press next.").showConfirm();

				judgeHandler(judgement);

				outputWindow.setText(startGame.printGameStatus("no winner"));

			}else{

				outputWindow.setText(startGame.printGameStatus("winner"));

			}

		}



	}

	/**
	 * Retrieves the last messages from every player and displays them on the screen.
	 * This is mostly for debugging purposes and can be found in the menu bar.
	 * 
	 * @param event
	 */

	@FXML private void getMess(ActionEvent event){

		messageList= new Stannix();
		startGame.getMessages();

	}

	/**
	 * This provides the close functionality for the applicaion
	 * 
	 * @param event
	 */

	@FXML private void exit(ActionEvent event){

		System.exit(0);

	}


	/**
	 * Basic getter function
	 * 
	 * @return the messageContentsText
	 */

	public String getMessageContentsText() {

		return messageContentsText;

	}


	/**
	 * Basic setter function
	 * 
	 * @param messageContentsText the messageContentsText to set
	 */

	public void setMessageContentsText(String messageContentsText) {

		this.messageContentsText = messageContentsText;

	}


	/**
	 * Basic getter function
	 * 
	 * @return the phoneNumberText
	 */

	public String getPhoneNumberText() {

		return phoneNumberText;

	}


	/**
	 * Basic setter function
	 * 
	 * @param phoneNumberText the phoneNumberText to set
	 */

	public void setPhoneNumberText(String phoneNumberText) {

		this.phoneNumberText = phoneNumberText;

	}

	/**
	 * Begins the player creation sequence after the game ID has been sent in via the users devices.
	 * After user creation, a current count of the players is displayed.
	 * 
	 * @param response
	 */

	private void reponseHandler(Action response){

		if(response == nextButton){

			try {

				currentPlayerRegistrationCount = startGame.createPlayers(gameID);

			} catch (InterruptedException e1) {

				e1.printStackTrace();

			}

			if(currentPlayerRegistrationCount >= minPlayersNeeded){

				Notifications.create()
				.title("Players have been initialized")
				.text("Currently " + currentPlayerRegistrationCount + " players playing!")
				.showWarning();

			}else if(currentPlayerRegistrationCount == 0){

				Notifications.create()
				.title("No playeres have been initialized")
				.text("Please start a new game, and try again.")
				.showWarning();

			}else if(currentPlayerRegistrationCount < minPlayersNeeded){

				Notifications.create()
				.title("Players have been initialized")
				.text("You must have at least 3 players to play Stannix!")
				.showWarning();		

			}

		}


	}

	/**
	 * Provides the ability to display the incoming messages from the users to the application UI.
	 * This is mainly used when helping judged select the winner. 
	 * 
	 * @param response
	 */

	private void questionHandler(Action response){

		if(response == nextButton){

			questionOutput = startGame.printMessages();

		}


	}

	/**
	 * Responds to the Judge 's input which it then validates it was received and if not prompts 
	 * again with alert text as well. Once received, adds a point to round winner.
	 * 
	 * @param response
	 */

	private void judgeHandler(Action response){



		if(response == nextButton){

			String winnerCheck = startGame.winnerCheck();

			if(winnerCheck.equals("")){


				Action judgement = Dialogs.create().title("Round " + currentRound + " Judging...")
						.actions(nextButton)
						.masthead("Alert: failed to get judge response! \n" + "Please choose from the following entries...\n" + questionOutput + "\n")
						.message( "Text your reply, when done press next.").showConfirm();

				judgeHandler(judgement);


			}else{

				Dialogs.create().title("Round " + currentRound + " Winner!")
				.actions(nextButton)
				.masthead("Player " + winnerCheck + " wins the round.")
				.message( "Press next, to go back to main screen.").showConfirm();

				startGame.sendWinningSubmission(currentRound);

			}



		}


	}

}