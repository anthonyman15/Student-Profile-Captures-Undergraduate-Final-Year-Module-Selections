package controller;

import static model.Schedule.TERM_1;
import static model.Schedule.TERM_2;
import static model.Schedule.YEAR_LONG;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Course;
import model.Schedule;
import model.Module;
import model.StudentProfile;
import view.ModuleSelectionToolRootPane;
import view.OverviewSelectionPane;
import view.ReserveModulesPane;
import view.CreateStudentProfilePane;
import view.SelectModulesPane;
import view.ModuleSelectionToolMenuBar;

public class ModuleSelectionToolController {

	// fields to be used throughout class
	private ModuleSelectionToolRootPane view;
	private StudentProfile model;

	private CreateStudentProfilePane cspp;
	private SelectModulesPane smp;
	private ReserveModulesPane rmp;
	private OverviewSelectionPane osp;
	private ModuleSelectionToolMenuBar mstmb;

	public ModuleSelectionToolController(ModuleSelectionToolRootPane view, StudentProfile model,
			SelectModulesPane module, ReserveModulesPane reserve, OverviewSelectionPane overview) {
		// initialise view and model fields
		this.view = view;
		this.model = model;

		// initialise view subcontainer fields
		cspp = view.getCreateStudentProfilePane();
		smp = view.getSelectModulesPane();
		rmp = view.getReserveModulesPane();
		osp = view.getOverviewSelectionPane();
		mstmb = view.getModuleSelectionToolMenuBar();

		// add courses to combobox in create student profile pane using the
		// generateAndReturnCourses helper method below
		cspp.addCoursesToComboBox(generateAndReturnCourses());

		// attach event handlers to view using private helper method
		this.attachEventHandlers();
	}
	
	public void populateList(StudentProfile model) {
		Collection<Module> modules = model.getStudentCourse().getAllModulesOnCourse();
		smp.getUnselectedT1().setItems(smp.getListT1());
		smp.getUnselectedT2().setItems(smp.getListT2());
		smp.getYearLongModule().setItems(smp.getListT3());
		smp.getSelectedT1().setItems(smp.getListT4());
		smp.getSelectedT2().setItems(smp.getListT5());
		rmp.getReserveT1().setItems(rmp.getListT1());
		rmp.getReserveT2().setItems(rmp.getListT2());
		rmp.getSelectedT1().setItems(rmp.getListT4());
		rmp.getSelectedT2().setItems(rmp.getListT5());
		smp.getListT1().setAll(modules.stream().filter(p -> p.getDelivery() == TERM_1 && !p.isMandatory()).collect(Collectors.toCollection(ArrayList::new)));
		smp.getListT2().setAll(modules.stream().filter(p -> p.getDelivery() == TERM_2 && !p.isMandatory()).collect(Collectors.toCollection(ArrayList::new)));
		smp.getListT3().setAll(modules.stream().filter(p -> p.getDelivery() == YEAR_LONG).collect(Collectors.toCollection(ArrayList::new)));
		smp.getListT4().setAll(modules.stream().filter(p -> p.getDelivery() == TERM_1 && p.isMandatory()).collect(Collectors.toCollection(ArrayList::new)));
		smp.getListT5().setAll(modules.stream().filter(p -> p.getDelivery() == TERM_2 && p.isMandatory()).collect(Collectors.toCollection(ArrayList::new)));
	}

	// helper method - used to attach event handlers
	private void attachEventHandlers() {
		// attach an event handler to the create student profile pane
		cspp.addCreateStudentProfileHandler(new CreateStudentProfileHandler());

		// attach events to the select modules pane
		smp.addAddHandlerT1(new AddHandlerT1());
		
		smp.addAddHandlerT2(new AddHandlerT2());
		
		smp.addRemoveHandlerT1(new RemoveHandlerT1());
		
		smp.addRemoveHandlerT2(new RemoveHandlerT2());
		
		smp.addResetHandler(new ResetHandler());
		
		smp.addSubmitHandler(new SubmitHandler());
		
		rmp.addAddReserveModulesHandlerT1(new AddReserveModulesHandlerT1());
		
		rmp.addAddReserveModulesHandlerT2(new AddReserveModulesHandlerT2());
		
		rmp.addRemoveReserveModulesHandlerT1(new RemoveReserveModulesHandlerT1());
		
		rmp.addRemoveReserveModulesHandlerT2(new RemoveReserveModulesHandlerT2());
		
		rmp.addConfirmReserveModulesHandlerT1(new ConfirmReserveModulesHandlerT1());
		
		rmp.addConfirmReserveModulesHandlerT2(new ConfirmReserveModulesHandlerT2());
		
		osp.addSaveOverviewButton(new SaveOverviewButton());
		// attach an event handler to the menu bar that closes the application
		mstmb.addExitHandler(e -> System.exit(0));
		
		mstmb.addAboutHandler(new AboutHandler());
		
		mstmb.addSaveHandler(new SaveHandler());
		
		mstmb.addLoadHandler(new LoadHandler());
	}

