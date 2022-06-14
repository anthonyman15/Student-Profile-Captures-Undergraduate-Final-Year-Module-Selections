 
package main;

import controller.ModuleSelectionToolController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.StudentProfile;
import view.ModuleSelectionToolRootPane;
import view.OverviewSelectionPane;
import view.ReserveModulesPane;
import view.SelectModulesPane;

public class ApplicationLoader extends Application {

	private ModuleSelectionToolRootPane view;
	
	@Override
	public void init() {
		//create view and model and pass their references to the controller
		view = new ModuleSelectionToolRootPane();
		StudentProfile model = new StudentProfile();
		SelectModulesPane module = new SelectModulesPane();
		ReserveModulesPane reserve = new ReserveModulesPane();
		OverviewSelectionPane overview = new OverviewSelectionPane();
		
		new ModuleSelectionToolController(view, model, module, reserve, overview);	
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		//sets min width and height for the stage window
		stage.setMinWidth(530); 
		stage.setMinHeight(500);
		
		stage.setTitle("Final Year Module Selection Tool");
		stage.setScene(new Scene(view));
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
