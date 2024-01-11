package com.app.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Modules {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MODULE_ID")
    private int moduleId;

    @Column(name = "COURSE_ID")
    private int courseId;

    @Column(name = "MODULE_NAME")
    private String moduleName;

    @Column(name = "PREREQUISITE")
    private String prerequisites;

    @Column(name = "OUTCOME")
    private String outcome;

    @Column(name = "MODULE_NOTES")
    private String moduleNotes;

	public int getModuleId() {
		return moduleId;
	}

	public void setModuleId(int moduleId) {
		this.moduleId = moduleId;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getPrerequisites() {
		return prerequisites;
	}

	public void setPrerequisites(String prerequisites) {
		this.prerequisites = prerequisites;
	}

	public String getOutcome() {
		return outcome;
	}

	public void setOutcome(String outcome) {
		this.outcome = outcome;
	}

	public String getModuleNotes() {
		return moduleNotes;
	}

	public void setModuleNotes(String moduleNotes) {
		this.moduleNotes = moduleNotes;
	}

	@Override
	public String toString() {
		return "Modules [moduleId=" + moduleId + ", courseId=" + courseId + ", moduleName=" + moduleName
				+ ", prerequisites=" + prerequisites + ", outcome=" + outcome + ", moduleNotes=" + moduleNotes + "]";
	}
    

}



