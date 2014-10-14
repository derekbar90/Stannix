package Stannix;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

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
	
	@FXML private void startGame(ActionEvent event){
		
		boolean bool = true;
		startGame = new Stannix();
		startGame.setTimeStamp();
		
		
		outputWindow.setText(startGame.gameInitialization());
		
		while(bool){
			
			String startTime = startGame.gameInitialization();
			ArrayList<Message> players = startGame.getMessages();
			outputWindow.setText(startGame.printMessages(players));
			bool = false;
			
		}
		
	}
	
@FXML private void start(ActionEvent event){
		
		setPhoneNumberText(phoneNumber.getText());
		setMessageContentsText(messageContents.getText()); 
		
		startGame = new Stannix(getPhoneNumberText(), getMessageContentsText());
		
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
