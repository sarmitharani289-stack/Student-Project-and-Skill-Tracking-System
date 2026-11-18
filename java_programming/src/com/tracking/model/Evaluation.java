package com.tracking.model;

import com.tracking.exception.InvalidMarkException;
import java.time.LocalDate;

public class Evaluation {
    private int evaluationId;
    private Faculty evaluator;
    private float requirementAnalysisMarks; // Max: 20
    private float designMarks;              // Max: 20
    private float implementationMarks;      // Max: 30
    private float documentationMarks;       // Max: 15
    private float presentationMarks;        // Max: 15
    private float totalMarks;               // Max: 100
    private float averageMarks;
    private LocalDate evaluationDate;

    public Evaluation(int evaluationId, Faculty evaluator, float reqMarks, float designMarks, 
                      float implMarks, float docMarks, float presMarks, LocalDate evaluationDate) 
                      throws InvalidMarkException {
        this.evaluationId = evaluationId;
        this.evaluator = evaluator;
        this.evaluationDate = evaluationDate;
        setMarks(reqMarks, designMarks, implMarks, docMarks, presMarks);
    }

    public void setMarks(float req, float design, float impl, float doc, float pres) throws InvalidMarkException {
        if (req < 0 || req > 20) throw new InvalidMarkException("Requirement Analysis marks must be between 0 and 20.");
        if (design < 0 || design > 20) throw new InvalidMarkException("System Design marks must be between 0 and 20.");
        if (impl < 0 || impl > 30) throw new InvalidMarkException("Implementation marks must be between 0 and 30.");
        if (doc < 0 || doc > 15) throw new InvalidMarkException("Documentation marks must be between 0 and 15.");
        if (pres < 0 || pres > 15) throw new InvalidMarkException("Presentation marks must be between 0 and 15.");

        this.requirementAnalysisMarks = req;
        this.designMarks = design;
        this.implementationMarks = impl;
        this.documentationMarks = doc;
        this.presentationMarks = pres;
        this.totalMarks = req + design + impl + doc + pres;
        this.averageMarks = this.totalMarks;
    }

    public int getEvaluationId() {
        return evaluationId;
    }

    public Faculty getEvaluator() {
        return evaluator;
    }

    public float getRequirementAnalysisMarks() {
        return requirementAnalysisMarks;
    }

    public float getDesignMarks() {
        return designMarks;
    }

    public float getImplementationMarks() {
        return implementationMarks;
    }

    public float getDocumentationMarks() {
        return documentationMarks;
    }

    public float getPresentationMarks() {
        return presentationMarks;
    }

    public float getTotalMarks() {
        return totalMarks;
    }

    public float getAverageMarks() {
        return averageMarks;
    }

    public LocalDate getEvaluationDate() {
        return evaluationDate;
    }

    public String getGrade() {
        if (totalMarks >= 90) return "O (Outstanding)";
        if (totalMarks >= 80) return "A+ (Excellent)";
        if (totalMarks >= 70) return "A (Very Good)";
        if (totalMarks >= 60) return "B+ (Good)";
        if (totalMarks >= 50) return "B (Average)";
        return "RA (Re-Appear)";
    }

    @Override
    public String toString() {
        return String.format("Score: %.1f/100 | Grade: %s | Date: %s | Mentor: %s", 
                totalMarks, getGrade(), evaluationDate, evaluator.getFacultyName());
    }
}
