package application;
	
import java.util.ArrayList;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;


public class Main extends Application {
	public static ArrayList<Competition> competitions = importExcel.getCompetitions();
	@Override
	public void start(Stage primaryStage) {
		exportExcel exportToExcel = new exportExcel(competitions);
		try {
			//Competition(String name, String link, Date date)
			
			FXMLLoader loader =  new FXMLLoader(getClass().getResource("MainPage.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root, 1200, 720);
			String css = this.getClass().getResource("application.css").toExternalForm();
			scene.getStylesheets().add(css);
			primaryStage.setTitle("Competition tracker app");
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream( "competition.png" )));
			primaryStage.show();
			primaryStage.setOnCloseRequest(e -> {
				exportToExcel.export();
			});
			MainController mainController = loader.getController();
			mainController.checkDue();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
