package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.event.EventHandler;

public class MainController implements Initializable {
	@FXML
	ListView<Competition> competitonsView;
	@FXML
	Button addButton;
	@FXML
	TextField searchTf;
	@FXML
	ChoiceBox<String> filterBox;
	@FXML
	ChoiceBox<String> sortBox;
	ArrayList<Competition> competitions;

	public void removeComp(ActionEvent e) {
		Competition comp = competitonsView.getSelectionModel().getSelectedItem();
		if (comp != null) {
			competitonsView.getItems().remove(comp);
			competitions.remove(comp);
		} else {
			Alert errorAlert = new Alert(AlertType.WARNING);
			errorAlert.setHeaderText("You Haven't selected any competiton");
			errorAlert.showAndWait();
		}
		competitonsView.setCellFactory(listCell -> new ListCell<Competition>() {
            @Override
            protected void updateItem(Competition item, boolean empty) {
                super.updateItem(item, empty);

                if(empty || item == null) {
                    setText(null);
                }
                else if (item != null && item.getType().equals("Team Based")) {
                	setText(item.getName());
                	getStyleClass().add("Team-based");
                } else {
                	setText(item.getName());
                	getStyleClass().add("Individual-based");
                }
            }
            });
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		this.competitions = Main.competitions;
		ObservableList<Competition> comps = FXCollections.observableArrayList(competitions);
		competitonsView.setItems(comps);
		competitonsView.setCellFactory(listCell -> new ListCell<Competition>() {
            @Override
            protected void updateItem(Competition item, boolean empty) {
                super.updateItem(item, empty);

                if(empty || item == null) {
                    setText(null);
                }
                else if (item != null && item.getType().equals("Team Based")) {
                	setText(item.getName());
                	getStyleClass().add("Team-based");
                } else {
                	setText(item.getName());
                	getStyleClass().add("Individual-based");
                }
            }
            });
		
		competitonsView.setOnMouseClicked(new EventHandler<MouseEvent>() {
		
		    @Override
		    public void handle(MouseEvent click) {

		        if (click.getClickCount() == 2) {
		        	Competition comp = competitonsView.getSelectionModel().getSelectedItem();
					SceneController sceneController = new SceneController();
					sceneController.switchToCompetition((Stage) competitonsView.getScene().getWindow(), comp);
		        }
		    }
		   
		});
		
		 searchTf.setOnKeyReleased(e -> {
				search(searchTf);
			});
		 searchTf.setPromptText("Search");
		 addButton.setOnAction(this::addCompetition);
		 String[] filterChoices = {"None", "Team", "Individual", "Active"};
		 filterBox.setValue("Filter");
		 filterBox.setOnAction(this::filter);
		 filterBox.getItems().addAll(filterChoices);
		 String[] sortChoices = {"None", "Name", "Date"};
   		 sortBox.setValue("Sort");
		 sortBox.setOnAction(this::sort);
		 sortBox.getItems().addAll(sortChoices);

	}
	public void checkDue() {
		Date todayDate = new Date();
		ArrayList<Competition> matchedComps = new ArrayList<>();
		for (int i = 0; i < competitions.size(); i++) {
			Competition com = competitions.get(i);
			if (todayDate.compareTo(new Date(com.getDate()) )  > 0) {
				if (com.getParticpants().size() > 0) {
					for (Particpant particpant: com.getParticpants())
						if (particpant.getRank() == 0) {
							matchedComps.add(com);
							break;
						}
				}
				else 
					matchedComps.add(com);
			}
	}
		if (matchedComps.size() > 0) {
			Alert errorAlert = new Alert(AlertType.INFORMATION);
			errorAlert.setHeaderText("The following competitions should be updated");
			errorAlert.setContentText(" " + matchedComps);
			errorAlert.showAndWait();
			filterBox.setValue("None");
		}
	}
	public void search(TextField searchTf) {
		if (searchTf.getText().length() == 0) { 
		
			competitonsView.setItems(FXCollections.observableArrayList(competitions));
		} else {
			ArrayList<Competition> matchedComps = new ArrayList<>();
			String searchTerm = searchTf.getText();
			for (int i = 0; i < competitions.size(); i++) {
				if (competitions.get(i).getName().toUpperCase().contains(searchTerm.toUpperCase())) {
					matchedComps.add(competitions.get(i));
				}
			}
	
			competitonsView.setItems(FXCollections.observableArrayList(matchedComps));
			
		}
		competitonsView.setCellFactory(listCell -> new ListCell<Competition>() {
            @Override
            protected void updateItem(Competition item, boolean empty) {
                super.updateItem(item, empty);

                if(empty || item == null) {
                    setText(null);
                }
                else if (item != null && item.getType().equals("Team Based")) {
                	setText(item.getName());
                	getStyleClass().add("Team-based");
                } else {
                	setText(item.getName());
                	getStyleClass().add("Individual-based");
                }
            }
            });
		
	}
	public void filter(ActionEvent e) {
		String filterBy = filterBox.getValue();
		ArrayList<Competition> matchedComps = new ArrayList<>();
		if (filterBy.equals("None")) { 
			competitonsView.setItems(FXCollections.observableArrayList(competitions));
		} else if (filterBy.equals("Individual")){
			for (int i = 0; i < competitions.size(); i++) {
				if (competitions.get(i).getType().equals("Individual based")) {
					matchedComps.add(competitions.get(i));
				}
			}
			if (matchedComps.size() > 0)
				competitonsView.setItems(FXCollections.observableArrayList(matchedComps));
			else {
				Alert errorAlert = new Alert(AlertType.WARNING);
				errorAlert.setHeaderText("There are no Individual based competitions.");
				errorAlert.showAndWait();
				filterBox.setValue("None");
			}
		} else if (filterBy.equals("Team")) {
			for (int i = 0; i < competitions.size(); i++) {
				if (competitions.get(i).getType().equals("Team Based")) {
					matchedComps.add(competitions.get(i));
				}
			}
			if (matchedComps.size() > 0)
				competitonsView.setItems(FXCollections.observableArrayList(matchedComps));
			else {
				Alert errorAlert = new Alert(AlertType.WARNING);
				errorAlert.setHeaderText("There are no Team based competitions");
				errorAlert.showAndWait();
				filterBox.setValue("None");
			}
		} else {
				
				Date todayDate = new Date();
				for (int i = 0; i < competitions.size(); i++) {
					if (todayDate.compareTo(new Date(competitions.get(i).getDate()) )  < 0) {
						matchedComps.add(competitions.get(i));
					}
			}
				if (matchedComps.size() > 0)
					competitonsView.setItems(FXCollections.observableArrayList(matchedComps));
				else {
					Alert errorAlert = new Alert(AlertType.WARNING);
					errorAlert.setHeaderText("There are no acitve competitions");
					errorAlert.showAndWait();
					filterBox.setValue("None");
				}
		}
		competitonsView.setCellFactory(listCell -> new ListCell<Competition>() {
            @Override
            protected void updateItem(Competition item, boolean empty) {
                super.updateItem(item, empty);

                if(empty || item == null) {
                    setText(null);
                }
                else if (item != null && item.getType().equals("Team Based")) {
                	setText(item.getName());
                	getStyleClass().add("Team-based");
                } else {
                	setText(item.getName());
                	getStyleClass().add("Individual-based");
                }
            }
            });
	}
	public void sort(ActionEvent e) {


        String sortBy = sortBox.getValue();

        if (sortBy.equals("None")) { 


            competitonsView.setItems(FXCollections.observableArrayList(competitions));

        } else if (sortBy.equals("Name")) {

            ArrayList<Competition> sortName = (ArrayList<Competition>)competitions.clone();

                boolean sorted = false;

                Competition temp;

                while(!sorted) {

                    sorted = true;

                    for (int i = 0; i < sortName.size() - 1; i++) {

                        if (sortName.get(i).getName().compareTo(sortName.get(i+1).getName()) > 0) {

                            temp = sortName.get(i);

                            sortName.set(i, sortName.get(i+1));

                            sortName.set(i+1, temp);

                            sorted = false;

                    }

                }

            }

            

                competitonsView.setItems(FXCollections.observableArrayList(sortName));

        } else if (sortBy.equals("Date")) {

            ArrayList<Competition> sortName = (ArrayList<Competition>)competitions.clone();

            boolean sorted = false;

            Competition temp;

            while(!sorted) {

                sorted = true;

                for (int i = 0; i < sortName.size() - 1; i++) {

                    if (sortName.get(i).getDate().compareTo(sortName.get(i+1).getDate()) > 0) {

                        temp = sortName.get(i);

                        sortName.set(i, sortName.get(i+1));

                        sortName.set(i+1, temp);

                        sorted = false;

                }

            }

        }

       

            competitonsView.setItems(FXCollections.observableArrayList(sortName));
            
    }
        competitonsView.setCellFactory(listCell -> new ListCell<Competition>() {
            @Override
            protected void updateItem(Competition item, boolean empty) {
                super.updateItem(item, empty);

                if(empty || item == null) {
                    setText(null);
                }
                else if (item != null && item.getType().equals("Team Based")) {
                	setText(item.getName());
                	getStyleClass().add("Team-based");
                } else {
                	setText(item.getName());
                	getStyleClass().add("Individual-based");
                }
            }
            });
    }
	public void addCompetition(ActionEvent event) {
		SceneController sceneController = new SceneController();
		sceneController.switchToEditCom((Stage) competitonsView.getScene().getWindow(), this.competitions);
	}

}

