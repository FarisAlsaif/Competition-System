package application;

import java.net.URL;
import java.util.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditComController implements Initializable {

    @FXML
    private Button AddButton;

    @FXML
    private Button cancelButton;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField link;

    @FXML
    private TextField name;

    @FXML
    private ChoiceBox<String> type;
    
    private ArrayList<Competition> competitions;
    private Competition competition;
    
    private String[] types = {"Individual based", "Team Based"};

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		type.getItems().addAll(types);
		type.setValue("Individual based");
		link.setPromptText("www.google.com");
		name.setPromptText("Competition Name");
		datePicker.setPromptText("12/16/2021");
	}
    public void add(ActionEvent e) {
    	
    	String comLink= link.getText(); 
    	String comName = name.getText();
    	String comType = type.getValue();
    	LocalDate comDate = datePicker.getValue();   
    	Date date =  Date.from(comDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    	Competition comp = new Competition(comName,comLink,date);
    	comp.editType(comType);
    	competitions.add(comp); 
    	goBack(e); 
    }
    public void setCompetitions(ArrayList<Competition> c) {
    		competitions = c;
			AddButton.setOnAction(this::add);
			cancelButton.setOnAction(this::goBack);
			
	}
    public void goBack(ActionEvent e) {
		SceneController sceneController = new SceneController();
		sceneController.switchToHome((Stage) AddButton.getScene().getWindow());
	}
    public void goBackComp(ActionEvent e) {
		SceneController sceneController = new SceneController();
		sceneController.switchToCompetition((Stage) AddButton.getScene().getWindow(), this.competition);
	}
    public void save(ActionEvent e) {
    	this.competition.editLink(link.getText());
    	this.competition.editName(name.getText());
    	LocalDate comDate = datePicker.getValue();   
    	Date date =  Date.from(comDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    	this.competition.editDate(date);
		SceneController sceneController = new SceneController();
		sceneController.switchToCompetition((Stage) AddButton.getScene().getWindow(), this.competition);
	}
    public void setCompetition(Competition c) {
    	this.competition = c;
    	link.setText(competition.getLink());
    	name.setText(competition.getName());
    	type.setValue(competition.getType());
    	type.setDisable(true);
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MMM-yyyy");
    	String date = competition.getDate();
    	LocalDate localDate = LocalDate.parse(date, formatter);
    	datePicker.setValue(localDate);
    	AddButton.setText("Save");
		AddButton.setOnAction(this::save);
		cancelButton.setOnAction(this::goBackComp);
    }

}
