package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import model.Module;

public class ModuleReserverPane extends VBox {
    private ListView<Module> listUnselec, listReserv ;
    private Button btnAdd, btnRemv, btnConfr;
	
	public ModuleReserverPane() {
		
		this.setAlignment(Pos.CENTER);
		this.setPadding(new Insets(30,30,30,30));
		
		VBox unselBox = new VBox();
		Label lblUnsel = new Label("Unselected Block 3/4 modules ");
		listUnselec = new ListView();
		unselBox.getChildren().addAll(lblUnsel,listUnselec);
		
	
		VBox selBox = new VBox();
		Label lblsel = new Label("Reserved Block 3/4 modules ");
		listReserv = new ListView();
		selBox.getChildren().addAll(lblsel,listReserv );
		VBox.setVgrow(unselBox, Priority.ALWAYS);
		VBox.setVgrow(selBox, Priority.ALWAYS); 
		
		HBox mainB = new HBox();
		mainB.getChildren().addAll(unselBox, selBox);
		mainB.setSpacing(20);
		mainB.setPadding(new Insets(0,0,20,0));
		HBox.setHgrow(unselBox, Priority.ALWAYS); 
		HBox.setHgrow(selBox, Priority.ALWAYS);
		
		
		HBox mainButn = new HBox();
		btnAdd = new Button("Add");
		btnAdd.setPrefSize( 90,30 );
		
		btnRemv = new Button("Remove");
		btnRemv.setPrefSize( 90,30 );
		
		btnConfr = new Button("Confirm");
		btnConfr.setPrefSize( 90,30 );
		
		Label lblBtn = new Label("Reserve one optional module");
		mainButn.setSpacing(10);
		mainButn.getChildren().addAll(lblBtn,btnAdd, btnRemv,btnConfr);
		mainButn.setAlignment(Pos.CENTER);
	
		
		this.getChildren().addAll(mainB,mainButn );
		
		
	}
	// Method to add an unselected module to the list of unselected modules
	public void addUnselectedModule(Module module) {
	    listUnselec.getItems().add(module);
	}

	public void addReservedModule(Module module) {
	    listReserv.getItems().add(module);
	}

    // Method to clear the list of unselected modules
    public void clearUnselectedModules() {
        listUnselec.getItems().clear();
    }

    // Method to clear the list of reserved modules
    public void clearReservedModules() {
        listReserv.getItems().clear();
    }

 
    // Method to remove a reserved module from the reserved list
    public void removeReservedModule(Module module) {
        listReserv.getItems().remove(module);
    }
    
    public void removeUnselectedModule(Module module) {
    	listUnselec.getItems().remove(module);
    }

    // Getters for the lists (in case you need them for the controller)
    public ListView<Module> getUnselectedList() {
        return listUnselec;
    }

    public ListView<Module> getReservedList() {
        return listReserv;
    }

    // Getters for the buttons (for event handlers)
    public Button getAddButton() {
        return btnAdd;
    }

    public Button getRemoveButton() {
        return btnRemv;
    }

    public Button getConfirmButton() {
        return btnConfr;
    }
	
    public void addAddHandlerr(EventHandler<ActionEvent> handler) {
    	btnAdd.setOnAction(handler);
	}
    
    public void addRemoveHandlerr(EventHandler<ActionEvent> handler) {
    	btnRemv.setOnAction(handler);
	}
    
    public void addConfirmHandler(EventHandler<ActionEvent> handler) {
    	btnConfr.setOnAction(handler);
	}
}
