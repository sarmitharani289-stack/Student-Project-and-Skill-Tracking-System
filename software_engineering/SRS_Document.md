# SOFTWARE REQUIREMENTS SPECIFICATION (SRS)
## Student Project and Skill Tracking System
**Scenario 2 | Academic Year: 2025–2026**  
**Register Numbers:** 2, 32, 62, 92, L2  
**Department:** Information Technology  
**Affiliated Institution:** Velalar College of Engineering and Technology (Autonomous), Erode  
**Regulation:** R2025 | Course: 25ITT33 – Software Engineering  

---

## 1. INTRODUCTION

### 1.1 Purpose
The purpose of this Software Requirements Specification (SRS) document is to provide a detailed, formal description of the **Student Project and Skill Tracking System (SPSTS)**. This document specifies the functional and non-functional requirements, architectural characteristics, interface specifications, and constraints governing the development of the system. It serves as a contract between stakeholders (department administrators, faculty guides, students) and the software development team.

### 1.2 Scope of the System
Currently, student project records, programming language competencies, milestone schedules, and faculty assessments are scattered across decentralized spreadsheets and physical rubrics. This leads to:
- Lack of centralized visibility into project progression.
- Difficulty in monitoring deadline compliance across project phases.
- Absence of real-time analytics to identify departmental technical skill gaps.
- Inefficient manual mark consolidation and report generation.

The **Student Project and Skill Tracking System** is a software solution designed to:
1. Centralize student profiles, departmental affiliations, and technical skill proficiencies.
2. Facilitate online project registrations with designated project titles, domains, descriptions, and technology stacks.
3. Manage time-bound project milestones, track submission dates, and provide real-time completion status indicators.
4. Record multi-component project evaluations (Requirement Analysis, Design, Implementation, Documentation, and Presentation) based on academic rubrics.
5. Provide actionable insights via Python-based analytics and data visualizations to assist faculty in identifying skill gaps and project completion trends.

### 1.3 Definitions, Acronyms, and Abbreviations
- **SRS:** Software Requirements Specification
- **SPSTS:** Student Project and Skill Tracking System
- **DFD:** Data Flow Diagram
- **ERD:** Entity-Relationship Diagram
- **UML:** Unified Modeling Language
- **DAO:** Data Access Object
- **JDBC:** Java Database Connectivity
- **PK / FK:** Primary Key / Foreign Key
- **EDA:** Exploratory Data Analysis
- **1NF / 2NF / 3NF / BCNF:** First, Second, Third, and Boyce-Codd Normal Forms

### 1.4 References
1. IEEE Std 830-1998: *IEEE Recommended Practice for Software Requirements Specifications*.
2. Roger S. Pressman & Bruce R. Maxim, *Software Engineering: A Practitioner’s Approach*, 9th Edition, McGraw-Hill, 2020.
3. Ian Sommerville, *Software Engineering*, 10th Edition, Pearson Education, 2018.
4. Abraham Silberschatz, Henry F. Korth, S. Sudarshan, *Database System Concepts*, 7th Edition, McGraw-Hill, 2020.
5. VCET B.Tech IT Curriculum & Syllabus R2025: Courses 25ITT32 (Java Programming), 25ITT31 (DBMS), 25ITC31 (Foundations of Data Science), and 25ITT33 (Software Engineering).

---

## 2. OVERALL DESCRIPTION

### 2.1 Product Perspective
The Student Project and Skill Tracking System operates as a modular, three-tier enterprise academic system:
- **Presentation Layer:** Console and GUI interfaces for students, faculty evaluators, and system administrators.
- **Application / Business Logic Layer (Java):** Core object-oriented services enforcing business rules, milestone validations, weighted mark calculations, and progress tracking algorithms.
- **Data Access & Storage Layer (Relational DBMS):** Normalized SQL database storing entities including students, skills, projects, milestones, evaluations, and faculty advisors.
- **Analytics & Intelligence Layer (Python):** Data extraction, preprocessing, statistical analysis, and exploratory data visualizations depicting completion rates, skill frequencies, and performance distributions.

### 2.2 Product Functions (High-Level)
1. **Student & Skill Management:** Register and maintain student demographics, catalog skills into technical domains, and map students to proficiency tiers (Beginner, Intermediate, Advanced).
2. **Project Lifecycle Management:** Create, configure, update, and monitor student project records mapped to domains, technology stacks, and faculty advisors.
3. **Milestone Tracking:** Break projects into deliverable milestones (SRS, Design, Implementation, Testing, Final Viva), enforcing deadline checks and status tracking (Pending, In-Progress, Completed, Delayed).
4. **Multi-Component Evaluation:** Record assessment marks across 5 standardized academic components with automatic aggregation of total and percentage marks.
5. **Reporting & Business Intelligence:** Generate summary reports and project progress audits.
6. **Data Science & Visualization:** Extract relational records for Python-based visual exploratory data analysis (EDA).

