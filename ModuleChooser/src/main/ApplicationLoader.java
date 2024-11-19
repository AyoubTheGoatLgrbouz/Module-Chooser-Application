 
package main;

import controller.ModuleChooserController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.StudentProfile;
import view.ModuleChooserRootPane;

public class ApplicationLoader extends Application {

	private ModuleChooserRootPane view;
	
	@Override
	public void init() {
		//create model and view and pass their references to the controller
		StudentProfile model = new StudentProfile();
		view = new ModuleChooserRootPane();
		new ModuleChooserController(model, view);	
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		//sets min width and height for the stage window
		stage.setMinWidth(530); 
		stage.setMinHeight(500);
		stage.setWidth(800); 
		stage.setHeight(600);
		stage.setTitle("Final Year Module Selection Tool");
		stage.setScene(new Scene(view));
		stage.show();
		stage.setResizable(true);
	
	}

	public static void main(String[] args) {
		launch(args);
	}

}
