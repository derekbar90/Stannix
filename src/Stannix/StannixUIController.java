package Stannix;

import java.util.ArrayList;

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

import com.twilio.sdk.resource.instance.Message;

public class StannixUIController {
	
	//FXML Linking Variables
	@FXML private TextArea outputWindow;
	@FXML private TextField phoneNumber, messageContents;
	@FXML private Button sendText;
	
	//Variables
	private String messageContentsText;
	private String phoneNumberText;
	
	Stannix startGame, messageList;
	
	@SuppressWarnings("deprecation")
	@FXML private void startGame(ActionEvent event) throws InterruptedException{
		
		boolean bool = true;
		int currentPlayerRegistrationCount = 0;
		
		startGame = new Stannix();
		
		String gameID = startGame.gameInitialization();
		Action nextButton = new DialogAction("Next", ButtonType.OTHER);
		Action response = Dialogs.create().title("Waiting for players...")
				.actions(nextButton)
				.masthead("To join the curent game:")
				.message( "Text: " + gameID + " to (703) 991-4800").showConfirm();
		
		if(response == nextButton){

			
			Notifications.create()
            	.title("Players have been initialized")
            	.text("Currently " + startGame.createPlayers(gameID) + " players playing!")
            	.showWarning();
			
		}
		
	}
	
	@FXML private void getMess(ActionEvent event){
		
		messageList= new Stannix();
		ArrayList<Message> players = startGame.getMessages();
		outputWindow.setText(startGame.printMessages(players));
		
	}
		
	@FXML private void exit(ActionEvent event){
		
		System.exit(0);
		
	}


	/**
	 * @return the messageContentsText
	 */
	public String getMessageContentsText() {
		return messageContentsText;
	}


	/**
	 * @param messageContentsText the messageContentsText to set
	 */
	public void setMessageContentsText(String messageContentsText) {
		this.messageContentsText = messageContentsText;
	}


	/**
	 * @return the phoneNumberText
	 */
	public String getPhoneNumberText() {
		return phoneNumberText;
	}


	/**
	 * @param phoneNumberText the phoneNumberText to set
	 */
	public void setPhoneNumberText(String phoneNumberText) {
		this.phoneNumberText = phoneNumberText;
	}
	
}
