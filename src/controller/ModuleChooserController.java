package controller;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import model.Block;
import model.Course;
import model.Module;
import model.StudentProfile;
import view.ModuleChooserRootPane;
import view.ModuleReserverPane;
import view.ModuleSelectorPane;
import view.OverviewSelectionPane;
import view.CreateStudentProfilePane;
import view.ModuleChooserMenuBar;

public class ModuleChooserController {

	//fields to be used throughout class
	private StudentProfile model;
	private ModuleChooserRootPane view;
	
	private CreateStudentProfilePane cspp;
	private ModuleSelectorPane msp;
	private ModuleChooserMenuBar mstmb;
	private ModuleReserverPane mrp;
	private OverviewSelectionPane osp;

	public ModuleChooserController(StudentProfile model, ModuleChooserRootPane view) {
		//initialise model and view fields
		this.model = model;
		this.view = view;
		
		//initialise view subcontainer fields
		cspp = view.getCreateStudentProfilePane();
		mstmb = view.getModuleSelectionToolMenuBar();
		msp = view.getModuleSelectorPane();
		mrp = view.getModuleReserverPane();
		osp = view.getOverviewSelectionPane();

		//add courses to combobox in create student profile pane using the generateAndGetCourses helper method below
		cspp.addCourseDataToComboBox(generateAndGetCourses());

		//attach event handlers to view using private helper method
		this.attachEventHandlers();
	}

	
	//helper method - used to attach event handlers
	private void attachEventHandlers() {
		//attach an event handler to the create student profile pane
		cspp.addCreateStudentProfileHandler(new CreateStudentProfileHandler());
		
		mstmb.addAboutHandler(new AboutHandler());
		mstmb.addSaveHandler(new SaveProfileHandler());
		mstmb.addLoadHandler(new LoadProfileHandler());
		
		msp.addResetHandler(new ResetHandler());
		msp.addAddHandler(new AddHandler());
		msp.addRemoveHandler(new RemoveHandler());
		msp.addSubmitHandler(new SubmitHandler());
		
		mrp.addAddHandlerr(new AddHandlerr());
		mrp.addRemoveHandlerr(new RemoveHandlerr());
		mrp.addConfirmHandler(new ConfirmHandler());
		
		osp.addSaveOverviewHandler(new SaveOverviewHandler());		
		//attach an event handler to the menu bar that closes the application
		mstmb.addExitHandler(e -> System.exit(0));
	}
	

	private class CreateStudentProfileHandler implements EventHandler<ActionEvent> {
	    @Override
	    public void handle(ActionEvent e) {

	        String pnumber = cspp.getStudentPnumber();
	        String email = cspp.getStudentEmail();

	        if (pnumber == null || pnumber.isEmpty() || !pnumber.matches("P\\d{1,7}")) {
	            Alert alert = new Alert(AlertType.ERROR, "Please enter a valid Pnumber starting with 'P' followed by up to 7 digits.");
	            alert.showAndWait();
	            return;
	        }

	        if (email == null || email.isEmpty() || !email.contains("@")) {
	            Alert alert = new Alert(AlertType.ERROR, "Please enter a valid email address.");
	            alert.showAndWait();
	            return;
	        }

	        if (cspp.getStudentName().getFullName().isEmpty() || cspp.getStudentDate() == null || cspp.getSelectedCourse() == null) {
	            Alert alert = new Alert(AlertType.ERROR, "Please complete all required fields.");
	            alert.showAndWait();
	            return;
	        }

	        model.setStudentPnumber(pnumber);
	        model.setStudentName(cspp.getStudentName());
	        model.setStudentEmail(email);
	        model.setSubmissionDate(cspp.getStudentDate());
	        model.setStudentCourse(cspp.getSelectedCourse());

	        populateSelectModulesTab();

	        Alert alert = new Alert(AlertType.INFORMATION, "Student profile created successfully!");
	        alert.showAndWait();

	        // Switch to the next tab
	        view.changeTab(1);
	    }
	}



	
	private void populateSelectModulesTab() { 
		msp.getCredit().setText("90");
		Course selectedCourse = model.getStudentCourse(); 
		if (selectedCourse != null) { 
			msp.clearSelectedModules();
			msp.clearUnselectedModules();
		for (Module module : selectedCourse.getAllModulesOnCourse()) { 
			if (module.isMandatory()) { 
				model.addSelectedModule(module); 
				msp.addSelectedModule(module); } 
			else { 
				msp.addUnselectedModule(module); 
				
			} 
		} 
		
		} 
		
	}
	
	
	private class AddHandler implements EventHandler<ActionEvent> {
	    @Override
	    public void handle(ActionEvent event) {
	        Module selectedModule = msp.getListUnsel().getSelectionModel().getSelectedItem();

	        if (selectedModule != null) {
	            if (model.addSelectedModule(selectedModule)) {
	                msp.addSelectedModule(selectedModule);
	                msp.getListUnsel().getItems().remove(selectedModule);
	                updateCurrentCredits();
	            } else {
	                showAlert("Module already selected or invalid!");
	            }
	        } else {
	            showAlert("Please select a module to add.");
	        }
	    }
	}
	
