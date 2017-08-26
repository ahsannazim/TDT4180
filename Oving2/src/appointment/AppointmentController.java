package appointment;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import appointment.Appointment;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import javafx.beans.property.Property;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.value.ChangeListener;
import java.time.LocalDate;

public class AppointmentController implements Initializable{
	
	@FXML private GridPane gridPane;
	@FXML private TextField formal;
	@FXML private TextField rom;
	@FXML private DatePicker start;
	
	
	@FXML private TextField fra;
	@FXML private TextField til;
	
	
	@FXML private TextField repetisjon;
	@FXML private DatePicker slutt;
	
	@FXML private Button knapp;
	
	@FXML private TextField blankSpace;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		startUp();
		setupListeners();
	}
	
	private void startUp() {
		setupRedBorder();
		setupPromptText();
		
        setupStart();
        setupSlutt();
		slutt.setDisable(true);
		knapp.setDisable(true);
	}
	
	private void setupRedBorder() {
		for (Node child : gridPane.getChildren()) {
			if (!(child.getClass().getSimpleName().equals("Label") || child.equals(knapp)
					|| child.equals(repetisjon) || child.equals(slutt))) {
				child.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
			}
			GridPane.setMargin(child, new Insets(5,0,0,0));
		}
	}
	
	private void setupPromptText() {
		formal.setPromptText("Formål");
		rom.setPromptText("rom");
		
		start.setPromptText("dd.mm.yyyy");
		slutt.setPromptText("dd.mm.yyyy");
		
		fra.setPromptText("hh:mm");
		til.setPromptText("hh:mm");
	}
	
	private void setupStart() {
		Callback<DatePicker, DateCell> dayCellFactory = dp -> new DateCell()
        {
            @Override
            public void updateItem(LocalDate item, boolean empty)
            {
                super.updateItem(item, empty);

                if(item.isBefore(LocalDate.now()) || item.isAfter(LocalDate.now().plusYears(1)))
                {
                    setStyle("-fx-background-color: #ffc0cb;");
                    Platform.runLater(() -> setDisable(true));

                }
            }
        };
        start.setDayCellFactory(dayCellFactory);
        start.setEditable(false);
		
	}
	private void setupSlutt() {
		Callback<DatePicker, DateCell> dayCellFactory = dp -> new DateCell()
        {
            @Override
            public void updateItem(LocalDate item, boolean empty)
            {
                super.updateItem(item, empty);
                if(item.isBefore(start.getValue().plusDays(Integer.parseInt(repetisjon.getText()))) 
                		|| item.isAfter(LocalDate.now().plusYears(1))){
                    setStyle("-fx-background-color: #ffc0cb;");
                    Platform.runLater(() -> setDisable(true));
                }
            }
        };
        slutt.setDayCellFactory(dayCellFactory);
        slutt.setEditable(false);
		
	}
	
	private void setupListeners() {
		
		formal.textProperty().addListener((property, oldValue,newValue)->{
			if (!newValue.trim().isEmpty())
				formal.setStyle("-fx-border-color: green ; -fx-border-width: 1px ;");
			if (isValidAll()) {
				knapp.setDisable(false);
			} else {
				knapp.setDisable(true);
			}
		});
		
		rom.textProperty().addListener((property, oldValue,newValue) ->{
			if (isValidRom(newValue)) {
				rom.setStyle("-fx-border-color: green ; -fx-border-width: 1px ;");
			} else {
				rom.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
			}
			if(isValidAll()) {
				knapp.setDisable(false);
			} else {
				knapp.setDisable(true);
			}
		});
		
		start.valueProperty().addListener((property, oldValue, newValue)->{
			start.setStyle("-fx-border-color: green ; -fx-border-width: 1px ;");
			slutt.setValue(null);
			setupSlutt();
			
			if(isValidAll()) {
				knapp.setDisable(false);
			} else {
				knapp.setDisable(true);
			}
		});
		
		slutt.valueProperty().addListener((property, oldValue, newValue)->{
			
		});
		
		repetisjon.textProperty().addListener((property, oldValue, newValue)->{
			slutt.setValue(null);
			handleRep(oldValue, newValue);
			if(isValidRep(repetisjon.getText())){
				if (!repetisjon.getText().equals("")){
					repetisjon.setStyle("-fx-border-color: green ; -fx-border-width: 1px ;");
					setupSlutt();
				} else {
					slutt.setDisable(true);
				}
			}else repetisjon.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
			
			if(isValidAll()) {
				knapp.setDisable(false);
			} else {
				knapp.setDisable(true);
			}
		});
		
		fra.textProperty().addListener((property, oldValue, newValue)->{
			timeHandler("fra", oldValue, newValue);
			if (isValidTime(fra.getText())) {
				fra.setStyle("-fx-border-color: green ; -fx-border-width: 1px ;");
			} else {
				fra.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
			}
			if(isValidAll()) {
				knapp.setDisable(false);
			} else {
				knapp.setDisable(true);
			}
		});
		
		til.textProperty().addListener((property, oldValue, newValue)->{
			timeHandler("til",oldValue, newValue);
			if (isValidTime2(til.getText())) {
				til.setStyle("-fx-border-color: green ; -fx-border-width: 1px ;");
				if (isValidAll()) {
					knapp.setDisable(false);
				}
			} else {
				til.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
			}
			if(isValidAll()) {
				knapp.setDisable(false);
			} else {
				knapp.setDisable(true);
			}
		});
		
		
		knapp.pressedProperty().addListener((property, oldValue, newValue)-> {
			if (property.getValue()) {
				System.out.println("Lagre");
			}
		});
		
	}
	
	private void handleRep(String oldValue, String newValue) {
		if (!newValue.matches("\\d*")){
			repetisjon.setText(oldValue);
			slutt.setDisable(true);
		}
		else slutt.setDisable(false);
	}
	
	private boolean isValidRep(String newValue) {
		if (newValue.matches("\\d*")) return true;
		return false;
	}
	
	private final String ROM_REGEX1 ="[a-zA-Z\\s\\-]+[0-9]*";
	private final String ROM_REGEX2 ="[0-9]+";
	private boolean isValidRom(String newValue) {
		if (!newValue.contains(" ")) return false;
		
		int lastSpace = newValue.lastIndexOf(" ");
		String rom1 = newValue.substring(0,lastSpace);
		String rom2 = newValue.substring(lastSpace+1);
		return(rom1.matches(ROM_REGEX1) && rom2.matches(ROM_REGEX2));
	}
	
	private void timeHandler(String felt, String oldValue, String newValue) {
		if (!Character.isDigit(newValue.charAt(newValue.length()-1))){
			if (newValue.length() != 3 || newValue.charAt(newValue.length()-1)!=':') {
				if (felt.equals("fra"))fra.setText(oldValue);
				else til.setText(oldValue);
			}
		} else {
			if (newValue.length()==1){
				if (Integer.parseInt(newValue)>2) {
					if (felt.equals("fra"))fra.setText(oldValue);
					else til.setText(oldValue);
				}
			}
			
			else if (newValue.length()==2 && oldValue.length()==1) {
				if (Integer.parseInt(oldValue)>1) {
					if (Character.getNumericValue(newValue.charAt(newValue.length()-1))>4) {
						if (felt.equals("fra"))fra.setText(oldValue);
						else til.setText(oldValue);
					}
				}
				else{
					if (felt.equals("fra")) fra.setText(newValue + ":");
					else til.setText(newValue + ":");
				}
			}
			else if (newValue.length()==3 && !(newValue.charAt(newValue.length()-1) ==':')) {
				if (felt.equals("fra"))fra.setText(newValue.substring(0,newValue.length()-1)+
						":"+newValue.substring(newValue.length()-1));
				else til.setText(newValue.substring(0,newValue.length()-1)+
						":"+newValue.substring(newValue.length()-1));
			}
			else if (newValue.length()==4) {
				if (Character.getNumericValue(newValue.charAt(newValue.length()-1)) >5) {
					if (felt.equals("fra"))fra.setText(oldValue);
					else til.setText(oldValue);
				}
			}
			else if (newValue.length()>5) {
				if(felt.equals("fra"))fra.setText(oldValue);
				else til.setText(oldValue);
			}
		}
	}
	private boolean isValidTime(String value) {
		if (value.matches("\\d\\d:\\d\\d")) return true;
		return false;
	}
	
	private boolean isValidTime2(String value) {
		if (isValidTime(value) && 
				Integer.parseInt(value.replace(":",""))> Integer.parseInt(fra.getText().replace(":", ""))) return true;
		return false;
	}
	
	private boolean isValidAll() {
		if (isValidRom(rom.getText()) && isValidTime(fra.getText()) 
				&& isValidTime2(til.getText()) && isValidRep(repetisjon.getText())
				&& !formal.getText().trim().isEmpty()) {
			if (!repetisjon.getText().trim().isEmpty()) {
				System.out.println(slutt.getValue());
				if (!(slutt.getValue() == null)) {
					return true;
				}
				return false;
			}
			else {
				if ((slutt.getValue() == null)) {
					return true;
				}
				return false;
			}
		}
		return false;
	}
}
