package com.tracking.model;

public class ProjectTechnology {
    private int technologyId;
    private String technologyName;

    public ProjectTechnology(int technologyId, String technologyName) {
        this.technologyId = technologyId;
        this.technologyName = technologyName;
    }

    public int getTechnologyId() {
        return technologyId;
    }

    public String getTechnologyName() {
        return technologyName;
    }

    @Override
    public String toString() {
        return technologyName;
    }
}
