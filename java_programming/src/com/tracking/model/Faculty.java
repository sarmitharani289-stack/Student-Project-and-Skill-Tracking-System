package com.tracking.model;

import java.util.ArrayList;
import java.util.List;

public class Faculty {
    private int facultyId;
    private String facultyName;
    private String email;
    private String department;
    private List<Project> assignedProjects;

    public Faculty(int facultyId, String facultyName, String email, String department) {
        this.facultyId = facultyId;
        this.facultyName = facultyName;
        this.email = email;
        this.department = department;
        this.assignedProjects = new ArrayList<>();
    }

    public int getFacultyId() {
        return facultyId;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public String getEmail() {
        return email;
    }

    public String getDepartment() {
        return department;
    }

    public List<Project> getAssignedProjects() {
        return assignedProjects;
    }

    public void assignProject(Project project) {
        if (!assignedProjects.contains(project)) {
            assignedProjects.add(project);
        }
    }

    public void viewProjects() {
        System.out.println("Assigned projects for Mentor " + facultyName + ": " + assignedProjects.size());
    }

    public void addRemarks(Milestone milestone, String remarks) {
        milestone.setRemarks(remarks);
    }

    @Override
    public String toString() {
        return String.format("%s (%s - %s)", facultyName, email, department);
    }
}
