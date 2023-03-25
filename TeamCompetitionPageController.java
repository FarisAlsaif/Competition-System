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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TeamCompetitionPageController {

	@FXML
	Text CompDate;

	@FXML
	Text CompLink;

	@FXML
	Text CompName;

	@FXML
	Button backButton;
	
	@FXML
    private Button addParticpantBtn;
	
	@FXML
    private Button deleteParticpantBtn;
	
	@FXML
    private Button editParticpantBtn;

    @FXML
    private Button generateBtn;
    
	@FXML
	Text compType;
	@FXML
	Button editCompetitonBtn;
	@FXML
	TableColumn<Particpant, String> teamName;

	@FXML
	TableColumn<Particpant, Integer> teamRank;

	@FXML
	TableView<Particpant> teamView;
	Competition competition;
	ArrayList<Particpant> teams;
	
	
    public void populateTeamList() {
    	teamName.setCellValueFactory(new PropertyValueFactory<Particpant, String>("Name"));
    	teamRank.setCellValueFactory(new PropertyValueFactory<Particpant, Integer>("Rank"));
		this.teams = this.competition.getParticpants();
		ObservableList<Particpant> teamsList = FXCollections.observableArrayList(teams);
		teamView.setItems(teamsList);	
	}
	
	public void setPage(Competition competition) {
		this.competition = competition;
		String name = this.competition.getName();
		String link = this.competition.getLink();
		CompName.setText(name);
		CompLink.setText(link);
		CompDate.setText(this.competition.getDate());
		compType.setText(this.competition.getType());
		addParticpantBtn.setOnAction(this::AddTeam);
		deleteParticpantBtn.setOnAction(this::deleteTeam);
		editParticpantBtn.setOnAction(this::editTeam);
		CompLink.setOnMouseClicked(
				new EventHandler<MouseEvent>() {

				    @Override
				    public void handle(MouseEvent click) {
				    	SceneController sceneController = new SceneController();
						sceneController.switchToWeb((Stage) CompLink.getScene().getWindow(), competition);
				    }
				});
		editCompetitonBtn.setOnAction(this::edit);
	}
	
	@FXML
	public void goBack(ActionEvent e) {
		SceneController sceneController = new SceneController();
		sceneController.switchToHome((Stage) backButton.getScene().getWindow());
	}
	public void edit(ActionEvent e) {
		SceneController sceneController = new SceneController();
		sceneController.switchToEditCom((Stage) backButton.getScene().getWindow(), this.competition);
	}
	
	
	public void AddTeam(ActionEvent e) {
		SceneController sceneController = new SceneController();
		sceneController.switchToTeamPage((Stage) addParticpantBtn.getScene().getWindow(), competition, null);
	}
	public void deleteTeam(ActionEvent e) {
		Team team = (Team) teamView.getSelectionModel().getSelectedItem();
		this.competition.deleteParticpant(team);
		teamView.getItems().remove(team);
	}
	public void editTeam(ActionEvent e) {
		Team team =  (Team) teamView.getSelectionModel().getSelectedItem();
		if (team != null) {
			SceneController sceneController = new SceneController();
			sceneController.switchToTeamPage((Stage) addParticpantBtn.getScene().getWindow(), competition, team);
		} else {
			Alert errorAlert = new Alert(AlertType.WARNING);
			errorAlert.setHeaderText("You Haven't selected any team");
			errorAlert.showAndWait();
		}
	}
	
	public void generateEmail(ActionEvent e) throws URISyntaxException, IOException {
		Team team = (Team) teamView.getSelectionModel().getSelectedItem();
		EmailHandler email = new EmailHandler();
		email.generateEmail(team.getMember(),team, competition);
		}

}