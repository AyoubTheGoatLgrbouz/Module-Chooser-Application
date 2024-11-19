package view;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.BorderPane;


public class ModuleChooserRootPane extends BorderPane {

	private CreateStudentProfilePane cspp;
	private ModuleChooserMenuBar mstmb;
	private ModuleSelectorPane msp;
	private TabPane tp;
	private ModuleReserverPane mrp;
	private OverviewSelectionPane osp;
	
	public ModuleChooserRootPane() {
		//create tab pane and disable tabs from being closed
		tp = new TabPane();
		tp.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		//create panes
		cspp = new CreateStudentProfilePane();
		msp = new ModuleSelectorPane();
		mrp = new ModuleReserverPane();
		osp = new OverviewSelectionPane();
		
		//create tabs with panes added
		Tab t1 = new Tab("Create Profile", cspp);
		Tab t2 = new Tab("Select Modules", msp);
		Tab t3 = new Tab("Reserve Modules",mrp);
		Tab t4 = new Tab("Overview Selection",osp );
		//add tabs to tab pane
		tp.getTabs().addAll(t1, t2,t3,t4);
		
		//create menu bar
		mstmb = new ModuleChooserMenuBar();
		
		//add menu bar and tab pane to this root pane
		this.setTop(mstmb);
		this.setCenter(tp);
		
	}

	//methods allowing sub-containers to be accessed by the controller.
	public CreateStudentProfilePane getCreateStudentProfilePane() {
		return cspp;
	}
	
	public ModuleChooserMenuBar getModuleSelectionToolMenuBar() {
		return mstmb;
	}
	
	public ModuleSelectorPane getModuleSelectorPane() {
		return msp;
	}
	
	public ModuleReserverPane getModuleReserverPane() {
		
		return mrp;
	}
	public OverviewSelectionPane getOverviewSelectionPane() {
		return osp;
	}
	
	
	//method to allow the controller to change tabs
	public void changeTab(int index) {
		tp.getSelectionModel().select(index);
	}
}