### 2.3 User Classes and Characteristics
| User Class | Description | Rights & Permissions |
| :--- | :--- | :--- |
| **Student** | Enrolled undergraduate IT students executing academic projects. | View/edit personal profile, register projects, record and update acquired skills, submit milestone deliverables, view progress and evaluation scores. |
| **Faculty / Guide** | Academic advisors assigned to supervise and assess projects. | View assigned student projects, review milestone deliverables, score project evaluation rubrics, provide feedback remarks, generate student performance reports. |
| **Department Admin** | Head of Department, Project Coordinators, and Academic Administrators. | Full administrative control: manage student/faculty master data, manage skill categories, monitor departmental progress dashboards, trigger data science analytics. |

### 2.4 Operating Environment
- **Operating System:** Cross-platform (Windows 10/11, Linux Ubuntu 22.04 LTS, macOS Sonoma).
- **Runtime Environment:** Java Runtime Environment (JRE/JDK 17 or 21 LTS).
- **Database Engine:** MySQL 8.0+ / SQLite 3 relational database.
- **Python Analytics Environment:** Python 3.10+ with standard scientific libraries (`pandas`, `numpy`, `matplotlib`, `seaborn`, `sqlite3`).

### 2.5 Design and Implementation Constraints
1. **Strict Normalization:** Relational schema must adhere to at least Third Normal Form (3NF) and Boyce-Codd Normal Form (BCNF) to eliminate update, insertion, and deletion anomalies.
2. **Object-Oriented Design:** Java implementation must reflect the UML Class Diagram, utilizing encapsulation, inheritance, interfaces, custom exceptions, and the Java Collections/Stream API.
3. **Platform Independence:** Database schema and application code must run across local development and classroom environments via standalone batch scripts (`.bat`).
4. **Data Integrity:** Primary Key, Foreign Key, Unique, and Check constraints must be strictly enforced at the database level.

---

## 3. SPECIFIC REQUIREMENTS

### 3.1 External Interface Requirements
#### 3.1.1 User Interfaces
- Menu-driven console CLI and automated demo runner providing clear numbered actions:
  - `[1]` Student Profile Management
  - `[2]` Skill Catalog & Proficiency Mapping
  - `[3]` Project Registration & Tech Stack Association
  - `[4]` Milestone Status Tracking
  - `[5]` Project Component Evaluation & Scoring
  - `[6]` Summary Progress & Skill Gap Analytics Report
- Formatted tabular output for command execution and automated audit reporting.

#### 3.1.2 Software Interfaces
- **Java Persistence / JDBC:** Bridge connecting Java domain services with the relational database.
- **Python Database Connector:** Python DB-API (sqlite3 / mysql.connector) extracting tabular datasets into Pandas DataFrames.
- **Matplotlib / Seaborn Engine:** Data visualization rendering pipeline generating standalone chart artifacts.

---

### 3.2 Functional Requirements

#### FR1: Student Details & Technical Skill Management
- **FR1.1:** The system shall allow registering students with unique Register Number, Full Name, Department, Year of Study, Email, and Phone Number.
- **FR1.2:** The system shall maintain a master catalog of technical skills categorized into domains (Programming, Web Development, Database, AI/ML, Cloud/DevOps).
- **FR1.3:** The system shall enable associating multiple skills with a student along with a designated proficiency level (`Beginner`, `Intermediate`, `Advanced`) and acquisition date.

#### FR2: Project Registration & Technology Association
- **FR2.1:** The system shall permit students to register project proposals specifying Project Title, Domain (e.g., Web, AI, IoT, Cloud), Abstract/Description, Start Date, Expected End Date, and Assigned Faculty Guide.
- **FR2.2:** The system shall allow mapping multiple technologies (languages, frameworks, databases) to each registered project via the `ProjectTechnology` entity.
- **FR2.3:** The initial status of a project shall default to `'In-Progress'`.

#### FR3: Milestone Tracking & Monitoring
- **FR3.1:** The system shall record defined project milestones (e.g., Requirement Analysis, System Design, Prototype Coding, Testing & Deployment).
- **FR3.2:** Each milestone shall have a planned submission date, actual completion date, status (`Pending`, `In-Progress`, `Completed`, `Delayed`), and mentor remarks.
- **FR3.3:** The system shall automatically compute project completion percentage based on the ratio of completed milestones to total assigned milestones.

#### FR4: Project Component Evaluation
- **FR4.1:** The system shall enable faculty evaluators to score student projects across five standardized rubrics:
  1. Requirement Analysis (Max: 20 marks)
  2. System Design (Max: 20 marks)
  3. Implementation & Code Quality (Max: 30 marks)
  4. Documentation & Viva (Max: 15 marks)
  5. Presentation & Demonstration (Max: 15 marks)
- **FR4.2:** The system shall automatically compute Total Marks (out of 100) and Average/Grade.
- **FR4.3:** The system shall restrict evaluation scores from exceeding predefined rubric maximums.

