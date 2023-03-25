package application;

import java.io.IOException;
import java.util.ArrayList;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneController {

	 private Scene scene;
	 private Parent root;
	 
	 public void switchToCompetition(Stage mainStage, Competition comp) {
			FXMLLoader loader;
			try {
				if (comp.getType().equals("Team Based")) {
					loader = new FXMLLoader(getClass().getResource("TeamCompetitionPage.fxml"));
					switchScene(loader, mainStage);
					TeamCompetitionPageController pageController = loader.getController();
					pageController.setPage(comp);
					pageController.populateTeamList();
				} else {
					loader = new FXMLLoader(getClass().getResource("CompetitionPage.fxml"));
					switchScene(loader, mainStage);
					CompetitionPageController pageController = loader.getController();
					pageController.setPage(comp);
					pageController.populateStudentList();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	 public void switchToHome(Stage mainStage) {
		 	FXMLLoader loader = new FXMLLoader(getClass().getResource("MainPage.fxml"));
			try {
				switchScene(loader, mainStage);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 }
	 public void switchToStudentPage(Stage mainStage,  Competition comp, Student student) {
		 if (student == null) {
			 FXMLLoader loader = new FXMLLoader(getClass().getResource("studentPage.fxml"));
				try {
					switchScene(loader, mainStage);
					
					StudentController pageController = loader.getController();
					pageController.setCompetition(comp);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		 } else {
			 FXMLLoader loader = new FXMLLoader(getClass().getResource("EditStudentPage.fxml"));
				try {
					switchScene(loader, mainStage);
					
					StudentController pageController = loader.getController();
					pageController.setCompetition(comp);
					pageController.setStudent(student);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		 }
		 	
	 }
	 public void switchToStudentPage(Stage mainStage,  Competition comp, Team team, Student student) {
		 if (student == null) {
			 FXMLLoader loader = new FXMLLoader(getClass().getResource("studentPage.fxml"));
				try {
					switchScene(loader, mainStage);
					
					StudentController pageController = loader.getController();
					pageController.setCompetition(comp);
					pageController.setTeam(team);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		 } else {
			 FXMLLoader loader = new FXMLLoader(getClass().getResource("EditStudentPage.fxml"));
				try {
					switchScene(loader, mainStage);
					
					StudentController pageController = loader.getController();
					pageController.setCompetition(comp);
					pageController.setTeam(team);
					pageController.setStudent(student);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		 }
		 	
	 }
	 public void switchToTeamPage(Stage mainStage,  Competition comp, Team team) {
		 if (team == null) {
			 FXMLLoader loader = new FXMLLoader(getClass().getResource("addTeamPage.fxml"));
				try {
					switchScene(loader, mainStage);
					
					TeamController pageController = loader.getController();
					pageController.setCompetition(comp);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		 } else {
			 FXMLLoader loader = new FXMLLoader(getClass().getResource("TeamPage.fxml"));
				try {
					switchScene(loader, mainStage);
					
					TeamController pageController = loader.getController();
					pageController.setCompetition(comp);
					pageController.setTeam(team);
					pageController.populateStudentList();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		 }
		 	
	 }
	 public void switchToWeb(Stage mainStage , Competition comp) {
		 	FXMLLoader loader = new FXMLLoader(getClass().getResource("webView.fxml"));
			try {
				switchScene(loader, mainStage);
				webViewController webController = loader.getController();
				webController.setPage(comp);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 }
    public void switchToEditCom(Stage mainStage,  ArrayList<Competition> competitions) {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("AddCompetition.fxml"));
		try {
			switchScene(loader, mainStage);
			EditComController editComController = loader.getController();
			editComController.setCompetitions(competitions);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    public void switchToEditCom(Stage mainStage,  Competition comp) {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("AddCompetition.fxml"));
		try {
			switchScene(loader, mainStage);
			EditComController editComController = loader.getController();
			editComController.setCompetition(comp);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    public void switchScene(FXMLLoader loader, Stage mainStage) throws IOException {
    	root = loader.load();
		scene = new Scene(root);
		String css = this.getClass().getResource("application.css").toExternalForm();
		scene.getStylesheets().add(css);
		mainStage.setScene(scene);
    }
}