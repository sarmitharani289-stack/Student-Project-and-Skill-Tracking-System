
package com.tracking.service;

import com.tracking.exception.InvalidMarkException;
import com.tracking.exception.ProjectNotFoundException;
import com.tracking.model.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class ProjectTrackingService {

    private final Map<Integer, Student> students = new LinkedHashMap<>();
    private final Map<Integer, Faculty> facultyMembers = new LinkedHashMap<>();
    private final Map<Integer, Skill> skills = new LinkedHashMap<>();
    private final Map<Integer, Project> projects = new LinkedHashMap<>();

    // Add student with duplicate register number validation
    public void addStudent(Student s) {

        if (s == null) {
            throw new IllegalArgumentException("Student cannot be null.");
        }

        if (s.getRegisterNumber() == null
                || s.getRegisterNumber().trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Register number cannot be empty.");
        }

        boolean duplicate = students.values().stream()
                .anyMatch(existing -> existing.getRegisterNumber()
                        .trim()
                        .equalsIgnoreCase(s.getRegisterNumber().trim()));

        if (duplicate) {
            throw new IllegalArgumentException(
                    "Register number already exists: "
                            + s.getRegisterNumber());
        }

        students.put(s.getStudentId(), s);
    }

    public void addFaculty(Faculty f) {
        facultyMembers.put(f.getFacultyId(), f);
    }

    public void addSkill(Skill sk) {
        skills.put(sk.getSkillId(), sk);
    }

    public void addProject(Project p) {
        projects.put(p.getProjectId(), p);
    }

    public Student getStudent(int id) {
        return students.get(id);
    }

    public Project getProject(int id) throws ProjectNotFoundException {
        Project p = projects.get(id);

        if (p == null) {
            throw new ProjectNotFoundException(
                    "Project with ID " + id + " does not exist.");
        }

        return p;
    }

    public Collection<Student> getAllStudents() {
        return Collections.unmodifiableCollection(students.values());
    }

    public Collection<Project> getAllProjects() {
        return Collections.unmodifiableCollection(projects.values());
    }

    public Collection<Skill> getAllSkills() {
        return Collections.unmodifiableCollection(skills.values());
    }

    // Filter projects by domain using Stream API
    public List<Project> getProjectsByDomain(String domain) {
        return projects.values().stream()
                .filter(p -> p.getDomain().toLowerCase()
                        .contains(domain.toLowerCase()))
                .collect(Collectors.toList());
    }

    // Get completed projects
    public List<Project> getCompletedProjects() {
        return projects.values().stream()
                .filter(p -> "Completed".equalsIgnoreCase(p.getStatus())
                        || p.getProgress() >= 100.0)
                .collect(Collectors.toList());
    }

    // Calculate average evaluation score
    public double getAverageEvaluationScore() {
        return projects.values().stream()
                .filter(p -> p.getEvaluation() != null)
                .mapToDouble(p -> p.getEvaluation().getTotalMarks())
                .average()
                .orElse(0.0);
    }

    // Identify skill gaps
    public Map<String, List<String>> identifySkillGaps() {

        Map<String, List<String>> gaps = new LinkedHashMap<>();

        for (Student s : students.values()) {

            if (s.getProject() != null) {

                String domain = s.getProject().getDomain().toLowerCase();

                Set<String> studentSkillNames = s.getSkills().stream()
                        .map(ss -> ss.getSkill().getSkillName().toLowerCase())
                        .collect(Collectors.toSet());

                List<String> missing = new ArrayList<>();

                if (domain.contains("ai") || domain.contains("ml")) {
                    if (!studentSkillNames.contains("machine learning")
                            && !studentSkillNames.contains("python")) {
                        missing.add("Machine Learning / Python");
                    }
                }

                if (domain.contains("web")) {
                    if (!studentSkillNames.contains("html/css/js")
                            && !studentSkillNames.contains("react.js")) {
                        missing.add("HTML/CSS/JS or Modern Frontend");
                    }
                }

                if (domain.contains("cloud")) {
                    if (!studentSkillNames.contains("docker & kubernetes")
                            && !studentSkillNames.contains("aws cloud")) {
                        missing.add("Cloud / Containerization (Docker/AWS)");
                    }
                }

                if (!missing.isEmpty()) {
                    gaps.put(
                            s.getStudentName() + " (" + s.getRegisterNumber() + ")",
                            missing);
                }
            }
        }

        return gaps;
    }

    // Technology usage ranking
    public Map<String, Long> getTechnologyUsageCounts() {

        return projects.values().stream()
                .flatMap(p -> p.getTechnologies().stream())
                .collect(Collectors.groupingBy(
                        ProjectTechnology::getTechnologyName,
                        Collectors.counting()))
                .entrySet()
                .stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new));
    }
}
