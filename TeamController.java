package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class TeamController implements Initializable {

    @FXML
    private Button addParticpantBtn;

    @FXML
    private Button backButton;
    
    @FXML
    private Button saveButton;

    @FXML
    private Button deleteParticpantBtn;

    @FXML
    private Button editParticpantBtn;

    @FXML
	TableView<Particpant> studentView;
	@FXML
	TableColumn<Particpant, String> studentMajor;
	@FXML
	TableColumn<Particpant, String> studentName;
	@FXML
	TableColumn<Particpant, String> studentID;
    
    @FXML
    private TextField nameField;

    @FXML
    private TextField rankField;
    
     ArrayList<Student> part;

	private Competition competition;

	private Team team;
	
	public void populateStudentList() {
    	studentID.setCellValueFactory(new PropertyValueFactory<Particpant, String>("ID"));
    	studentName.setCellValueFactory(new PropertyValueFactory<Particpant, String>("Name"));
    	studentMajor.setCellValueFactory(new PropertyValueFactory<Particpant, String>("Major"));
		this.part = this.team.getMember();
		ObservableList<Particpant> studentsList = FXCollections.observableArrayList(part);
		studentView.setItems(studentsList);	
	}
	
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	saveButton.setOnAction(this::add);
		backButton.setOnAction(this::goBack);
	}
	public void setCompetition(Competition comp) {
		this.competition = comp;
		
	}
	public void setTeam(Team team) {
		addParticpantBtn.setOnAction(this::AddStudent);
		deleteParticpantBtn.setOnAction(this::deleteStudent);
		editParticpantBtn.setOnAction(this::editStudent);
		this.team = team;
		nameField.setText(team.getName());
		rankField.setText(team.getRank() + "");
		saveButton.setOnAction(this::save);
	}
	public void add(ActionEvent e) {
		// TODO Validate the form
		Team team = new Team( nameField.getText(), -1, Integer.parseInt(rankField.getText())); // Student(-1 , IDField , NameField , String Major ,int Rank ){
		this.competition.addParticpant(team);
		SceneController sceneController = new SceneController();
		sceneController.switchToCompetition((Stage) saveButton.getScene().getWindow(), competition);
	}
	public void save(ActionEvent e) {
		
		this.team.setName(nameField.getText());
		this.team.setRank(Integer.parseInt(rankField.getText()));
		SceneController sceneController = new SceneController();
		sceneController.switchToCompetition((Stage) saveButton.getScene().getWindow(), competition);
		// TODO Validate the form

//		sceneController.switchToCompetition((Stage) cancelButton.getScene().getWindow(), competition);
	}
	public void goBack(ActionEvent e) {
		SceneController sceneController = new SceneController();
		sceneController.switchToCompetition((Stage) backButton.getScene().getWindow(), competition);
	}
	public void AddStudent(ActionEvent e) {
		SceneController sceneController = new SceneController();
		sceneController.switchToStudentPage((Stage) addParticpantBtn.getScene().getWindow(), competition,team, null);
	}
	public void deleteStudent(ActionEvent e) {
		Student student = (Student) studentView.getSelectionModel().getSelectedItem();
		this.team.deleteMember(student);
		studentView.getItems().remove(student);
	}
	public void editStudent(ActionEvent e) {
		Student student =  (Student) studentView.getSelectionModel().getSelectedItem();
		if (student != null) {
			SceneController sceneController = new SceneController();
			sceneController.switchToStudentPage((Stage) addParticpantBtn.getScene().getWindow(), competition, team, student);
		} else {
			Alert errorAlert = new Alert(AlertType.WARNING);
			errorAlert.setHeaderText("You Haven't selected any student");
			errorAlert.showAndWait();
		}
	}
}
