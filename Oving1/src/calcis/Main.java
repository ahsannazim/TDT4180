package calcis;
	
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class Main extends Application {
	
	String[] symbols = {"C","x/-","%","÷","7","8","9","x","4","5","6","-","1","2","3","+","0",".","="};
	
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			StackPane top = new StackPane();
			Label calcText = new Label("1,478");
			calcText.setTextFill(Color.WHITE);
			
			calcText.setFont(Font.font("Helvetica",70));
			Rectangle topRect = new Rectangle(400,150,Color.BLACK);
			StackPane.setAlignment(calcText,Pos.BOTTOM_RIGHT);
			top.getChildren().addAll(topRect,calcText);
			
			
			GridPane buttons= new GridPane();
			int pos= 0;
			for (int i= 0;i<4;i++) {
				for (int j = 0;j<4;j++) {
					StackPane button = new StackPane();
					
					Label buttonSymbol = new Label(symbols[pos]);
					buttonSymbol.setFont(Font.font("Helvetica",40));
					
					Rectangle backgroundColor;
					if (j == 3) {
						backgroundColor = new Rectangle(100,100,Color.ORANGE);
						buttonSymbol.setTextFill(Color.WHITE);
					} else {
						backgroundColor = new Rectangle(100,100,Color.LIGHTGRAY);
					}
					button.getChildren().addAll(backgroundColor,buttonSymbol);
					
					buttons.add(button,j,i);
					pos++;
				}
			}
			
			GridPane bottom = new GridPane();
			for (int i = 0; i<3;i++) {
				StackPane button = new StackPane();
				Label buttonSymbol = new Label(symbols[pos]);
				buttonSymbol.setFont(Font.font("Helvetica",40));
				
				Rectangle backgroundColor;
				if(i==0) {
					backgroundColor = new Rectangle(200,100,Color.LIGHTGRAY);
				} else if (i==1) {
					backgroundColor= new Rectangle(100,100, Color.LIGHTGRAY);
				} else {
					backgroundColor = new Rectangle(100,100,Color.ORANGE);
					buttonSymbol.setTextFill(Color.WHITE);
				}
				
				button.getChildren().addAll(backgroundColor,buttonSymbol);
				bottom.add(button, i, 5);
				pos++;
				
			}
			
			
			buttons.setGridLinesVisible(true);
			bottom.setGridLinesVisible(true);
			
			root.setTop(top);
			root.setCenter(buttons);
			root.setBottom(bottom);
			
			Scene scene = new Scene(root,400,650);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.setTitle("Kalkulator");
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
