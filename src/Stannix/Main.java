package Stannix;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


/**
 * Main class to start Scene/Application
 * @author derekbarrera
 * @version 1.0
 */

public class Main extends Application {
	
	/**
     * Start method to create and invoke the Stage
     * 
     * @param stage Current stage
     * @throws Exception Catch
     */

	@Override
	public void start(Stage primaryStage) {

		try {

			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("StannixUI.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch(Exception e) {

			e.printStackTrace();

		}

	}

	/**
     * Main method to start program
     * 
     * @param args the command line arguments
     */
	
	public static void main(String[] args) {

		launch(args);

	}
}
