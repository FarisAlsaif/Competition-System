package application;



import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class webViewController implements Initializable {
	
	@FXML
	Button backButton;

	@FXML
	WebView webView;
	
	@FXML
	WebEngine engine;
	@FXML
	private ProgressBar progress; 

	private Competition competition;
	
	public void setPage(Competition comp) {
		this.competition = comp;
		String Link = competition.getLink();
		
		engine.getLoadWorker().stateProperty().addListener(
	            new ChangeListener<State>() {
	                @Override
	                public void changed(ObservableValue<? extends State> ov, State oldState, State newState) {
	                    if (newState == State.SUCCEEDED) {
	                         // hide progress bar then page is ready
	                         progress.setVisible(false);
	                    }
	                }
	            });
		engine.load(Link);	
	}
	
	
	  public void goBack(ActionEvent e) { 
		  SceneController sceneController = new SceneController(); 
		  sceneController.switchToCompetition((Stage) backButton.getScene().getWindow() , competition); 
	  }
	  


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		engine = webView.getEngine();
		backButton.setOnAction(this::goBack);

	    // updating progress bar using binding
	    progress.progressProperty().bind(engine.getLoadWorker().progressProperty());
	}
	
	
	
}
