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
    private Project project;

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

    public Project getProject() {
        return project;
    }

    public void addSkill(StudentSkill skill) {
        skills.add(skill);
    }

    public void registerProject(Project project) {
        this.project = project;
    }

    public void viewProgress() {
        if (project != null) {
            System.out.printf("Student %s [%s] -> Project: %s, Progress: %.1f%%\n",
                    studentName, registerNumber, project.getTitle(), project.getProgress());
        } else {
            System.out.printf("Student %s [%s] -> No project registered currently.\n", studentName, registerNumber);
        }
    }

    public void updateProfile(String email, String phone) {
        this.email = email;
        this.phone = phone;
    }

    @Override
    public String toString() {
        return String.format("%s (%s, Year %d, %s)", studentName, registerNumber, yearOfStudy, department);
    }
}
