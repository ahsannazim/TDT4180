package hangman;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Hangman extends Application{

	@Override
	public void start(Stage mainStage) throws Exception {
		// TODO Auto-generated method stub
		Parent root = FXMLLoader.load(getClass().getResource("hangman.fxml"));
		Scene scene = new Scene(root,400,400);
		
		mainStage.setScene(scene);
		mainStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	

}
