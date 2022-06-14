package view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Cell;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Course;
import model.Module;
import model.StudentProfile;
import static model.Schedule.*;

public class SelectModulesPane extends GridPane{
	
	ObservableList<Module> t1 = FXCollections.observableArrayList();
	ObservableList<Module> t2 = FXCollections.observableArrayList();
	ObservableList<Module> t3 = FXCollections.observableArrayList();
	ObservableList<Module> t4 = FXCollections.observableArrayList();
	ObservableList<Module> t5 = FXCollections.observableArrayList();
	private ListView<Module> unselectedT1, unselectedT2, yearLongModule, selectedT1, selectedT2;
	private Button btnAdd1, btnAdd2, btnRemove1, btnRemove2, btnReset, btnSubmit;
	private TextField txtTerm1, txtTerm2;
	
	public SelectModulesPane() {
		//styling
		this.setVgap(10);
		this.setHgap(20);
		this.setAlignment(Pos.CENTER);
		this.setGridLinesVisible(false);
		
		ColumnConstraints column0 = new ColumnConstraints();
		column0.setHalignment(HPos.CENTER);
		
		this.getColumnConstraints().addAll(column0);
		
		//VB
		VBox vb = new VBox();
		vb.setPadding(new Insets(15, 5, 5, 20));	//up, right, down, left
		vb.setSpacing(10);
		VBox vb2 = new VBox();
		vb2.setPadding(new Insets(5, 5, 10, 20));
		vb2.setSpacing(10);
		VBox vbTerm1 = new VBox();
		vbTerm1.setPadding(new Insets(5, 5, 10, 20));
		vbTerm1.setSpacing(10);
		VBox vb3 = new VBox();
		vb3.setPadding(new Insets(15, 15, 5, 5));
		vb3.maxHeight(Double.MAX_VALUE);
		vb3.maxWidth(Double.MAX_VALUE);
		vb3.setSpacing(10);
		VBox vb4 = new VBox();
		vb4.setPadding(new Insets(-90, 15, 5, 5));
		vb4.setSpacing(10);
		VBox vb5 = new VBox();
		vb5.setPadding(new Insets(-140, 15, 5, 5));
		vb5.setSpacing(10);
		VBox vbTerm2 = new VBox();
		vbTerm2.setPadding(new Insets(5, 20, 10, 5));
		vbTerm2.setSpacing(10);
		VBox vbAction = new VBox();
		vbAction.setPadding(new Insets(5, 15, 10, 450));
		vbAction.setSpacing(10);
		
		//HB
		HBox hbTerm1 = new HBox();
		hbTerm1.setPadding(new Insets(5, 15, 10, 150));
		hbTerm1.setSpacing(10);
		HBox hbTerm2 = new HBox();
		hbTerm2.setPadding(new Insets(5, 15, 10, 20));
		hbTerm2.setSpacing(10);
		HBox action = new HBox();
		action.setPadding(new Insets(5, 15, 10, 20));
		action.setSpacing(10);
		
		//create labels
		Label lblUnselectedTerm1 = new Label("Unselected Term 1 modules");
		Label lblUnselectedTerm2 = new Label("Unselected Term 2 modules");
		Label lblSelectedYearLong = new Label("Selected Year Long modules");
		Label lblSelectedTerm1 = new Label("Selected Term 1 modules");
		Label lblSelectedTerm2 = new Label("Selected Term 2 modules");
		Label lblCurrentTerm1 = new Label("Current Term 1 Credits:");
		Label lblCurrentTerm2 = new Label("Current Term 2 Credits:");
		
		//initialise ListView
		unselectedT1 = new ListView<Module>();	//Unselected Term 1
		unselectedT1.setPrefSize(500, 100);
		unselectedT2 = new ListView<Module>();	//Unselected Term 2
		unselectedT2.setPrefSize(500, 100);
		yearLongModule = new ListView<Module>();	//Selected Year Long
		yearLongModule.setPrefSize(400, 50);
		selectedT1 = new ListView<Module>();	//Selected Term 1
		selectedT1.setPrefSize(500, 100);
		selectedT2 = new ListView<Module>();	//Selected Term 2
		selectedT2.setPrefSize(500, 100);
		
		//setup text fields
		txtTerm1 = new TextField();
		txtTerm1.setPrefWidth(50);
		txtTerm1.setEditable(false);
		txtTerm2 = new TextField();
		txtTerm2.setPrefWidth(50);
		txtTerm2.setEditable(false);
		
		//initialise buttons
		btnAdd1 = new Button("Add");
		btnAdd2 = new Button("Add");
		btnRemove1 = new Button("Remove");
		btnRemove2 = new Button("Remove");
		btnReset = new Button("Reset");
		btnSubmit = new Button("Submit");
		HBox buttonBar1 = new HBox(20, txtTerm1, btnAdd1, btnRemove1);
		HBox buttonBar2 = new HBox(20, txtTerm2, btnAdd2, btnRemove2);
		HBox buttonBar3 = new HBox(40, btnReset, btnSubmit);
		buttonBar1.setAlignment(Pos.CENTER);
		buttonBar2.setAlignment(Pos.CENTER);
		buttonBar3.setAlignment(Pos.CENTER);
		
		vb.getChildren().addAll(lblUnselectedTerm1, unselectedT1, buttonBar1);
		vb2.getChildren().addAll(lblUnselectedTerm2, unselectedT2, buttonBar2);
		hbTerm1.getChildren().addAll(lblCurrentTerm1, txtTerm1);
		vbTerm1.getChildren().add(hbTerm1);
		vb3.getChildren().addAll(lblSelectedYearLong, yearLongModule);
		vb4.getChildren().addAll(lblSelectedTerm1, selectedT1);
		vb5.getChildren().addAll(lblSelectedTerm2, selectedT2);
		hbTerm2.getChildren().addAll(lblCurrentTerm2, txtTerm2);
		vbTerm2.getChildren().add(hbTerm2);
		action.getChildren().add(buttonBar3);
		vbAction.getChildren().add(action);
		
		//add controls and labels to container
		this.add(vb, 0, 0);
		this.add(vb2, 0, 1);
		this.add(vbTerm1, 0, 3);
		this.add(vb3, 1, 0);
		this.add(vb4, 1, 1, 1, 1);
		this.add(vb5, 1, 2, 1, 1);
		this.add(vbTerm2, 1, 3);
		this.add(vbAction, 0, 4, 2, 1);
	}
	
