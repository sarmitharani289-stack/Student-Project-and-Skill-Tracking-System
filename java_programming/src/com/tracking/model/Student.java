
package com.tracking.model;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private int studentId;
    private String registerNumber;
    private String studentName;
    private String department;
    private int yearOfStudy;
    private String email;
    private String phone;
    private List<StudentSkill> skills;
    private List<Project> projects = new ArrayList<>();

    public Student(int studentId, String registerNumber, String studentName,
            String department, int yearOfStudy, String email, String phone) {
        this.studentId = studentId;
        this.registerNumber = registerNumber;
        this.studentName = studentName;
        this.department = department;
        this.yearOfStudy = yearOfStudy;
        this.email = email;
        this.phone = phone;
        this.skills = new ArrayList<>();
        this.projects = new ArrayList<>();
    }

    public int getStudentId() {
        return studentId;
    }

    public String getRegisterNumber() {
        return registerNumber;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getDepartment() {
        return department;
    }

    public int getYearOfStudy() {
        return yearOfStudy;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public List<StudentSkill> getSkills() {
        return skills;
    }

    // Get all projects registered by the student
    public List<Project> getProjects() {
        return projects;
    }

    // Get the most recently registered project
    public Project getProject() {
        if (projects.isEmpty()) {
            return null;
        }
        return projects.get(projects.size() - 1);
    }

    public void addSkill(StudentSkill skill) {
        skills.add(skill);
    }

    // Register multiple projects without replacing previous projects
    public void registerProject(Project project) {
        if (project != null) {
            boolean exists = projects.stream()
                    .anyMatch(p -> p.getProjectId() == project.getProjectId());

            if (!exists) {
                projects.add(project);
            }
        }
    }

    public void viewProgress() {
        if (!projects.isEmpty()) {
            for (Project project : projects) {
                System.out.printf(
                        "Student %s [%s] -> Project: %s, Progress: %.1f%%\n",
                        studentName,
                        registerNumber,
                        project.getTitle(),
                        project.getProgress());
            }
        } else {
            System.out.printf(
                    "Student %s [%s] -> No project registered currently.\n",
                    studentName,
                    registerNumber);
        }
    }

    public void updateProfile(String email, String phone) {
        this.email = email;
        this.phone = phone;
    }

    @Override
    public String toString() {
        return String.format("%s (%s, Year %d, %s)",
                studentName, registerNumber, yearOfStudy, department);
    }
}
