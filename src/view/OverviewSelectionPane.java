package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Module;

public class OverviewSelectionPane extends GridPane{

	private TextArea studentProfile, selectedModules, reservedModules;
	private Button btnSaveOverview;
	
	public OverviewSelectionPane() {
		//styling
		this.setVgap(20);
		this.setHgap(20);
		this.setAlignment(Pos.CENTER);
		this.setGridLinesVisible(false);
		
		//TextArea
		studentProfile = new TextArea();
		studentProfile.setPrefHeight(150);
		studentProfile.setPromptText("Profile will appear here");
		studentProfile.setEditable(false);
		selectedModules = new TextArea();
		selectedModules.setPromptText("Selected modules will appear here");
		selectedModules.setEditable(false);
		reservedModules = new TextArea();
		reservedModules.setPromptText("Reserved modules will appear here");
		reservedModules.setEditable(false);
		
		//Button
		btnSaveOverview = new Button("Save Overview");
		
		//VBox
		VBox vbSO = new VBox();
		vbSO.setPadding(new Insets(5, 15, 10, 440));
		
		vbSO.getChildren().add(btnSaveOverview);
		
		this.add(studentProfile, 0, 0, 2, 1);
		this.add(selectedModules, 0, 1);
		this.add(reservedModules, 1, 1);
		this.add(vbSO, 0, 2, 2, 1);
	}
	
	public Button SaveOverviewButton() {
		return btnSaveOverview;
	}
	
	public TextArea getStudentProfile() {
		return studentProfile;
	}
	
	public TextArea getSelectedModules() {
		return selectedModules;
	}
	
	public TextArea getReservedModules() {
		return reservedModules;
	}
	
	public void addSaveOverviewButton(EventHandler<ActionEvent> handler) {
		btnSaveOverview.setOnAction(handler);
	}
}
