package com.tracking.model;

public class Skill {
    private int skillId;
    private String skillName;
    private String skillCategory;

    public Skill(int skillId, String skillName, String skillCategory) {
        this.skillId = skillId;
        this.skillName = skillName;
        this.skillCategory = skillCategory;
    }

    public int getSkillId() {
        return skillId;
    }

    public String getSkillName() {
        return skillName;
    }

    public String getSkillCategory() {
        return skillCategory;
    }

    public void updateSkill(String skillName, String skillCategory) {
        this.skillName = skillName;
        this.skillCategory = skillCategory;
    }

    @Override
    public String toString() {
        return String.format("%s [%s]", skillName, skillCategory);
    }
}