	private class ResetHandler implements EventHandler<ActionEvent> {
	    @Override
	    public void handle(ActionEvent event) {
	        
	        msp.clearSelectedModules();
	        msp.clearUnselectedModules();
	        msp.getCredit().setText("90");

	        model.clearSelectedModules();

	        populateSelectModulesTab();


	        
	        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Modules have been reset successfully.");
	        alert.showAndWait();
	    }
	}


	private class RemoveHandler implements EventHandler<ActionEvent> {
	    @Override
	    public void handle(ActionEvent event) {
	        Module selectedModule = msp.getListSelec().getSelectionModel().getSelectedItem();

	        if (selectedModule != null) {
	            if (model.getAllSelectedModules().remove(selectedModule)) {
	                msp.addUnselectedModule(selectedModule);
	                msp.getListSelec().getItems().remove(selectedModule);
	                updateCurrentCredits();
	            } else {
	                showAlert("Module not found in selected list!");
	            }
	        } else {
	            showAlert("Please select a module to remove.");
	        }
	    }
	}

	
	    private void updateCurrentCredits() {
	        int totalCredits = model.getAllSelectedModules().stream()
	                .mapToInt(Module::getModuleCredits)
	                .sum();
	        msp.getCredit().setText(String.valueOf(totalCredits));
	    }

	    private void showAlert(String message) {
	        Alert alert = new Alert(Alert.AlertType.WARNING);
	        alert.setHeaderText(null);
	        alert.setContentText(message);
	        alert.showAndWait();
	    }
	
	 
	    private class SubmitHandler implements EventHandler<ActionEvent> {
	        @Override
	        public void handle(ActionEvent event) {
	            int totalCredits = model.getAllSelectedModules().stream()
	                    .mapToInt(Module::getModuleCredits)
	                    .sum();

	            // Validate the total credits
	            if (totalCredits < 120) {
	                Alert alert = new Alert(AlertType.ERROR, "You must select modules with a total of at least 120 credits before submitting.");
	                alert.showAndWait();
	                return; 
	            }

	            Set<Module> selectedModules = new HashSet<>();
	            selectedModules.addAll(msp.getListSelecB1().getItems());
	            selectedModules.addAll(msp.getListSelecB2().getItems());
	            selectedModules.addAll(msp.getListSelec().getItems());

	            model.setAllSelectedModules(selectedModules);

	            Set<Module> unselectedModules = new HashSet<>(model.getStudentCourse().getAllModulesOnCourse());
	            unselectedModules.removeAll(selectedModules); 
	            mrp.clearUnselectedModules(); 
	            for (Module module : unselectedModules) {
	                mrp.addUnselectedModule(module); 
	            }

	            Alert alert = new Alert(AlertType.INFORMATION, "Modules submitted successfully!");
	            alert.showAndWait();
	            view.changeTab(2);
	        }
	    }


	
	    private class AddHandlerr implements EventHandler<ActionEvent> {
	        @Override
	        public void handle(ActionEvent event) {
	            Module selectedModule = mrp.getUnselectedList().getSelectionModel().getSelectedItem();

	            if (selectedModule != null) {
	                if (mrp.getReservedList().getItems().size() >= 1) {
	                    showAlert("Only one module can be reserved at a time.");
	                } else {
	                    mrp.addReservedModule(selectedModule);
	                    mrp.removeUnselectedModule(selectedModule);
	                }
	            } else {
	                showAlert("Please select a module to add.");
	            }
	        }
	    }

	    private class RemoveHandlerr implements EventHandler<ActionEvent> {
	        @Override
	        public void handle(ActionEvent event) {
	            Module selectedModule = mrp.getReservedList().getSelectionModel().getSelectedItem(); 

	            if (selectedModule != null) {
	               
	                mrp.removeReservedModule(selectedModule);
	                mrp.addUnselectedModule(selectedModule); 

	    
	           
	            } else {
	                showAlert("Please select a module to remove.");
	            }
	        }
	    }
	    
