package model;

import java.io.Serializable;
import java.util.Objects;


public class Module implements Comparable<Module> , Serializable {
	
	private String moduleCode;
	private String moduleName;
	private int moduleCredits;
	private boolean mandatory;
	private Block runPlan;
	
	public Module(String moduleCode, String moduleName, int moduleCredits, boolean mandatory, Block runPlan) {
		this.moduleCode = moduleCode;
		this.moduleName = moduleName;
		this.moduleCredits = moduleCredits;
		this.mandatory = mandatory;
		this.runPlan = runPlan;
	}

	public String getModuleCode() {
		return moduleCode;
	}

	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public int getModuleCredits() {
		return moduleCredits;
	}
	
	public void setModuleCredits(int moduleCredits) {
		this.moduleCredits = moduleCredits;
	}
	
	private String booleanToYesNo(boolean value) {
	    return value ? "Yes" : "No";
	}

	public boolean isMandatory() {
		return mandatory ;
	}
	
	public void setMandatory(boolean mandatory) {
		this.mandatory = mandatory ;
	}
	
	public Block getRunPlan() {
		return runPlan;
	}

	public void setRunPlan(Block runPlan) {
		this.runPlan = runPlan;
	}
	
	
	@Override
	public String toString() {
		//a non-standard toString that simply returns the module code and name,
		//so as to assist in displaying modules correctly in a ListView<Module> in the view
		//-Note- you may customise this if you wish to do so.
		return moduleCode + " : " + moduleName;
	}
	
	public String actualToString() {
		return "Module:[moduleCode=" + moduleCode + ", moduleName=" + moduleName 
				+", moduleCredits=" + moduleCredits + ", mandatory=" + mandatory + ", runPlan=" + runPlan + "]";
	}
	
	public String DesignToString() {
		return "Module code: " + moduleCode + 
                ", Module name: " + moduleName + "\n" +
                "Credits: " + moduleCredits + 
                ", Mandatory on your course? " + booleanToYesNo(mandatory) + 
                ", Block: " + runPlan  + "\n" ;
	}
	
	@Override
	public int compareTo(Module other) {
		int result = this.moduleCode.compareTo(other.moduleCode);
		
		if (result == 0) {
			result = Integer.compare(this.moduleCredits, other.moduleCredits);
			
			if (result == 0) {
				result = Boolean.compare(other.mandatory, this.mandatory);
				
				if (result == 0) {
					result = this.moduleName.compareTo(other.moduleName);
					
					if (result == 0) {
						result = this.runPlan.compareTo(other.runPlan);
					}
				}
				
			}
		}
		
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		return (obj instanceof Module other)
				&& this.mandatory == other.mandatory && this.moduleCredits == other.moduleCredits &&
				this.moduleCode.equals(other.moduleCode) && this.moduleName.equals(other.moduleName) &&
				this.runPlan.equals(other.runPlan);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(mandatory, moduleCredits, moduleCode, moduleName, runPlan);
	}
	
	
}
