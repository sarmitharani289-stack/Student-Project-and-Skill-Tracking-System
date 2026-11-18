# RELATIONAL MODEL AND DATABASE NORMALIZATION
## Student Project and Skill Tracking System
**Scenario 2 | Academic Year: 2025–2026**  
**Register Numbers:** 2, 32, 62, 92, L2  
**Department:** Information Technology  
**Affiliated Institution:** Velalar College of Engineering and Technology, Erode  
**Regulation:** R2025 | Course: 25ITT31 – Database Management Systems  

---

## 1. RELATIONAL DATA MODEL

The relational data model for the **Student Project and Skill Tracking System** maps the conceptual entities, attributes, and relationships depicted in the [ER Diagram](file:///C:/Student_Project_and_Skill_Tracking_System/database_management/ER_Diagram.jpg) into formal relational schemas.

### 1.1 Relational Schemas and Integrity Constraints

#### 1. `Student`
- **Schema:** `Student(student_id, register_number, student_name, department, year_of_study, email, phone)`
- **Primary Key (PK):** `student_id`
- **Candidate Keys / Unique Constraints (UQ):** `register_number`, `email`
- **Domain Constraints:** `year_of_study IN (1, 2, 3, 4)`

#### 2. `Faculty`
- **Schema:** `Faculty(faculty_id, faculty_name, email, department)`
- **Primary Key (PK):** `faculty_id`
- **Candidate Keys (UQ):** `email`

#### 3. `Skill`
- **Schema:** `Skill(skill_id, skill_name, skill_category)`
- **Primary Key (PK):** `skill_id`
- **Candidate Keys (UQ):** `skill_name`
- **Domain Constraints:** `skill_category IN ('Programming', 'Web Development', 'Database', 'AI/ML', 'Cloud/DevOps', 'Mobile App', 'Cybersecurity')`

#### 4. `StudentSkill`
- **Schema:** `StudentSkill(student_skill_id, student_id, skill_id, proficiency_level, acquired_date)`
- **Primary Key (PK):** `student_skill_id`
- **Foreign Keys (FK):**
  - `student_id REFERENCES Student(student_id) ON DELETE CASCADE`
  - `skill_id REFERENCES Skill(skill_id) ON DELETE RESTRICT`
- **Unique Constraint (UQ):** `(student_id, skill_id)` *(A student cannot have duplicate records for the same skill)*
- **Domain Constraints:** `proficiency_level IN ('Beginner', 'Intermediate', 'Advanced')`

#### 5. `Project`
- **Schema:** `Project(project_id, student_id, faculty_id, project_title, domain, description, start_date, end_date, status)`
- **Primary Key (PK):** `project_id`
- **Foreign Keys (FK):**
  - `student_id REFERENCES Student(student_id) ON DELETE CASCADE`
  - `faculty_id REFERENCES Faculty(faculty_id) ON DELETE SET NULL`
- **Domain Constraints:** `status IN ('Proposed', 'In-Progress', 'Completed', 'Under-Review')`, `end_date >= start_date`

#### 6. `ProjectTechnology`
- **Schema:** `ProjectTechnology(technology_id, project_id, technology_name)`
- **Primary Key (PK):** `technology_id`
- **Foreign Keys (FK):**
  - `project_id REFERENCES Project(project_id) ON DELETE CASCADE`
- **Unique Constraint (UQ):** `(project_id, technology_name)`

#### 7. `Milestone`
- **Schema:** `Milestone(milestone_id, project_id, milestone_name, submission_date, completion_status, remarks)`
- **Primary Key (PK):** `milestone_id`
- **Foreign Keys (FK):**
  - `project_id REFERENCES Project(project_id) ON DELETE CASCADE`
- **Domain Constraints:** `completion_status IN ('Pending', 'In-Progress', 'Completed', 'Delayed')`

#### 8. `Evaluation`
- **Schema:** `Evaluation(evaluation_id, project_id, faculty_id, requirement_analysis_marks, design_marks, implementation_marks, documentation_marks, presentation_marks, total_marks, average_marks, evaluation_date)`
- **Primary Key (PK):** `evaluation_id`
- **Foreign Keys (FK):**
  - `project_id REFERENCES Project(project_id) ON DELETE CASCADE`
  - `faculty_id REFERENCES Faculty(faculty_id) ON DELETE RESTRICT`
- **Check Constraints:**
  - `requirement_analysis_marks BETWEEN 0 AND 20`
  - `design_marks BETWEEN 0 AND 20`
  - `implementation_marks BETWEEN 0 AND 30`
  - `documentation_marks BETWEEN 0 AND 15`
  - `presentation_marks BETWEEN 0 AND 15`
  - `total_marks = requirement_analysis_marks + design_marks + implementation_marks + documentation_marks + presentation_marks`
  - `average_marks = total_marks` *(out of 100)*

---

## 2. FUNCTIONAL DEPENDENCIES (FD)

The functional dependencies governing the attributes across the domain entities are as follows:

1. **Student:**
   - $FD_1: \text{student\_id} \rightarrow \{\text{register\_number, student\_name, department, year\_of\_study, email, phone}\}$
   - $FD_2: \text{register\_number} \rightarrow \{\text{student\_id, student\_name, department, year\_of\_study, email, phone}\}$
   - $FD_3: \text{email} \rightarrow \{\text{student\_id, register\_number, student\_name, department, year\_of\_study, phone}\}$

2. **Faculty:**
   - $FD_4: \text{faculty\_id} \rightarrow \{\text{faculty\_name, email, department}\}$
   - $FD_5: \text{email} \rightarrow \{\text{faculty\_id, faculty\_name, department}\}$

3. **Skill:**
   - $FD_6: \text{skill\_id} \rightarrow \{\text{skill\_name, skill\_category}\}$
   - $FD_7: \text{skill\_name} \rightarrow \{\text{skill\_id, skill\_category}\}$

4. **StudentSkill:**
   - $FD_8: \text{student\_skill\_id} \rightarrow \{\text{student\_id, skill\_id, proficiency\_level, acquired\_date}\}$
   - $FD_9: \{\text{student\_id, skill\_id}\} \rightarrow \{\text{student\_skill\_id, proficiency\_level, acquired\_date}\}$

5. **Project:**
   - $FD_{10}: \text{project\_id} \rightarrow \{\text{student\_id, faculty\_id, project\_title, domain, description, start\_date, end\_date, status}\}$

6. **ProjectTechnology:**
   - $FD_{11}: \text{technology\_id} \rightarrow \{\text{project\_id, technology\_name}\}$
   - $FD_{12}: \{\text{project\_id, technology\_name}\} \rightarrow \{\text{technology\_id}\}$

7. **Milestone:**
   - $FD_{13}: \text{milestone\_id} \rightarrow \{\text{project\_id, milestone\_name, submission\_date, completion\_status, remarks}\}$

8. **Evaluation:**
   - $FD_{14}: \text{evaluation\_id} \rightarrow \{\text{project\_id, faculty\_id, requirement\_analysis\_marks, design\_marks, implementation\_marks, documentation\_marks, presentation\_marks, total\_marks, average\_marks, evaluation\_date}\}$

---

## 3. DATABASE NORMALIZATION PROCESS

Normalization systematically decomposes unnormalized data tables into well-structured relations to eliminate **Insertion Anomalies**, **Update Anomalies**, and **Deletion Anomalies**.

### 3.1 Unnormalized Form (UNF)
In a spreadsheet-based legacy system, all information is stored in a single flat table:
$$\text{UNF}(\text{reg\_no, name, dept, skills(multi-valued), project\_title, tech\_used(multi-valued), milestones(multi-valued), marks, faculty})$$

**Anomalies in UNF:**
- **Insertion Anomaly:** A new skill or faculty member cannot be added without first registering a student project.
- **Update Anomaly:** Updating a faculty member's email requires changing thousands of rows across spreadsheets.
- **Deletion Anomaly:** Deleting a student project inadvertently wipes out the records of acquired student skills and faculty advisor details.

---

### 3.2 First Normal Form (1NF)
> **Definition:** A relation is in 1NF if and only if all domain values are atomic (indivisible) and there are no repeating groups or multi-valued attributes.

**Steps to achieve 1NF:**
1. Split multi-valued skills into individual rows.
2. Split multi-valued project technologies into atomic rows.
3. Split repeating milestone columns into atomic rows.
4. Define candidate keys for each tuple.

*Result:* Every attribute contains only single, atomic values. However, relations still suffer from partial dependencies because non-prime attributes depend on parts of composite primary keys.

---

### 3.3 Second Normal Form (2NF)
> **Definition:** A relation is in 2NF if and only if it is in 1NF and every non-prime attribute is **fully functionally dependent** on the primary key (no partial dependencies).

**Identification of Partial Dependencies in Composite Keys:**
If we had kept a combined table:
$$\text{StudentProject}(\underline{\text{student\_id, project\_id}}, \text{student\_name, dept, project\_title, domain})$$
Here:
- $\text{student\_id} \rightarrow \text{student\_name, dept}$ *(Partial dependency on part of the composite key)*
- $\text{project\_id} \rightarrow \text{project\_title, domain}$ *(Partial dependency)*

**Decomposition to achieve 2NF:**
- Decompose into `Student(\underline{student\_id}, \dots)` where all student attributes depend entirely on `student_id`.
- Decompose into `Project(\underline{project\_id}, \dots)` where all project attributes depend entirely on `project_id`.
- Decompose skills into `StudentSkill(\underline{student\_skill\_id}, student\_id, skill\_id, \dots)` and `ProjectTechnology(\underline{technology\_id}, project\_id, \dots)`.

*Result:* No partial dependencies exist. All non-key attributes are fully dependent on the primary keys.

---

### 3.4 Third Normal Form (3NF)
> **Definition:** A relation is in 3NF if and only if it is in 2NF and there is **no transitive dependency** ($X \rightarrow Y$ and $Y \rightarrow Z$, where $Y$ is not a superkey and $Z$ is a non-prime attribute).

**Identification of Transitive Dependencies:**
If `Project` had stored faculty details directly:
$$\text{Project}(\underline{\text{project\_id}}, \text{title}, \text{faculty\_id}, \text{faculty\_name}, \text{faculty\_email}, \text{faculty\_dept})$$
Then:
- $\text{project\_id} \rightarrow \text{faculty\_id}$
- $\text{faculty\_id} \rightarrow \text{faculty\_name, faculty\_email, faculty\_dept}$
This creates a transitive dependency: $\text{project\_id} \rightarrow \text{faculty\_name}$.

**Decomposition to achieve 3NF:**
- Extract faculty information into an independent relation:
  `Faculty(\underline{faculty_id}, faculty_name, email, department)`
- Keep `faculty_id` in `Project` as a Foreign Key referencing `Faculty(faculty_id)`.
- Similarly, separate `Skill(\underline{skill_id}, skill_name, skill_category)` from `StudentSkill`.

*Result:* Every non-prime attribute depends directly on the primary key, the whole primary key, and nothing but the primary key. All relations are strictly in 3NF.

---

### 3.5 Boyce-Codd Normal Form (BCNF)
> **Definition:** A relation is in BCNF if and only if for every functional dependency $X \rightarrow Y$, $X$ is a **superkey**.

**BCNF Verification for SPSTS Schema:**
- In `Student`: Keys are `student_id`, `register_number`, and `email`. All determinants ($FD_1, FD_2, FD_3$) are candidate keys / superkeys. $\implies$ **In BCNF**.
- In `Faculty`: Keys are `faculty_id` and `email`. All determinants ($FD_4, FD_5$) are superkeys. $\implies$ **In BCNF**.
- In `Skill`: Keys are `skill_id` and `skill_name`. Both determinants ($FD_6, FD_7$) are superkeys. $\implies$ **In BCNF**.
- In `StudentSkill`: Key is `student_skill_id` and candidate key is `(student_id, skill_id)`. Both determinants ($FD_8, FD_9$) are superkeys. $\implies$ **In BCNF**.
- In `Project`: Key is `project_id`. The determinant is a superkey. $\implies$ **In BCNF**.
- In `ProjectTechnology`: Key is `technology_id` and candidate key is `(project_id, technology_name)`. Both determinants ($FD_{11}, FD_{12}$) are superkeys. $\implies$ **In BCNF**.
- In `Milestone`: Key is `milestone_id`. Determinant is a superkey. $\implies$ **In BCNF**.
- In `Evaluation`: Key is `evaluation_id`. Determinant is a superkey. $\implies$ **In BCNF**.

**Conclusion:** The decomposed schema is completely **lossless**, preserves all functional dependencies, and satisfies **Boyce-Codd Normal Form (BCNF)**.

---
*End of Relational Model and Database Normalization Document*
