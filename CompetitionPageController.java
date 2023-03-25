package application;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
public class CompetitionPageController {
	@FXML
	Text CompName;
	@FXML
	Text CompLink;
	@FXML
	Text CompDate;
	@FXML
	Text compType;
	@FXML
	Button backButton;
	@FXML
	TableView<Particpant> studentView;
	@FXML
	TableColumn<Particpant, Integer> studentRank;
	@FXML
	TableColumn<Particpant, String> studentMajor;
	@FXML
	TableColumn<Particpant, String> studentName;
	@FXML
	TableColumn<Particpant, String> studentID;
	@FXML
    private Button addParticpantBtn;

    @FXML
    private Button deleteParticpantBtn;

    @FXML
    private Button editCompetitonBtn;

    @FXML
    private Button editParticpantBtn;

    @FXML
    private Button generateBtn;
    
	Competition competition;
	ArrayList<Particpant> students;
	
    public void populateStudentList() {
    	studentID.setCellValueFactory(new PropertyValueFactory<Particpant, String>("ID"));
    	studentName.setCellValueFactory(new PropertyValueFactory<Particpant, String>("Name"));
    	studentMajor.setCellValueFactory(new PropertyValueFactory<Particpant, String>("Major"));
    	studentRank.setCellValueFactory(new PropertyValueFactory<Particpant, Integer>("Rank"));
		this.students = this.competition.getParticpants();
		ObservableList<Particpant> studentsList = FXCollections.observableArrayList(students);
		studentView.setItems(studentsList);	
		
	}
	
	public void setPage(Competition competition) {
		this.competition = competition;
		String name = this.competition.getName();
		String link = this.competition.getLink();
		CompName.setText(name);
		CompLink.setText(link);
		CompDate.setText(this.competition.getDate());
		compType.setText(this.competition.getType());
		addParticpantBtn.setOnAction(this::AddStudent);
		deleteParticpantBtn.setOnAction(this::deleteStudent);
		editParticpantBtn.setOnAction(this::editStudent);
		CompLink.setOnMouseClicked(
				new EventHandler<MouseEvent>() {

				    @Override
				    public void handle(MouseEvent click) {
				    	SceneController sceneController = new SceneController();
						sceneController.switchToWeb((Stage) CompLink.getScene().getWindow(), competition);
				    }
				});
		editCompetitonBtn.setOnAction(this::edit);
		generateBtn.setOnAction(arg0 -> {
			try {
				generateEmail(arg0);
			} catch (URISyntaxException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}
	public void edit(ActionEvent e) {
		SceneController sceneController = new SceneController();
		sceneController.switchToEditCom((Stage) backButton.getScene().getWindow(), this.competition);
	}
	public void goBack(ActionEvent e) {
		SceneController sceneController = new SceneController();
		sceneController.switchToHome((Stage) backButton.getScene().getWindow());
	}
	public void AddStudent(ActionEvent e) {
		SceneController sceneController = new SceneController();
		sceneController.switchToStudentPage((Stage) addParticpantBtn.getScene().getWindow(), competition, null);
	}
	public void deleteStudent(ActionEvent e) {
		Student student = (Student) studentView.getSelectionModel().getSelectedItem();
		this.competition.deleteParticpant(student);
		studentView.getItems().remove(student);
	}
	public void editStudent(ActionEvent e) {
		Student student =  (Student) studentView.getSelectionModel().getSelectedItem();
		if (student != null) {
			SceneController sceneController = new SceneController();
			sceneController.switchToStudentPage((Stage) addParticpantBtn.getScene().getWindow(), competition, student);
		} else {
			Alert errorAlert = new Alert(AlertType.WARNING);
			errorAlert.setHeaderText("You Haven't selected any student");
			errorAlert.showAndWait();
		}
	}
	public void generateEmail(ActionEvent e) throws URISyntaxException, IOException {
		Student student = (Student) studentView.getSelectionModel().getSelectedItem();
		if (student == null) {
			Alert errorAlert = new Alert(AlertType.WARNING);
			errorAlert.setHeaderText("You Haven't selected any particpant.");
			errorAlert.showAndWait();
		}
		else {
			EmailHandler email = new EmailHandler();
			email.generateEmail(student, competition);
		
		}
		}
	
}

