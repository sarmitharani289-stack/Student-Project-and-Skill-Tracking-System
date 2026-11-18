# Student Project and Skill Tracking System (SPSTS)

**Course Specialization:** B.Tech. Information Technology | **Regulation:** R2025  
**Institution:** Velalar College of Engineering and Technology (Autonomous), Erode  
**Academic Year:** 2025–2026  
**Assigned Scenario:** Scenario 2 – Student Project and Skill Tracking System  
**Target Register Numbers:** 2, 32, 62, 92, L2  

---

## 📌 1. Project Overview & Problem Statement

### Problem Statement
In undergraduate academic environments, student mini-project assignments, technical skill proficiencies, programming language proficiencies, project milestone schedules, and faculty assessments are traditionally recorded across disparate spreadsheets and paper rubrics. This fragmented approach creates severe administrative bottlenecks:
1. **Lack of Centralized Visibility:** Mentors cannot readily track milestone completion rates or identify at-risk delayed projects in real time.
2. **Skill Disconnect:** Inability to cross-reference student technical skills against project domain requirements, leading to undetected skill gaps.
3. **Inefficient Reporting:** Absence of automated rubric aggregation, grading, and visual statistical analytics.

### Proposed Solution
The **Student Project and Skill Tracking System (SPSTS)** is a multi-tier engineering solution that:
- Centralizes student profiles and tracks multi-domain technical skills (`Beginner`, `Intermediate`, `Advanced`).
- Registers student mini-projects, linking domains, technology stacks, and assigned faculty guides.
- Tracks project deliverables via time-bound milestones and automated completion percentage tracking.
- Implements academic evaluation rubrics across 5 key components (Requirement Analysis, Design, Implementation, Documentation, Presentation).
- Provides Python-based data retrieval, preprocessing, and visual analytics for actionable insights into project completion rates, popular technologies, and skill distributions.

---

## 🏗️ 2. System Architecture & Tech Stack

```
+-----------------------------------------------------------------------------------+
|                            USER / ACTOR LAYER                                     |
|               Student (Track/Submit)  |  Faculty Guide (Assess/Review)             |
+-----------------------------------------------------------------------------------+
                                          |
                                          v
+-----------------------------------------------------------------------------------+
|                PRESENTATION LAYER (INTERACTIVE CONSOLE & RUNNERS)                 |
|         run_java.bat          |       run_dbms.bat        |  run_data_science.bat  |
|   (Interactive Manual Input)  | (Manual SQL & Record Ops) | (Manual Filter & Calc) |
+-----------------------------------------------------------------------------------+
                                          |
                                          v
+-----------------------------------------------------------------------------------+
|                    APPLICATION & BUSINESS LOGIC LAYER (JAVA 21)                   |
|   * Object-Oriented Domain Entities (Student, Skill, Project, Milestone, Eval)    |
|   * Multithreaded Milestone Monitoring Daemon (MilestoneMonitorThread)             |
|   * Collections, Lambdas & Stream API Analytics Engine                            |
+-----------------------------------------------------------------------------------+
                                          |
                                          v
+-----------------------------------------------------------------------------------+
|                        DATA PERSISTENCE & STORAGE LAYER (SQL)                     |
|   * Normalized Relational Schema (3NF / BCNF Compliant)                            |
|   * Relational Tables: Student, Faculty, Skill, StudentSkill, Project, Milestone  |
|   * ANSI SQL Queries, Constraints, Views, and Optimization Indexes                |
+-----------------------------------------------------------------------------------+
                                          |
                                          v
+-----------------------------------------------------------------------------------+
|                      DATA SCIENCE & ANALYTICS LAYER (PYTHON 3)                    |
|   * Python DB-API (sqlite3) Data Retrieval & Quality Auditing                     |
|   * Data Wrangling, Descriptive Statistics (Mean, Median, Quartiles, StdDev)       |
|   * 5 Publication-Quality Visualizations (PNG / SVG)                              |
+-----------------------------------------------------------------------------------+
```

---

## 📂 3. Repository Directory Structure

