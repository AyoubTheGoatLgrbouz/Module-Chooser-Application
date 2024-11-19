package view;


import java.util.Set;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import model.Block;
import model.Module;

public class ModuleSelectorPane extends VBox {
	private ListView<Module>  listSelecB1, listSelecB2, listUnsel ,listSelec;
	private TextField credit;
	private Button btnAdd, btnRemv;
	private Button btnRes, btnSubm;
	

	public ModuleSelectorPane() {
	
		
		VBox vBox1 = new VBox();
		
		VBox nn = new VBox();
		Label lblSelB1 = new Label("Selected Block 1 modules ");
		listSelecB1 = new ListView();
		nn.getChildren().addAll(lblSelB1,listSelecB1 );
		VBox.setVgrow(listSelecB1, Priority.ALWAYS);
		
		VBox nnn = new VBox();
		Label lblSelB2 = new Label("Selected Block 2 modules ");
		listSelecB2 = new ListView();
		nnn.getChildren().addAll(lblSelB2,listSelecB2 );
		VBox.setVgrow(listSelecB2, Priority.ALWAYS);
		
		vBox1.setSpacing(10);
		vBox1.getChildren().addAll(nn,nnn );
		
		
		VBox vBox2= new VBox();
	    
	    VBox vBoxa= new VBox();
		Label lbUnSel = new Label("Unselected Block 3/4 modules ");
		listUnsel = new ListView();
		vBoxa.setSpacing(10);
		VBox.setVgrow(listUnsel, Priority.ALWAYS);
				
		HBox hBox= new HBox();
		Label lbBtn = new Label("Block 3/4 ");
		btnAdd = new Button("Add");
		btnRemv = new Button("Remove");
		hBox.getChildren().addAll(lbBtn,btnAdd,btnRemv);
		hBox.setSpacing(10);
		hBox.setAlignment(Pos.CENTER);
		vBoxa.getChildren().addAll(lbUnSel,listUnsel,hBox );
		
		
		VBox vBoxb= new VBox();
		Label lblSel = new Label("Selected Block 3/4 modules ");
		listSelec = new ListView();
		vBoxb.getChildren().addAll(lblSel,listSelec);
		VBox.setVgrow(listSelec, Priority.ALWAYS);

		
		vBox2.setSpacing(10);
		vBox2.getChildren().addAll(vBoxa, vBoxb );
		
		
		
		HBox hBoxMain = new HBox();
		hBoxMain.getChildren().addAll(vBox1, vBox2);
		hBoxMain.setSpacing(30);
		HBox.setHgrow(vBox1, Priority.ALWAYS); 
		HBox.setHgrow(vBox2, Priority.ALWAYS);
		
		
		HBox kll = new HBox();
		Label lblSc = new Label("Current credits: ");
		credit = new TextField("0");
		credit.setEditable(false);
		credit.setPrefWidth(60);
		kll.setSpacing(15);
		kll.getChildren().addAll(lblSc, credit);
		kll.setAlignment(Pos.CENTER);
		kll.setPadding(new Insets(20,0,20,0));
		
		HBox BoxBtns = new HBox();
		
		btnRes = new Button("Reset");
		btnRes.setPrefSize( 70,70 );
		
		btnSubm = new Button("Submit");
		btnSubm.setPrefSize( 70,70 );
		
		BoxBtns.setSpacing(25);
		BoxBtns.getChildren().addAll(btnRes, btnSubm);
		BoxBtns.setAlignment(Pos.CENTER);
		
		
		this.getChildren().addAll(hBoxMain,kll,BoxBtns );
		this.setAlignment(Pos.CENTER);
		this.setPadding(new Insets(30,30,30,30));
		
	}
	  public ListView<Module> getListUnsel() {
	        return listUnsel;
	    }

	    public ListView<Module> getListSelec() {
	        return listSelec;
	    }

	    public ListView<Module> getListSelecB1() {
	        return listSelecB1;
	    }

	    public ListView<Module> getListSelecB2() {
	        return listSelecB2;
	    }

	    public TextField getCredit() {
	        return credit;
	    }

		public void clearSelectedModules() { 
			listSelec.getItems().clear(); 
			listSelecB1.getItems().clear(); 
			listSelecB2.getItems().clear(); 
			
		} 
		
		public void clearUnselectedModules() { 
			listUnsel.getItems().clear(); 
			
		} 
		public void addSelectedModule(Module module) { 
			if (module.getRunPlan() == Block.BLOCK_1) 
			{ listSelecB1.getItems().add(module); 
			} else if (module.getRunPlan() == Block.BLOCK_2) 
			{ listSelecB2.getItems().add(module); 
			} else { 
				listSelec.getItems().add(module); 
				
			} 
			
		} 
		public void addUnselectedModule(Module module) { 
			listUnsel.getItems().add(module); 
			
			}
		
		public void addSelectedModules(Set<Module> modules) { 
			for (Module module : modules) 
			{ addSelectedModule(module); } 
			} 
		
		
		public void addUnselectedModules(Set<Module> modules) {
		listUnsel.getItems().addAll(modules);
	
	}
		// Method to attach reset handler to the reset button 
		public void addResetHandler(EventHandler<ActionEvent> handler) {
			btnRes.setOnAction(handler);
		}
		
		public void addAddHandler(EventHandler<ActionEvent> handler) { 
			btnAdd.setOnAction(handler);
		}
		
		public void addRemoveHandler(EventHandler<ActionEvent> handler) { 
			btnRemv.setOnAction(handler);
		}
		
		public void addSubmitHandler(EventHandler<ActionEvent> handler) {
			btnSubm.setOnAction(handler);
		}
}
