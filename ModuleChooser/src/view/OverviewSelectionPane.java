package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import model.Module;
import model.StudentProfile;


public class OverviewSelectionPane extends VBox {
	private TextArea listPrf,  listSelec, listReser;
    private Button btnSave;
    private StudentProfile sp;  

	
	public OverviewSelectionPane() {
		

		listPrf = new TextArea("Profile will appear here");
		listPrf.setEditable(false);
		listPrf.setPrefSize(600,200 );
		
		
		listSelec = new TextArea("Selected modules will appear here");
		listSelec.setEditable(false);
		listSelec.setPrefSize(400,400 );
		
		listReser = new TextArea("Reserved modules will appear here");
		listReser.setEditable(false);
		listReser.setPrefSize(400,400 );
		
		VBox.setVgrow(listSelec, Priority.ALWAYS); 
		VBox.setVgrow(listReser, Priority.ALWAYS);
		
		HBox ltBox = new HBox();
		ltBox.getChildren().addAll(listSelec, listReser);
		ltBox.setSpacing(20) ;
		HBox.setHgrow(listSelec, Priority.ALWAYS); 
		HBox.setHgrow(listReser, Priority.ALWAYS);
		ltBox.setPadding(new Insets(0,0,20,0));
		
		VBox topContainer = new VBox(listPrf, ltBox);
		topContainer.setSpacing(20);
		VBox.setVgrow(topContainer, Priority.ALWAYS);
		topContainer.setPadding(new Insets(10,0,20,0));
		
		btnSave = new Button("Save Overview");
		btnSave.setPrefSize( 120,30 );
		
		
		
		this.getChildren().addAll(topContainer,ltBox , btnSave);
		this.setPadding(new Insets(20,20,20,20));
		
		this.setAlignment(Pos.CENTER);
		
	
		
		}
	
	
	
	 public static void updateOverviewPane(OverviewSelectionPane overviewPane, StudentProfile sp) {
	        if (sp != null) {
	            // Update Student Profile (listPrf)
	            String studentProfile = "Name: " + sp.getStudentName().getFullName() + "\n" +
	                                    "Email: " + sp.getStudentEmail() + "\n" +
	                                    "PNumber: " + sp.getStudentPnumber() + "\n" +
	                                    "Date: " + sp.getSubmissionDate()+ "\n" +
	                                    "Course: " + sp.getStudentCourse().getCourseName();
	                                  
	            overviewPane.listPrf.setText(studentProfile);

	            // Update Selected Modules (listSelec)
	            StringBuilder selectedModules = new StringBuilder("Selected Modules:\n"
	            		+ "========= \n");
	            for (Module module : sp.getAllSelectedModules()) {
	                selectedModules.append(module.DesignToString()).append("\n");
	            }
	            overviewPane.listSelec.setText(selectedModules.toString());

	            // Update Reserved Modules (listReser)
	            StringBuilder reservedModules = new StringBuilder("Reserved Modules:\n"
	            		+ "========= \n");
	            for (Module module : sp.getAllReservedModules()) {
	                reservedModules.append(module.DesignToString()).append("\n");
	            }
	            overviewPane.listReser.setText(reservedModules.toString());	        
	            } else {
	            System.out.println("ModuleSelectorPane (msp) is not initialized.");
	        }
	    }
	 
	 
	 
	 
	 public void addSaveOverviewHandler(EventHandler<ActionEvent> handler) { 
		 btnSave.setOnAction(handler);
		}
}
	


	