```
c:\Student_Project_and_Skill_Tracking_System\
│
├── README.md                                          <-- Master Documentation & Setup Guide
│
├── software_engineering\                              <-- Software Engineering Deliverables
│   ├── SRS_Document.md                                <-- Formal IEEE 830 Software Requirements Specification
│   ├── Testing_Techniques_and_Test_Cases.md           <-- Test Plan, ECP, BVA & 20 Test Cases (100% Pass)
│   ├── System_Architecture_Diagram.jpg                <-- 5-Tier System Architecture Diagram
│   ├── Use_Case_Diagram.jpg                           <-- UML Use Case Diagram with <<include>> & <<extend>>
│   ├── Class_Diagram.jpg                              <-- UML Class Diagram with attributes, methods & visibility
│   ├── DFD_Level_0_Diagram.jpg                        <-- Context-Level Data Flow Diagram (DFD Level 0)
│   ├── DFD_Level_1_Diagram.jpg                        <-- Detailed Data Flow Diagram (DFD Level 1)
│   ├── Activity_Diagram.jpg                           <-- Swimlane Activity Diagram (Student, Faculty, Admin)
│   └── Sequence_Diagram.jpg                           <-- Temporal Message Passing Sequence Diagram
│
├── database_management\                               <-- Database Management Systems (DBMS)
│   ├── ER_Diagram.jpg                                 <-- Conceptual Entity-Relationship Diagram
│   ├── Relational_Model_and_Normalization.md          <-- Relational Schemas, FDs, 1NF->2NF->3NF->BCNF Proofs
│   ├── schema.sql                                     <-- DDL Script: Table definitions, constraints, indexes
│   ├── insert_data.sql                                <-- DML Script: Population dataset (Reg: 2, 32, 62, 92, L2)
│   ├── queries.sql                                    <-- 10 Advanced SQL Queries, Joins, Aggregations, Views
│   ├── dbms_executor.py                               <-- Interactive & Automated SQL Execution Engine
│   ├── student_project_tracking.db                    <-- SQLite Relational Database instance
│   ├── dbms_output.txt                                <-- Captured text execution output of all queries
│   ├── dbms_execution_screenshot.png                  <-- High-resolution terminal execution screenshot
│   └── run_dbms.bat                                   <-- Double-clickable batch file to run DBMS
│
├── java_programming\                                  <-- Object-Oriented Java Application
│   ├── src\com\tracking\
│   │   ├── Main.java                                  <-- Interactive Menu CLI & Automated Showcase Runner
│   │   ├── model\                                     <-- Domain classes (Student, Project, Skill, etc.)
│   │   ├── service\                                   <-- Business logic, Stream API & Thread daemon
│   │   ├── exception\                                 <-- Custom Exceptions (InvalidMarkException, etc.)
│   │   └── util\                                      <-- Terminal screenshot & chart generation utilities
│   ├── bin\                                           <-- Compiled bytecode (.class files)
│   ├── java_output.txt                                <-- Captured program output transcript
│   ├── java_execution_screenshot.png                  <-- High-resolution terminal execution screenshot
│   └── run_java.bat                                   <-- Double-clickable batch file to compile & run Java
│
└── foundations_of_data_science\                       <-- Python Analytics & Visualizations
    ├── data_analysis.py                               <-- Interactive & Automated Analytics Pipeline
    ├── Student_Project_Tracking_Analytics.ipynb       <-- Interactive Jupyter Notebook
    ├── data_science_output.txt                        <-- Captured statistical analysis transcript
    ├── data_science_execution_screenshot.png          <-- High-resolution terminal execution screenshot
    ├── run_data_science.bat                           <-- Double-clickable batch file to run Analytics
    └── visualizations\                                <-- 5 High-Resolution Output Visualization Charts
        ├── viz1_project_status_completion_rate.png    <-- Donut Chart: Project Completion Breakdown
        ├── viz2_popular_technology_stacks.png         <-- Horizontal Bar Chart: Tech Stack Adoption
        ├── viz3_skill_proficiency_distribution.png    <-- Grouped Bar Chart: Skills by Proficiency Level
        ├── viz4_milestone_progression_status.png      <-- Clustered Bar: Milestone Status per Project
        └── viz5_evaluation_marks_breakdown.png        <-- Academic Rubric Marks & Total Scores
```

---

## ⚡ 4. How to Run & Manually Test the System (Interactive `.bat` Files)

Each folder contains a double-clickable Windows `.bat` file equipped with **Two Modes of Execution**:
- **Mode [1] (Interactive Mode):** Allows you to **manually type inputs**, add new students, register projects, input rubric marks, and execute ad-hoc SQL queries.
- **Mode [2] (Automated Demo Mode):** Runs the complete end-to-end benchmark showcase and refreshes the output text file and screenshot.

---

