-- ============================================================================
-- DATABASE SCHEMA: Student Project and Skill Tracking System
-- Scenario 2 | Regulation R2025 | 25ITT31 - Database Management Systems
-- ============================================================================

-- Drop existing tables in reverse dependency order
DROP TABLE IF EXISTS Evaluation;
DROP TABLE IF EXISTS Milestone;
DROP TABLE IF EXISTS ProjectTechnology;
DROP TABLE IF EXISTS Project;
DROP TABLE IF EXISTS StudentSkill;
DROP TABLE IF EXISTS Skill;
DROP TABLE IF EXISTS Faculty;
DROP TABLE IF EXISTS Student;

-- 1. Student Table
CREATE TABLE Student (
    student_id INTEGER PRIMARY KEY AUTOINCREMENT,
    register_number VARCHAR(20) NOT NULL UNIQUE,
    student_name VARCHAR(100) NOT NULL,
    department VARCHAR(50) NOT NULL DEFAULT 'Information Technology',
    year_of_study INTEGER NOT NULL CHECK (year_of_study BETWEEN 1 AND 4),
    email VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(15)
);

-- 2. Faculty Table
CREATE TABLE Faculty (
    faculty_id INTEGER PRIMARY KEY AUTOINCREMENT,
    faculty_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    department VARCHAR(50) NOT NULL DEFAULT 'Information Technology'
);

-- 3. Skill Table
CREATE TABLE Skill (
    skill_id INTEGER PRIMARY KEY AUTOINCREMENT,
    skill_name VARCHAR(50) NOT NULL UNIQUE,
    skill_category VARCHAR(50) NOT NULL CHECK (skill_category IN (
        'Programming', 'Web Development', 'Database', 'AI/ML', 'Cloud/DevOps', 'Mobile App', 'Cybersecurity'
    ))
);

-- 4. StudentSkill Mapping Table (Many-to-Many between Student and Skill)
CREATE TABLE StudentSkill (
    student_skill_id INTEGER PRIMARY KEY AUTOINCREMENT,
    student_id INTEGER NOT NULL,
    skill_id INTEGER NOT NULL,
    proficiency_level VARCHAR(20) NOT NULL CHECK (proficiency_level IN ('Beginner', 'Intermediate', 'Advanced')),
    acquired_date DATE NOT NULL,
    FOREIGN KEY (student_id) REFERENCES Student(student_id) ON DELETE CASCADE,
    FOREIGN KEY (skill_id) REFERENCES Skill(skill_id) ON DELETE RESTRICT,
    UNIQUE (student_id, skill_id)
);

-- 5. Project Table
CREATE TABLE Project (
    project_id INTEGER PRIMARY KEY AUTOINCREMENT,
    student_id INTEGER NOT NULL,
    faculty_id INTEGER,
    project_title VARCHAR(150) NOT NULL,
    domain VARCHAR(50) NOT NULL,
    description TEXT,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'In-Progress' CHECK (status IN (
        'Proposed', 'In-Progress', 'Completed', 'Under-Review'
    )),
    FOREIGN KEY (student_id) REFERENCES Student(student_id) ON DELETE CASCADE,
    FOREIGN KEY (faculty_id) REFERENCES Faculty(faculty_id) ON DELETE SET NULL,
    CHECK (end_date >= start_date)
);

-- 6. ProjectTechnology Table (Technologies mapped to each Project)
CREATE TABLE ProjectTechnology (
    technology_id INTEGER PRIMARY KEY AUTOINCREMENT,
    project_id INTEGER NOT NULL,
    technology_name VARCHAR(50) NOT NULL,
    FOREIGN KEY (project_id) REFERENCES Project(project_id) ON DELETE CASCADE,
    UNIQUE (project_id, technology_name)
);

-- 7. Milestone Table (Project Milestones and Progress)
CREATE TABLE Milestone (
    milestone_id INTEGER PRIMARY KEY AUTOINCREMENT,
    project_id INTEGER NOT NULL,
    milestone_name VARCHAR(100) NOT NULL,
    submission_date DATE NOT NULL,
    completion_status VARCHAR(20) NOT NULL DEFAULT 'Pending' CHECK (completion_status IN (
        'Pending', 'In-Progress', 'Completed', 'Delayed'
    )),
    remarks TEXT,
    FOREIGN KEY (project_id) REFERENCES Project(project_id) ON DELETE CASCADE
);

-- 8. Evaluation Table (Component-wise Rubric Marks)
CREATE TABLE Evaluation (
    evaluation_id INTEGER PRIMARY KEY AUTOINCREMENT,
    project_id INTEGER NOT NULL UNIQUE,
    faculty_id INTEGER NOT NULL,
    requirement_analysis_marks REAL NOT NULL CHECK (requirement_analysis_marks BETWEEN 0 AND 20),
    design_marks REAL NOT NULL CHECK (design_marks BETWEEN 0 AND 20),
    implementation_marks REAL NOT NULL CHECK (implementation_marks BETWEEN 0 AND 30),
    documentation_marks REAL NOT NULL CHECK (documentation_marks BETWEEN 0 AND 15),
    presentation_marks REAL NOT NULL CHECK (presentation_marks BETWEEN 0 AND 15),
    total_marks REAL NOT NULL CHECK (total_marks BETWEEN 0 AND 100),
    average_marks REAL NOT NULL CHECK (average_marks BETWEEN 0 AND 100),
    evaluation_date DATE NOT NULL,
    FOREIGN KEY (project_id) REFERENCES Project(project_id) ON DELETE CASCADE,
    FOREIGN KEY (faculty_id) REFERENCES Faculty(faculty_id) ON DELETE RESTRICT
);

-- Create Helpful Indexes for Query Optimization
CREATE INDEX idx_student_reg ON Student(register_number);
CREATE INDEX idx_project_student ON Project(student_id);
CREATE INDEX idx_project_status ON Project(status);
CREATE INDEX idx_milestone_project ON Milestone(project_id);
CREATE INDEX idx_eval_project ON Evaluation(project_id);
CREATE INDEX idx_studentskill_student ON StudentSkill(student_id);
