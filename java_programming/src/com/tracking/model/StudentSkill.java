package com.tracking.model;

import java.time.LocalDate;

public class StudentSkill {
    private int studentSkillId;
    private Skill skill;
    private String proficiencyLevel; // Beginner, Intermediate, Advanced
    private LocalDate acquiredDate;

    public StudentSkill(int studentSkillId, Skill skill, String proficiencyLevel, LocalDate acquiredDate) {
        this.studentSkillId = studentSkillId;
        this.skill = skill;
        this.proficiencyLevel = proficiencyLevel;
        this.acquiredDate = acquiredDate;
    }

    public int getStudentSkillId() {
        return studentSkillId;
    }

    public Skill getSkill() {
        return skill;
    }

    public String getProficiencyLevel() {
        return proficiencyLevel;
    }

    public LocalDate getAcquiredDate() {
        return acquiredDate;
    }

    public void updateProficiency(String newLevel) {
        this.proficiencyLevel = newLevel;
    }

    @Override
    public String toString() {
        return String.format("%s (%s, Acquired: %s)", skill.getSkillName(), proficiencyLevel, acquiredDate);
    }
}
