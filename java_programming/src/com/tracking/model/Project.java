package com.tracking.model;

import com.tracking.service.Trackable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Project implements Trackable {
    private int projectId;
    private String title;
    private String domain;
    private String description;
    private List<ProjectTechnology> technologies;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status; // Proposed, In-Progress, Completed, Under-Review
    private Faculty guide;
    private List<Milestone> milestones;
    private Evaluation evaluation;

    public Project(int projectId, String title, String domain, String description, 
                   LocalDate startDate, LocalDate endDate, String status, Faculty guide) {
        this.projectId = projectId;
        this.title = title;
        this.domain = domain;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.guide = guide;
        this.technologies = new ArrayList<>();
        this.milestones = new ArrayList<>();
        if (guide != null) {
            guide.assignProject(this);
        }
    }

    public int getProjectId() {
        return projectId;
    }

    public String getTitle() {
        return title;
    }

    public String getDomain() {
        return domain;
    }

    public String getDescription() {
        return description;
    }

    public List<ProjectTechnology> getTechnologies() {
        return technologies;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Faculty getGuide() {
        return guide;
    }

    public List<Milestone> getMilestones() {
        return milestones;
    }

    public Evaluation getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(Evaluation evaluation) {
        this.evaluation = evaluation;
    }

    public void addTechnology(String techName) {
        technologies.add(new ProjectTechnology(technologies.size() + 1, techName));
    }

    public void addMilestone(Milestone milestone) {
        milestones.add(milestone);
    }

    @Override
    public double getProgress() {
        if (milestones == null || milestones.isEmpty()) {
            return 0.0;
        }
        long completed = milestones.stream().filter(Milestone::isCompleted).count();
        return (double) completed / milestones.size() * 100.0;
    }

    @Override
    public String generateReport() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Project [%d]: %s (%s)\n", projectId, title, domain));
        sb.append(String.format("   Status: %s | Progress: %.1f%%\n", status, getProgress()));
        sb.append(String.format("   Guide: %s\n", guide != null ? guide.getFacultyName() : "Unassigned"));
        sb.append("   Technologies: ");
        for (int i = 0; i < technologies.size(); i++) {
            sb.append(technologies.get(i).getTechnologyName());
            if (i < technologies.size() - 1) sb.append(", ");
        }
        sb.append("\n");
        sb.append("   Milestones:\n");
        for (Milestone m : milestones) {
            sb.append("      * ").append(m.toString()).append("\n");
        }
        if (evaluation != null) {
            sb.append("   Evaluation: ").append(evaluation.toString()).append("\n");
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return String.format("[%d] %s (%s - %s) [%.0f%%]", projectId, title, domain, status, getProgress());
    }
}