	public TextField getT1Credits() {
		return txtTerm1;
	}
	
	public TextField getT2Credits() {
		return txtTerm2;
	}
	
	public ObservableList<Module> getListT1() {
		return t1;
	}
	
	public ObservableList<Module> getListT2() {
		return t2;
	}
	
	public ObservableList<Module> getListT3() {
		return t3;
	}
	
	public ObservableList<Module> getListT4() {
		return t4;
	}
	
	public ObservableList<Module> getListT5() {
		return t5;
	}
	
	public ListView<Module> getUnselectedT1() {
		return unselectedT1;
	}
	
	public ListView<Module> getUnselectedT2() {
		return unselectedT2;
	}
	
	public ListView<Module> getYearLongModule() {
		return yearLongModule;
	}
	
	public ListView<Module> getSelectedT1() {
		return selectedT1;
	}
	
	public ListView<Module> getSelectedT2() {
		return selectedT2;
	}
	
	public Button getSubmitButton() {
		return btnSubmit;
	}
	
	//Add event for btnAdd1
	public void addAddHandlerT1(EventHandler<ActionEvent> handler) {
		btnAdd1.setOnAction(handler);
	}
	
	//Add event for btnAdd2
	public void addAddHandlerT2(EventHandler<ActionEvent> handler) {
		btnAdd2.setOnAction(handler);
	}
	
	//Remove event for btnRemove1
	public void addRemoveHandlerT1(EventHandler<ActionEvent> handler) {
		btnRemove1.setOnAction(handler);
	}
	
	//Remove event for btnRemove2
	public void addRemoveHandlerT2(EventHandler<ActionEvent> handler) {
		btnRemove2.setOnAction(handler);
	}
	
	//Reset Handler
	public void addResetHandler(EventHandler<ActionEvent> handler) {
		btnReset.setOnAction(handler);
	}
	
	//Submit Handler
	public void addSubmitHandler(EventHandler<ActionEvent> handler) {
		btnSubmit.setOnAction(handler);
	}
}
