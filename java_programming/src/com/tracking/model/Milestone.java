package com.tracking.model;

import java.time.LocalDate;

public class Milestone {
    private int milestoneId;
    private String milestoneName;
    private LocalDate submissionDate;
    private String completionStatus; // Pending, In-Progress, Completed, Delayed
    private String remarks;

    public Milestone(int milestoneId, String milestoneName, LocalDate submissionDate, String completionStatus, String remarks) {
        this.milestoneId = milestoneId;
        this.milestoneName = milestoneName;
        this.submissionDate = submissionDate;
        this.completionStatus = completionStatus;
        this.remarks = remarks;
    }

    public int getMilestoneId() {
        return milestoneId;
    }

    public String getMilestoneName() {
        return milestoneName;
    }

    public LocalDate getSubmissionDate() {
        return submissionDate;
    }

    public String getCompletionStatus() {
        return completionStatus;
    }

    public String getRemarks() {
        return remarks;
    }

    public void updateStatus(String status) {
        this.completionStatus = status;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public boolean isCompleted() {
        return "Completed".equalsIgnoreCase(completionStatus);
    }

    @Override
    public String toString() {
        return String.format("[%s] %s (Due: %s) - %s", completionStatus, milestoneName, submissionDate, remarks);
    }
}