#### FR5: Reporting & Progress Analysis
- **FR5.1:** The system shall generate comprehensive project progress reports including student info, guide info, milestone completion, and evaluation scores.
- **FR5.2:** The system shall identify students falling behind scheduled milestone deadlines.
- **FR5.3:** The system shall detect students lacking foundational skills required by their registered project domain.

#### FR6: Database Extraction via Python
- **FR6.1:** The system shall provide Python scripts to connect to the relational database and execute analytical queries.
- **FR6.2:** The Python module shall preprocess retrieved records: handle missing values, format dates, and engineer aggregate metrics.

#### FR7: Data Visualization
- **FR7.1:** The Python subsystem shall generate five distinct visual charts:
  1. Overall Project Completion Status (Pie / Donut Chart).
  2. Most Popular Technology Choices (Horizontal Bar Chart).
  3. Skill Distribution by Domain and Proficiency Level (Grouped Bar Chart).
  4. Milestone Timeline & Completion Tracking (Bar / Progress Plot).
  5. Component-wise Evaluation Marks Distribution (Box Plot / Correlation Heatmap).
- **FR7.2:** All charts shall be saved as high-resolution image files (`.png`) for academic audit.

---

### 3.3 Non-Functional Requirements

| Requirement Category | Metric / Specification |
| :--- | :--- |
| **Performance** | Database query response time under 100 ms for datasets up to 100,000 records. Application startup and report generation under 2 seconds. |
| **Reliability** | 99.9% uptime. ACID compliance across all database transactions; no partial milestone updates or orphan records. |
| **Security** | Role-based data access. Students cannot modify evaluation marks. Integrity constraints prevent invalid marks (>100 or <0). |
| **Maintainability** | Clear separation of concerns following MVC/DAO architectural patterns. Well-commented, modular Java and Python codebases. |
| **Portability** | Standalone execution on Windows via `.bat` files with zero external server dependencies using ANSI SQL / SQLite / Java SE. |

---

## 4. SYSTEM MODELS AND ARCHITECTURAL DESIGN

The complete system architecture and behavioral models have been formally developed and are stored in their respective project directories:

1. **System Architecture Diagram:** Documents the 5-tier architecture spanning Users $\rightarrow$ Presentation $\rightarrow$ Business Logic $\rightarrow$ Data Access $\rightarrow$ Relational Database $\rightarrow$ Python Analytics. Located at: [`software_engineering/System_Architecture_Diagram.jpg`](file:///C:/Student_Project_and_Skill_Tracking_System/software_engineering/System_Architecture_Diagram.jpg)
2. **Use Case Diagram:** Defines primary actors (`Student`, `Faculty`, `Admin`) and their interactions, including `<<include>>` and `<<extend>>` relationships. Located at: [`software_engineering/Use_Case_Diagram.jpg`](file:///C:/Student_Project_and_Skill_Tracking_System/software_engineering/Use_Case_Diagram.jpg)
3. **UML Class Diagram:** Specifies domain classes (`Student`, `Faculty`, `Skill`, `StudentSkill`, `Project`, `ProjectTechnology`, `Milestone`, `Evaluation`) with attributes, methods, visibility, and cardinalities. Located at: [`software_engineering/Class_Diagram.jpg`](file:///C:/Student_Project_and_Skill_Tracking_System/software_engineering/Class_Diagram.jpg)
4. **Data Flow Diagrams (Level 0 & Level 1):** Illustrate data transformations between external entities, business processes (1.0 to 4.0), data stores (D1 to D5), and Python analytics. Located at: [`software_engineering/DFD_Level_0_Diagram.jpg`](file:///C:/Student_Project_and_Skill_Tracking_System/software_engineering/DFD_Level_0_Diagram.jpg) and [`software_engineering/DFD_Level_1_Diagram.jpg`](file:///C:/Student_Project_and_Skill_Tracking_System/software_engineering/DFD_Level_1_Diagram.jpg)
5. **Activity Diagram:** Outlines concurrent swimlanes for Student, Faculty, and Admin workflows. Located at: [`software_engineering/Activity_Diagram.jpg`](file:///C:/Student_Project_and_Skill_Tracking_System/software_engineering/Activity_Diagram.jpg)
6. **Sequence Diagram:** Captures temporal message passing for project registration, milestone tracking, evaluation scoring, and progress reporting. Located at: [`software_engineering/Sequence_Diagram.jpg`](file:///C:/Student_Project_and_Skill_Tracking_System/software_engineering/Sequence_Diagram.jpg)
7. **Entity-Relationship (ER) Diagram:** Formulates the normalized conceptual database schema with primary keys, foreign keys, and cardinalities. Located at: [`database_management/ER_Diagram.jpg`](file:///C:/Student_Project_and_Skill_Tracking_System/database_management/ER_Diagram.jpg)

---
*End of Software Requirements Specification*