	    private class ConfirmHandler implements EventHandler<ActionEvent> {
	        @Override
	        public void handle(ActionEvent event) {
	            ListView<Module> reservedList = mrp.getReservedList();
	            Set<Module> reservedModules = new TreeSet<>(reservedList.getItems());
                view.changeTab(3);

	            if (!reservedModules.isEmpty()) {
	                for (Module module : reservedModules) {
	                	model.addReservedModule(module);
	                }

	                OverviewSelectionPane.updateOverviewPane(osp, model);
	            
	            } else {
	                showAlert("Please select a module to reserve.");
	               
	            
	            }
	        }
	    }
	    
	    private class SaveOverviewHandler implements EventHandler<ActionEvent> {
	        @Override
	        public void handle(ActionEvent event) {
	            String studentName = model.getStudentName() != null ? model.getStudentName().toString() : "N/A";
	            String studentEmail = model.getStudentEmail() != null ? model.getStudentEmail() : "N/A";
	            String studentPnumber = model.getStudentPnumber() != null ? model.getStudentPnumber() : "N/A";
	            String courseName = model.getStudentCourse() != null ? model.getStudentCourse().getCourseName() : "N/A";

	            Set<Module> selectedModules = model.getAllSelectedModules() != null ? model.getAllSelectedModules() : new HashSet<>();
	            Set<Module> reservedModules = model.getAllReservedModules() != null ? model.getAllReservedModules() : new HashSet<>();

	            StringBuilder overviewContent = new StringBuilder();

	            overviewContent.append("Student Overview\n");
	            overviewContent.append("===================\n\n");

	            overviewContent.append("Student Details\n");
	            overviewContent.append("===================\n");
	            overviewContent.append("Student Name: ").append(studentName).append("\n");
	            overviewContent.append("Student Email: ").append(studentEmail).append("\n");
	            overviewContent.append("Student PNumber: ").append(studentPnumber).append("\n");
	            overviewContent.append("Course: ").append(courseName).append("\n\n");

	            overviewContent.append("Selected Modules\n");
	            overviewContent.append("===================\n");
	            if (selectedModules.isEmpty()) {
	                overviewContent.append("No selected modules.\n");
	            } else {
	                for (Module module : selectedModules) {
	                    overviewContent.append(" - ").append(module.getModuleName())
	                                   .append(" (Credits: ").append(module.getModuleCredits()).append(")\n");
	                }
	            }
	            overviewContent.append("\n");

	            overviewContent.append("Reserved Modules\n");
	            overviewContent.append("===================\n");
	            if (reservedModules.isEmpty()) {
	                overviewContent.append("No reserved modules.\n");
	            } else {
	                for (Module module : reservedModules) {
	                    overviewContent.append(" - ").append(module.getModuleName())
	                                   .append(" (Credits: ").append(module.getModuleCredits()).append(")\n");
	                }
	            }
	            saveToFile(overviewContent.toString());
	        }

	     
	        private void saveToFile(String content) {
	            File file = new File("overview.txt");

	            try (FileOutputStream fos = new FileOutputStream(file)) {
	                fos.write(content.getBytes());
	                fos.flush();
	                System.out.println("Overview saved successfully to: " + file.getAbsolutePath());
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	    }

	        

	    private class SaveProfileHandler implements EventHandler<ActionEvent> {

	        @Override
	        public void handle(ActionEvent event) {
	            File file = new File(System.getProperty("user.home"), "studentProfile.ser");
	            try (FileOutputStream fileOut = new FileOutputStream(file);
	                 ObjectOutputStream out = new ObjectOutputStream(fileOut)) {

	                out.writeObject(model);

	                showAlert("Student profile saved successfully to: " + file.getAbsolutePath(), Alert.AlertType.INFORMATION);

	            } catch (IOException e) {
	                showAlert("Error saving student profile: " + e.getMessage(), Alert.AlertType.ERROR);
	                e.printStackTrace();
	            }
	        }

	        private void showAlert(String message, Alert.AlertType alertType) {
	            Alert alert = new Alert(alertType);
	            alert.setHeaderText(null);
	            alert.setContentText(message);
	            alert.showAndWait();
	        }
	    }

	    private class LoadProfileHandler implements EventHandler<ActionEvent> {

	        @Override
	        public void handle(ActionEvent event) {
	            File file = new File(System.getProperty("user.home"), "studentProfile.ser");
	            if (!file.exists()) {
	                showAlert("No saved profile found at: " + file.getAbsolutePath(), Alert.AlertType.WARNING);
	                return;
	            }

	            try (FileInputStream fileIn = new FileInputStream(file);
	                 ObjectInputStream in = new ObjectInputStream(fileIn)) {

	                model = (StudentProfile) in.readObject();

	                updateGUIWithLoadedData();
	                
	                showAlert("Student profile loaded successfully from: " + file.getAbsolutePath(), Alert.AlertType.INFORMATION);

	            } catch (IOException | ClassNotFoundException e) {
	                showAlert("Error loading student profile: " + e.getMessage(), Alert.AlertType.ERROR);
	                e.printStackTrace();
	            }
	        }

	        private void updateGUIWithLoadedData() {
	            cspp.setStudentPnumber(model.getStudentPnumber());
	            cspp.setStudentName(model.getStudentName());
	            cspp.setStudentEmail(model.getStudentEmail());
	            cspp.setStudentDate(model.getSubmissionDate());
	            cspp.setSelectedCourse(model.getStudentCourse());

	            msp.clearSelectedModules();
	            msp.clearUnselectedModules();

	            for (Module module : model.getStudentCourse().getAllModulesOnCourse()) {
	                if (model.getAllSelectedModules().contains(module)) {
	                    msp.addSelectedModule(module);
	                } else {
	                    msp.addUnselectedModule(module);
	                }
	            }

	            updateCurrentCredits();

	            mrp.clearReservedModules();
	            for (Module reservedModule : model.getAllReservedModules()) {
	                mrp.addReservedModule(reservedModule);
	                
	                Set<Module> unselectedModules = new HashSet<>(model.getStudentCourse().getAllModulesOnCourse());
	                unselectedModules.removeAll(model.getAllSelectedModules());
	                unselectedModules.removeAll(model.getAllReservedModules());
	                mrp.clearUnselectedModules(); 
	                for (Module module : unselectedModules) {
	                    mrp.addUnselectedModule(module); 
	                }

	                OverviewSelectionPane.updateOverviewPane(osp, model);
	            }
	        }

	        private void showAlert(String message, Alert.AlertType alertType) {
	            Alert alert = new Alert(alertType);
	            alert.setHeaderText(null);
	            alert.setContentText(message);
	            alert.showAndWait();
	        }
	    }



	    private class AboutHandler implements EventHandler<ActionEvent> {
	        @Override
	        public void handle(ActionEvent event) {
	            Alert aboutAlert = new Alert(AlertType.INFORMATION);

	            aboutAlert.setTitle("About Module Chooser");

	            aboutAlert.setHeaderText(null);

	            aboutAlert.setContentText(
	                    "Module Chooser Application\n\n"
	                    + "Description:\n"
	                    + "This application allows second-year undergraduate computing students to create a profile, "
	                    + "select final-year modules, and review their choices before confirming their module selections.\n\n"
	                    + "Developed By: Ayoub Ben Brahim\n"
	                    + "Version: 1.0"
	            );

	         
	            aboutAlert.showAndWait();
	        }
	    }

	
	
	
	
	//helper method - generates modules and course data and returns courses within an array
	private Course[] generateAndGetCourses() {
		Module ctec3701 = new Module("CTEC3701", "Software Development: Methods & Standards", 30, true, Block.BLOCK_1);

		Module ctec3702 = new Module("CTEC3702", "Big Data and Machine Learning", 30, true, Block.BLOCK_2);
		Module ctec3703 = new Module("CTEC3703", "Mobile App Development and Big Data", 30, true, Block.BLOCK_2);

		Module ctec3451 = new Module("CTEC3451", "Development Project", 30, true, Block.BLOCK_3_4);

		Module ctec3704 = new Module("CTEC3704", "Functional Programming", 30, false, Block.BLOCK_3_4);
		Module ctec3705 = new Module("CTEC3705", "Advanced Web Development", 30, false, Block.BLOCK_3_4);

		Module imat3711 = new Module("IMAT3711", "Privacy and Data Protection", 30, false, Block.BLOCK_3_4);
		Module imat3722 = new Module("IMAT3722", "Fuzzy Logic and Inference Systems", 30, false, Block.BLOCK_3_4);

		Module ctec3706 = new Module("CTEC3706", "Embedded Systems and IoT", 30, false, Block.BLOCK_3_4);


		Course compSci = new Course("Computer Science");
		compSci.addModule(ctec3701);
		compSci.addModule(ctec3702);
		compSci.addModule(ctec3451);
		compSci.addModule(ctec3704);
		compSci.addModule(ctec3705);
		compSci.addModule(imat3711);
		compSci.addModule(imat3722);

		Course softEng = new Course("Software Engineering");
		softEng.addModule(ctec3701);
		softEng.addModule(ctec3703);
		softEng.addModule(ctec3451);
		softEng.addModule(ctec3704);
		softEng.addModule(ctec3705);
		softEng.addModule(ctec3706);

		Course[] courses = new Course[2];
		courses[0] = compSci;
		courses[1] = softEng;

		return courses;
	}

	    }

