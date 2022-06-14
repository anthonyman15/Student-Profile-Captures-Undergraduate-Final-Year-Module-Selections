package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Module;

public class ReserveModulesPane extends GridPane{

	ObservableList<Module> t1 = FXCollections.observableArrayList();
	ObservableList<Module> t2 = FXCollections.observableArrayList();
	ObservableList<Module> t4 = FXCollections.observableArrayList();
	ObservableList<Module> t5 = FXCollections.observableArrayList();
	private ListView<Module> LVUnselectedTerm1, LVReservedTerm1, LVUnselectedTerm2, LVReservedTerm2;
	private Button btnAddT1, btnRemoveT1, btnConfirmT1, btnAddT2, btnRemoveT2, btnConfirmT2;
	private TextField txtTerm1, txtTerm2;
	private TitledPane tpTerm1, tpTerm2;
	private Accordion accd;
	
	public ReserveModulesPane() {
		this.setAlignment(Pos.CENTER);
		//VB
		VBox vbUnselectedTerm1 = new VBox();
		vbUnselectedTerm1.setPadding(new Insets(15, 5, 5, 20));
		vbUnselectedTerm1.setSpacing(10);
		VBox vbReservedTerm1 = new VBox();
		vbReservedTerm1.setPadding(new Insets(15, 20, 5, 5));
		vbReservedTerm1.setSpacing(10);
		VBox vbUnselectedTerm2 = new VBox();
		vbUnselectedTerm2.setPadding(new Insets(15, 5, 5, 20));
		vbUnselectedTerm2.setSpacing(10);
		VBox vbReservedTerm2 = new VBox();
		vbReservedTerm2.setPadding(new Insets(15, 20, 5, 5));
		vbReservedTerm2.setSpacing(10);
		
		//label
		Label lblUnselectedTerm1 = new Label("Unselected Term 1 Modules");
		Label lblReservedTerm1 = new Label("Reserved Term 1 Modules");
		Label lblUnselectedTerm2 = new Label("Unselected Term 2 Modules");
		Label lblReservedTerm2 = new Label("Reserved Term 2 Modules");
		Label lblTerm1Credits = new Label("Reserve 30 credits worth of term 1 modules");
		Label lblTerm2Credits = new Label("Reserve 30 credits worth of term 2");
		
		//initialise ListView
		LVUnselectedTerm1 = new ListView<Module>();
		LVUnselectedTerm1.setPrefSize(400, 400);
		LVReservedTerm1 = new ListView<Module>();
		LVReservedTerm1.setPrefSize(400, 400);
		LVUnselectedTerm2 = new ListView<Module>();
		LVUnselectedTerm2.setPrefSize(400, 400);
		LVReservedTerm2 = new ListView<Module>();
		LVReservedTerm2.setPrefSize(400, 400);
		
		//initialise TextField
		txtTerm1 = new TextField();
		txtTerm1.setPrefWidth(50);
		txtTerm1.setEditable(false);
		txtTerm2 = new TextField();
		txtTerm2.setPrefWidth(50);
		txtTerm2.setEditable(false);
		
		//HB
		HBox hbTerm1 = new HBox();
		hbTerm1.setPadding(new Insets(5, 15, 10, 150));
		//hbTerm1.setAlignment(Pos.CENTER);
		hbTerm1.setSpacing(10);
		HBox hbTerm2 = new HBox();
		hbTerm2.setPadding(new Insets(5, 15, 10, 150));
		//hbTerm2.setAlignment(Pos.CENTER);
		hbTerm2.setSpacing(10);
		
		//Button
		btnAddT1 = new Button("Add");
		btnRemoveT1 = new Button("Remove");
		btnConfirmT1 = new Button("Confirm");
		btnAddT2 = new Button("Add");
		btnRemoveT2 = new Button("Remove");
		btnConfirmT2 = new Button("Confirm");
		
		//Term 1 HB
		hbTerm1.getChildren().addAll(lblTerm1Credits, btnAddT1, btnRemoveT1, btnConfirmT1, txtTerm1);
		hbTerm1.setAlignment(Pos.CENTER);
		hbTerm1.setSpacing(10);
		hbTerm2.getChildren().addAll(lblTerm2Credits, btnAddT2, btnRemoveT2, btnConfirmT2, txtTerm2);
		hbTerm2.setAlignment(Pos.CENTER);
		hbTerm2.setSpacing(10);
		
		vbUnselectedTerm1.getChildren().addAll(lblUnselectedTerm1, LVUnselectedTerm1);
		vbReservedTerm1.getChildren().addAll(lblReservedTerm1, LVReservedTerm1);
		vbUnselectedTerm2.getChildren().addAll(lblUnselectedTerm2, LVUnselectedTerm2);
		vbReservedTerm2.getChildren().addAll(lblReservedTerm2, LVReservedTerm2);
		
		//TitledPane
		tpTerm1 = new TitledPane();
		GridPane gp1 = new GridPane();
		gp1.setVgap(5);
		gp1.setHgap(5);
		gp1.add(vbUnselectedTerm1, 0, 0);
		gp1.add(vbReservedTerm1, 1, 0);
		gp1.add(hbTerm1, 0, 4, 2, 1);
		tpTerm1.setText("Term 1 modules");
		tpTerm1.setContent(gp1);
		tpTerm1.setExpanded(false);
		
		tpTerm2 = new TitledPane();
		GridPane gp2 = new GridPane();
		gp2.setVgap(5);
		gp2.setHgap(5);
		gp2.add(vbUnselectedTerm2, 0, 0);
		gp2.add(vbReservedTerm2, 1, 0);
		gp2.add(hbTerm2, 0, 4, 2, 1);
		tpTerm2.setText("Term 2 modules");
		tpTerm2.setContent(gp2);
		tpTerm2.setExpanded(false);
		
		accd = new Accordion();
		accd.setExpandedPane(tpTerm1);
		accd.getPanes().addAll(tpTerm1, tpTerm2);
	
		VBox root = new VBox();
		root.setPadding(new Insets(20, 10, 10, 10));
		//root.getChildren().addAll(tpTerm1, tpTerm2);
		root.getChildren().add(accd);
		
		this.add(root, 0, 0);
	}
	