	private void getStudent() {
		smp.getListT4().getClass();
	}
	// event handler (currently empty), which can be used for creating a profile
	private class CreateStudentProfileHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			model.setStudentName(view.getCreateStudentProfilePane().getStudentName());
			model.setStudentPnumber(view.getCreateStudentProfilePane().getStudentPnumber());
			model.setStudentEmail(view.getCreateStudentProfilePane().getStudentEmail());
			model.setSubmissionDate(view.getCreateStudentProfilePane().getStudentDate());
			model.setStudentCourse(view.getCreateStudentProfilePane().getSelectedCourse());
			model.getStudentCourse().getAllModulesOnCourse();
			populateList(model);
			view.changeTab(1);
		}
	}

	//Term 1 add button (Select Modules Pane)
	private class AddHandlerT1 implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			if (smp.getListT4().size() == 3)
				return;
			Module unselectModuleT1 = smp.getUnselectedT1().getSelectionModel().getSelectedItem();
			if (unselectModuleT1 == null)
				return;			
				smp.getListT1().remove(unselectModuleT1);
				smp.getListT4().add(unselectModuleT1);
				SmpT1Credits(smp.getListT4(), smp.getT1Credits());
		}
	}
	
	//Term 2 add button (Select Modules Pane)
	private class AddHandlerT2 implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			if(smp.getListT5().size() == 3) return;
			Module unselectModuleT2 = smp.getUnselectedT2().getSelectionModel().getSelectedItem();
			if(unselectModuleT2 == null) return;
				smp.getListT2().remove(unselectModuleT2);
				smp.getListT5().add(unselectModuleT2);
				SmpT1Credits(smp.getListT5(), smp.getT2Credits());
		}
	}
	
	//Calculate the Term 1 Credits
	private void SmpT1Credits(ObservableList<Module> modules, TextField txtModules) {
		int i = 0;	
		for (Module w : smp.getListT3())
		i += w.getModuleCredits() / 2;
		for (Module w : modules)
		i += w.getModuleCredits();		
		txtModules.setText("" + i);
	}
	
	//Calculate the Term 2 Credits
	private void SmpT2Credits(ObservableList<Module> modules, TextField txtModules) {
		int i = 0;	
		for (Module w : smp.getListT3())
		i += w.getModuleCredits() / 2;
		for (Module w : modules)
		i += w.getModuleCredits();		
		txtModules.setText("" + i);
	}
	
	//Term 1 remove button (Select Modules Pane)
	private class RemoveHandlerT1 implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			if(smp.getListT4().size() == 1) return;
			Module selectedModuleT1 = smp.getSelectedT1().getSelectionModel().getSelectedItem();
			if(selectedModuleT1 == null) return;
			if(selectedModuleT1.isMandatory()) return;
			if(selectedModuleT1.isMandatory() == false) {
				smp.getListT4().remove(selectedModuleT1);
				smp.getListT1().add(selectedModuleT1);
				SmpT1Credits(smp.getListT4(), smp.getT1Credits());
			}
		}
	}
	
	//Term2 remove button (Select Modules Pane)
	private class RemoveHandlerT2 implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			if(smp.getListT5().size() == 0) return;
			Module selectedModuleT2 = smp.getSelectedT2().getSelectionModel().getSelectedItem();
			if(selectedModuleT2 == null) return;
			if(selectedModuleT2.isMandatory()) return;
			if(selectedModuleT2.isMandatory() == false) {
				smp.getListT5().remove(selectedModuleT2);
				smp.getListT2().add(selectedModuleT2);
				SmpT2Credits(smp.getListT5(), smp.getT2Credits());
			}
		}
	}
	
	//Reset button (Select Modules Pane)
	private class ResetHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			model.getStudentCourse().getAllModulesOnCourse();
			smp.getSelectedT1().getItems().clear();
			smp.getSelectedT2().getItems().clear();
			SmpT1Credits(smp.getListT4(), smp.getT1Credits());
			SmpT1Credits(smp.getListT5(), smp.getT2Credits());
			populateList(model);
		}
	}
	//Submit button (Select Modules Pane)
	private class SubmitHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			for (Module w : smp.getSelectedT1().getItems()) {
				  model.addSelectedModule(w);
			}
			
			for (Module w : smp.getSelectedT2().getItems()) {
				  model.addSelectedModule(w);
			}		
			
			if(smp.getListT4().size() == 3 && smp.getListT5().size() == 3) {
				smp.getUnselectedT1().getItems();
				for (Module w : smp.getListT1()) {
					rmp.getListT1().addAll(w);
				}
				for (Module w : smp.getListT2()) {
					rmp.getListT2().add(w);
				}
				view.changeTab(2);
				smp.getSubmitButton().setDisable(true);;
			}
			else {
				return;
			}
			//System.out.println(model.getAllSelectedModules());
		}
	}
	
	//Add Button Term 1 for Reserved Module
	private class AddReserveModulesHandlerT1 implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			if (rmp.getListT4().size() == 2)
				return;
			Module unselectReserveModuleT1 = rmp.getReserveT1().getSelectionModel().getSelectedItem();
			if (unselectReserveModuleT1 == null)
				return;			
			if (unselectReserveModuleT1.isMandatory() == false) {
				rmp.getListT1().remove(unselectReserveModuleT1);
				rmp.getListT4().add(unselectReserveModuleT1);
				RmpT1Credits(rmp.getListT4(), rmp.getT1ReserveCredits());
			}
		}
	}
	
	//Add Button Term 2 for Reserved Module
	private class AddReserveModulesHandlerT2 implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			if (cspp.getSelectedCourse().getCourseName() == "Software Engineering")
			{ 
			if (rmp.getListT5().size() == 2)
				return;
			Module unselectReserveModuleT2 = rmp.getReserveT2().getSelectionModel().getSelectedItem();
			if (unselectReserveModuleT2 == null)
				return;
			if (unselectReserveModuleT2.isMandatory() == false) {
				rmp.getListT2().remove(unselectReserveModuleT2);
				rmp.getListT5().add(unselectReserveModuleT2);
				RmpT2Credits(rmp.getListT5(), rmp.getT2ReserveCredits());
				}
			}
			else {
				if (rmp.getListT5().size() == 3)
					return;
				Module unselectReserveModuleT2 = rmp.getReserveT2().getSelectionModel().getSelectedItem();
				if (unselectReserveModuleT2 == null)
					return;
				if (unselectReserveModuleT2.isMandatory() == false) {
					rmp.getListT2().remove(unselectReserveModuleT2);
					rmp.getListT5().add(unselectReserveModuleT2);
					RmpT2Credits(rmp.getListT5(), rmp.getT2ReserveCredits());
					}
			}
		}
	}
	
	//Remove Button Term 1 for Reserved Module
	private class RemoveReserveModulesHandlerT1 implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			if(rmp.getListT4().size() == 0) return;
			Module selectedReserveModuleT1 = rmp.getSelectedT1().getSelectionModel().getSelectedItem();
			if(selectedReserveModuleT1 == null) return;
			if(selectedReserveModuleT1.isMandatory()) return;
			if(selectedReserveModuleT1.isMandatory() == false) {
				rmp.getListT4().remove(selectedReserveModuleT1);
				rmp.getListT1().add(selectedReserveModuleT1);
				RmpT1Credits(rmp.getListT4(), rmp.getT1ReserveCredits());
			}
		}
	}
	
	//Remove Button Term 2 for Reserved Module
	private class RemoveReserveModulesHandlerT2 implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			if(rmp.getListT5().size() == 0) return;
			Module selectedReserveModuleT1 = rmp.getSelectedT2().getSelectionModel().getSelectedItem();
			if(selectedReserveModuleT1 == null) return;
			if(selectedReserveModuleT1.isMandatory()) return;
			if(selectedReserveModuleT1.isMandatory() == false) {
				rmp.getListT5().remove(selectedReserveModuleT1);
				rmp.getListT2().add(selectedReserveModuleT1);
				RmpT2Credits(rmp.getListT5(), rmp.getT2ReserveCredits());
			}
		}
	}
	
	//Confirm Button Term 1 for Reserved Module
	private class ConfirmReserveModulesHandlerT1 implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			if (rmp.getListT4().size() != 2) return;
			for (Module w : rmp.getSelectedT1().getItems())
			model.addReservedModule(w);
			rmp.getAccordion().setExpandedPane(rmp.getTpTerm2());
			//System.out.println(model.getAllReservedModules());
		}
	}
	
	private class ConfirmReserveModulesHandlerT2 implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			if (cspp.getSelectedCourse().getCourseName() == "Software Engineering") {
				if (rmp.getListT5().size() != 2) return;
				for (Module w : rmp.getSelectedT2().getItems())
					model.addReservedModule(w);
			}else {
				if (rmp.getListT5().size() != 3) return;
				for (Module w : rmp.getSelectedT2().getItems())
					model.addReservedModule(w);
			}
			addOverview();
			view.changeTab(3);
		}
	}
	
	//Calculate the Term 1 Credits
	private void RmpT1Credits(ObservableList<Module> modules, TextField txtModules) {
		int i = 0;	
		for (Module w : modules)
		i += w.getModuleCredits();		
		txtModules.setText("" + i);
	}
	
	//Calculate the Term 2 Credits
	private void RmpT2Credits(ObservableList<Module> modules, TextField txtModules) {
		int i = 0;
		for (Module w : modules) {
		i += w.getModuleCredits();
		txtModules.setText("" + i);
		}
	}
	
	private void addOverview() {		
		osp.getStudentProfile().setText(
				"First Name: " + model.getStudentName().getFirstName() + "\n" +
				"Surname: " + model.getStudentName().getFamilyName() + "\n" +
				"P-Number: " + model.getStudentPnumber() + "\n" +
				"Email: " + model.getStudentEmail() + "\n" +
				"Date: " + model.getSubmissionDate() + "\n" +
				"Course: " + model.getStudentCourse());
		
		String select = "";
		for (Module a : model.getAllSelectedModules()) {
			select += a.toString() + "\n";
		}
		osp.getSelectedModules().setText(select);
		
		String reserve = "";
		for (Module a : model.getAllReservedModules()) {
			reserve += a.toString() + "\n";
		}
		osp.getReservedModules().setText(reserve);
	}
	
	private class SaveOverviewButton implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			SaveOverview();
			osp.SaveOverviewButton().setDisable(true);;
		}
	}
	
	private void SaveOverview() {
		File student = new File("./student.txt");
				try {
					if(student.exists() == false) {
						System.out.println("new file created");
						student.createNewFile();
					}
					PrintWriter record = new PrintWriter(new FileWriter(student, true));
					record.println(model.getStudentName().getFirstName() + " " + model.getStudentName().getFamilyName());
					record.println(model.getStudentPnumber());
					record.println(model.getStudentCourse());
					record.println(model.getStudentEmail());
					record.println(model.getSubmissionDate());
					record.println("Selected Modules:");
					for(Module a : model.getAllSelectedModules()) {
						record.println(a);
					}
					record.println("Reserved Modules:");
					for(Module a : model.getAllReservedModules()) {
						record.println(a);
					}
					record.println("\n");
				record.close();
				} catch(IOException e) {
					System.out.println("File Cannot Create");
				}
	}
	
	private class AboutHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			Stage window = new Stage();
			String fileContent = "";
			File file = new File("about.txt");
			Scanner scan = null;
			try {
				scan = new Scanner(file);
				while(scan.hasNextLine()) {
					fileContent = fileContent.concat(scan.nextLine() + "\n");
				}
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} finally {
				if (scan != null) scan.close();
			}
			
			window.initModality(Modality.APPLICATION_MODAL);
			window.setTitle("About");
			window.setMinWidth(250);
			
			Label About = new Label();
			About.setText("About");
			About.setFont(new Font("About", 20));
			Label Info = new Label();
			Info.setText(fileContent);
			Info.setFont(new Font(fileContent, 18));
			
			VBox vbLayout = new VBox();
			vbLayout.getChildren().addAll(About, Info);
			vbLayout.setAlignment(Pos.CENTER);
			
			Scene scene = new Scene(vbLayout);
			window.setScene(scene);
			window.showAndWait();
		}
	}
	
	//Save Handler
	private class SaveHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			ObjectOutputStream oos = null;
			File student = new File("./StudentProfile.dat");
			try {
				if(student.exists()) {
					student.delete();
				}
				oos = new ObjectOutputStream(new FileOutputStream(student, true));
				oos.writeObject(model);
			} catch (IOException e1) {
				System.out.println(e1.getMessage());
			}finally {
				try {
					oos.close();
				} catch (Exception e1){
					System.out.println(e1.getMessage());
				}
			}
		}
	}
	
	//Load Handler
	private class LoadHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			try {
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream("./StudentProfile.dat"));
				model = (StudentProfile) ois.readObject();
				ois.close();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}
		}
	}

	// helper method - generates course and module data and returns courses within
	// an array
	private Course[] generateAndReturnCourses() {
		Module imat3423 = new Module("IMAT3423", "Systems Building: Methods", 15, true, Schedule.TERM_1);
		Module ctec3451 = new Module("CTEC3451", "Development Project", 30, true, Schedule.YEAR_LONG);
		Module ctec3902_SoftEng = new Module("CTEC3902", "Rigorous Systems", 15, true, Schedule.TERM_2);
		Module ctec3902_CompSci = new Module("CTEC3902", "Rigorous Systems", 15, false, Schedule.TERM_2);
		Module ctec3110 = new Module("CTEC3110", "Secure Web Application Development", 15, false, Schedule.TERM_1);
		Module ctec3605 = new Module("CTEC3605", "Multi-service Networks 1", 15, false, Schedule.TERM_1);
		Module ctec3606 = new Module("CTEC3606", "Multi-service Networks 2", 15, false, Schedule.TERM_2);
		Module ctec3410 = new Module("CTEC3410", "Web Application Penetration Testing", 15, false, Schedule.TERM_2);
		Module ctec3904 = new Module("CTEC3904", "Functional Software Development", 15, false, Schedule.TERM_2);
		Module ctec3905 = new Module("CTEC3905", "Front-End Web Development", 15, false, Schedule.TERM_2);
		Module ctec3906 = new Module("CTEC3906", "Interaction Design", 15, false, Schedule.TERM_1);
		Module ctec3911 = new Module("CTEC3911", "Mobile Application Development", 15, false, Schedule.TERM_1);
		Module imat3410 = new Module("IMAT3104", "Database Management and Programming", 15, false, Schedule.TERM_2);
		Module imat3406 = new Module("IMAT3406", "Fuzzy Logic and Knowledge Based Systems", 15, false, Schedule.TERM_1);
		Module imat3611 = new Module("IMAT3611", "Computer Ethics and Privacy", 15, false, Schedule.TERM_1);
		Module imat3613 = new Module("IMAT3613", "Data Mining", 15, false, Schedule.TERM_1);
		Module imat3614 = new Module("IMAT3614", "Big Data and Business Models", 15, false, Schedule.TERM_2);
		Module imat3428_CompSci = new Module("IMAT3428", "Information Technology Services Practice", 15, false,
				Schedule.TERM_2);

		Course compSci = new Course("Computer Science");
		compSci.addModuleToCourse(imat3423);
		compSci.addModuleToCourse(ctec3451);
		compSci.addModuleToCourse(ctec3902_CompSci);
		compSci.addModuleToCourse(ctec3110);
		compSci.addModuleToCourse(ctec3605);
		compSci.addModuleToCourse(ctec3606);
		compSci.addModuleToCourse(ctec3410);
		compSci.addModuleToCourse(ctec3904);
		compSci.addModuleToCourse(ctec3905);
		compSci.addModuleToCourse(ctec3906);
		compSci.addModuleToCourse(ctec3911);
		compSci.addModuleToCourse(imat3410);
		compSci.addModuleToCourse(imat3406);
		compSci.addModuleToCourse(imat3611);
		compSci.addModuleToCourse(imat3613);
		compSci.addModuleToCourse(imat3614);
		compSci.addModuleToCourse(imat3428_CompSci);

		Course softEng = new Course("Software Engineering");
		softEng.addModuleToCourse(imat3423);
		softEng.addModuleToCourse(ctec3451);
		softEng.addModuleToCourse(ctec3902_SoftEng);
		softEng.addModuleToCourse(ctec3110);
		softEng.addModuleToCourse(ctec3605);
		softEng.addModuleToCourse(ctec3606);
		softEng.addModuleToCourse(ctec3410);
		softEng.addModuleToCourse(ctec3904);
		softEng.addModuleToCourse(ctec3905);
		softEng.addModuleToCourse(ctec3906);
		softEng.addModuleToCourse(ctec3911);
		softEng.addModuleToCourse(imat3410);
		softEng.addModuleToCourse(imat3406);
		softEng.addModuleToCourse(imat3611);
		softEng.addModuleToCourse(imat3613);
		softEng.addModuleToCourse(imat3614);

		Course[] courses = new Course[2];
		courses[0] = compSci;
		courses[1] = softEng;

		return courses;
	}

}
