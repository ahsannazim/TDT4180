package appointment;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Program extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		Parent root = FXMLLoader.load(getClass().getResource("Appointment.fxml"));
		
		Scene scene = new Scene(root,400,400);
		
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setResizable(false);
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
