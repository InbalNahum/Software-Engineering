package application;

import java.io.IOException;
import java.net.URL;

import common.CpsGlobals;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class Main extends Application {
	public static Stage stage;

	@Override
	public void start(Stage primaryStage) throws IOException {
	    // constructing our scene
		this.stage = primaryStage;
	    URL url = getClass().getResource("WelcomeWindow.fxml");
	    AnchorPane pane = FXMLLoader.load( url );
	    Scene scene = new Scene( pane );
	    primaryStage.setScene( scene );
	    primaryStage.getIcons().add(new Image(getClass().getResourceAsStream(CpsGlobals.cpsIconPath)));
	    primaryStage.setTitle( CpsGlobals.WelcomeWindowTitle);
	    stage.setResizable(false);
	    primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