### 1️⃣ Database Management System (DBMS)
- **Batch File Location:** [`database_management/run_dbms.bat`](file:///C:/Student_Project_and_Skill_Tracking_System/database_management/run_dbms.bat)
- **Double-click:** `run_dbms.bat`
- **Interactive Menu Options for Manual Input:**
  - `[1]` View Table Records (Student, Project, Skill, StudentSkill, Milestone, Evaluation)
  - `[2]` **Manually Insert New Student** (prompts for Register Number, Full Name, Year, Email, Phone)
  - `[3]` **Manually Insert New Project** (prompts for Title, Domain, Description, Guide, Tech Stack)
  - `[4]` **Manually Add / Update Project Milestone** (prompts for Milestone Name, Due Date, Status)
  - `[5]` **Manually Record Project Evaluation Marks** (prompts for Rubric Marks with bounds checking)
  - `[6]` Execute Predefined Analytical Query (Queries 1 to 9)
  - `[7]` **Execute Custom SQL Query** (Type any custom `SELECT`, `INSERT`, `UPDATE` statement!)
  - `[8]` Run Complete Benchmark & Regenerate [`dbms_output.txt`](file:///C:/Student_Project_and_Skill_Tracking_System/database_management/dbms_output.txt)
  - `[0]` Exit

---

### 2️⃣ Java Programming Application
- **Batch File Location:** [`java_programming/run_java.bat`](file:///C:/Student_Project_and_Skill_Tracking_System/java_programming/run_java.bat)
- **Double-click:** `run_java.bat`
- **Interactive Menu Options for Manual Input:**
  - `[1]` **Register a New Student** (Manual entry of Reg No, Name, Dept, Year, Email, Phone)
  - `[2]` **Map Technical Skill to Student** (Manual entry of Skill Name, Category, Proficiency)
  - `[3]` **Register a New Project** (Manual entry of Title, Domain, Description, Tech Stack)
  - `[4]` **Add / Update Project Milestone** (Manual entry of Milestone Name, Deadline, Status)
  - `[5]` **Record Project Evaluation Marks** (Manual entry of 5 rubric scores with validation)
  - `[6]` View Student Directory & Acquired Skills
  - `[7]` View Projects & Milestone Progress
  - `[8]` View Evaluation Marks & Academic Grades
  - `[9]` Departmental Skill Gap Analysis Report
  - `[10]` Run Full Automated Demonstration & Update [`java_output.txt`](file:///C:/Student_Project_and_Skill_Tracking_System/java_programming/java_output.txt)
  - `[0]` Exit

---

### 3️⃣ Foundations of Data Science (Python)
- **Batch File Location:** [`foundations_of_data_science/run_data_science.bat`](file:///C:/Student_Project_and_Skill_Tracking_System/foundations_of_data_science/run_data_science.bat)
- **Double-click:** `run_data_science.bat`
- **Interactive Menu Options for Manual Input:**
  - `[1]` View Dataset Summary & Record Counts
  - `[2]` **Search & Filter Projects** (Manually enter domain keyword e.g. `AI`, `Web`, `Cloud`)
  - `[3]` **Inspect Student Skills & Detect Skill Gaps** (Manually enter Student Reg No e.g. `25IT002`)
  - `[4]` **Custom Rubric Score Calculator** (Manually enter rubric marks to compute grade & statistics)
  - `[5]` Descriptive Statistics (Mean, Median, Standard Deviation, Quartiles)
  - `[6]` Audit & Verify Generated Visualizations (5 PNG Charts in `visualizations/`)
  - `[7]` Run Full Automated EDA Pipeline & Update [`data_science_output.txt`](file:///C:/Student_Project_and_Skill_Tracking_System/foundations_of_data_science/data_science_output.txt)
  - `[0]` Exit

---

## 📊 5. Generated Visualizations Summary

All 5 publication-quality visualization charts are saved in [`foundations_of_data_science/visualizations/`](file:///C:/Student_Project_and_Skill_Tracking_System/foundations_of_data_science/visualizations/):
1. **`viz1_project_status_completion_rate.png`:** Donut Chart displaying Completed (37.5%), In-Progress (37.5%), and Proposed (25.0%) projects.
2. **`viz2_popular_technology_stacks.png`:** Horizontal Bar Chart depicting Python (62.5%), MySQL (50.0%), Java (25.0%), Docker (25.0%), and React (25.0%).
3. **`viz3_skill_proficiency_distribution.png`:** Grouped Bar Chart categorizing skills into Advanced, Intermediate, and Beginner across 6 domains.
4. **`viz4_milestone_progression_status.png`:** Clustered Bar Chart tracking completed, pending, and delayed milestones per project.
5. **`viz5_evaluation_marks_breakdown.png`:** Evaluation Score Chart showing individual student rubric totals and academic grades (Grade O: Sarmida S 96.5%, Dinesh Kumar K 95.0%, Pradeep M 93.5%).

---

## 👥 6. Target Student Register Numbers

| Register Number | Student Name | Role / Domain Contribution |
| :--- | :--- | :--- |
| **25IT002 (Reg: 2)** | Sarmida S | Project Lead, Full-Stack Architecture & Java Business Services |
| **25IT032 (Reg: 32)** | Kavin R | AI/ML Pipeline, Data Preprocessing & Python Analytics |
| **25IT062 (Reg: 62)** | Pradeep M | Web Microservices, Data Modeling & Normalization |
| **25IT092 (Reg: 92)** | Shreya V | Cloud Infrastructure, Security Constraints & Testing Documentation |
| **25ITL02 (Reg: L2)** | Dinesh Kumar K | Mobile Application Interface, DFD/UML Diagrams & Quality Assurance |

---
*Developed for Academic Evaluation under Regulation R2025, Department of Information Technology, Velalar College of Engineering and Technology, Erode.*
