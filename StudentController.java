package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class StudentController implements Initializable {

    @FXML
    private TextField IDField;

    @FXML
    private TextField MajorField;

    @FXML
    private TextField NameField;

    @FXML
    private TextField RankField;

    @FXML
    private Button addButtton;
    @FXML
    private Button saveButtton;
    @FXML
    private Button cancelButton;

	private Competition competition;
	private Student student;
	private Team team;
	boolean teamEdit;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		IDField.setPromptText("i.e., 202242370");
	    MajorField.setPromptText("i.e., SWE");
	    NameField.setPromptText("i.e., Saeed");
	    RankField.setPromptText("i.e., 12");
		if (saveButtton != null)
			saveButtton.setOnAction(this::save);
		if (addButtton != null)
			addButtton.setOnAction(this::add);
		
		cancelButton.setOnAction(this::cancel);
	}
	public void setCompetition(Competition comp) {
		this.competition = comp;
	}
	public void setTeam(Team team) {
		this.team = team;
		teamEdit = true;
		RankField.setText(this.team.getRank() + "");
		RankField.setDisable(true);
	}
	public void setStudent(Student student) {
		this.student = student;
		IDField.setText(student.getID());
		NameField.setText(student.getName());
		MajorField.setText(student.getMajor());
		if (!teamEdit)
			RankField.setText(student.getRank() + "");
	}
	public void add(ActionEvent e) {
		// TODO Validate the form
		try {
			if (!IDField.getText().matches("[0-9]{9}")) {
				throw new Exception();
			}
			Student student = new Student(-1, IDField.getText(), NameField.getText(), MajorField.getText(), Integer.parseInt(RankField.getText()));
			SceneController sceneController = new SceneController();
			if (teamEdit) {
				this.team.addMember(student);
				sceneController.switchToTeamPage((Stage) cancelButton.getScene().getWindow(), competition, this.team);
			} else {
				this.competition.addParticpant(student);
				sceneController.switchToCompetition((Stage) cancelButton.getScene().getWindow(), competition);
			}
		} catch (NumberFormatException ee) {
			Alert errorAlert = new Alert(AlertType.WARNING);
			errorAlert.setHeaderText("Rank Should be Integer");
			errorAlert.showAndWait();
		} catch (Exception ee) {
			Alert errorAlert = new Alert(AlertType.WARNING);
			errorAlert.setHeaderText("ID should be 9 digits.");
			errorAlert.showAndWait();
		}
		
	}
	public void save(ActionEvent e) {
		// TODO Validate the form
		try {
			if (!IDField.getText().matches("[0-9]{9}")) {
				throw new Exception();
			}
			this.student.setID(IDField.getText()); 
			this.student.setName(NameField.getText());
			this.student.setMajor(MajorField.getText());
			this.student.setRank(Integer.parseInt(RankField.getText()));
			SceneController sceneController = new SceneController();
			if (teamEdit) {
				sceneController.switchToTeamPage((Stage) cancelButton.getScene().getWindow(), competition, this.team);
			} else {
				sceneController.switchToCompetition((Stage) cancelButton.getScene().getWindow(), competition);
			}
		} catch (NumberFormatException ee) {
			Alert errorAlert = new Alert(AlertType.WARNING);
			errorAlert.setHeaderText("Rank Should be Integer");
			errorAlert.showAndWait();
		} catch (Exception ee) {
			Alert errorAlert = new Alert(AlertType.WARNING);
			errorAlert.setHeaderText("ID should be 9 digits.");
			errorAlert.showAndWait();
		}
		
		
	}
	public void cancel(ActionEvent e) {
		SceneController sceneController = new SceneController();
		if (teamEdit) {
			sceneController.switchToTeamPage((Stage) cancelButton.getScene().getWindow(), competition, this.team);
		} else {
			sceneController.switchToCompetition((Stage) cancelButton.getScene().getWindow(), competition);
		}
	}
	

}