	public Accordion getAccordion() {
		return accd;
	}
	
	public TitledPane getTpTerm2() {
		return tpTerm2;
	}
	
	public TextField getT1ReserveCredits() {
		return txtTerm1;
	}
	
	public TextField getT2ReserveCredits() {
		return txtTerm2;
	}
	
	public ObservableList<Module> getListT1() {
		return t1;
	}
	
	public ObservableList<Module> getListT2() {
		return t2;
	}
	
	public ObservableList<Module> getListT4() {
		return t4;
	}
	
	public ObservableList<Module> getListT5() {
		return t5;
	}
	
	public ListView<Module> getReserveT1() {
		return LVUnselectedTerm1;
	}
	
	public ListView<Module> getReserveT2() {
		return LVUnselectedTerm2;
	}
	
	public ListView<Module> getSelectedT1() {
		return LVReservedTerm1;
	}
	
	public ListView<Module> getSelectedT2() {
		return LVReservedTerm2;
	}
	
	public void addAddReserveModulesHandlerT1(EventHandler<ActionEvent> handler) {
		btnAddT1.setOnAction(handler);
	}
	
	public void addAddReserveModulesHandlerT2(EventHandler<ActionEvent> handler) {
		btnAddT2.setOnAction(handler);
	}
	
	public void addRemoveReserveModulesHandlerT1(EventHandler<ActionEvent> handler) {
		btnRemoveT1.setOnAction(handler);
	}
	
	public void addRemoveReserveModulesHandlerT2(EventHandler<ActionEvent> handler) {
		btnRemoveT2.setOnAction(handler);
	}
	
	public void addConfirmReserveModulesHandlerT1(EventHandler<ActionEvent> handler) {
		btnConfirmT1.setOnAction(handler);
	}
	
	public void addConfirmReserveModulesHandlerT2(EventHandler<ActionEvent> handler) {
		btnConfirmT2.setOnAction(handler);
	}
}
