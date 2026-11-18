-- ============================================================================
-- SQL QUERIES & ANALYTICAL SCRIPTS: Student Project and Skill Tracking System
-- Scenario 2 | Course: 25ITT31 - Database Management Systems
-- ============================================================================

-- ----------------------------------------------------------------------------
-- QUERY 1: Basic Selection & Projection with Joins
-- Retrieve full student details, their registered project, and faculty guide
-- ----------------------------------------------------------------------------
SELECT 
    s.register_number,
    s.student_name,
    p.project_title,
    p.domain,
    p.status AS project_status,
    f.faculty_name AS faculty_guide,
    f.email AS faculty_email
FROM Student s
INNER JOIN Project p ON s.student_id = p.student_id
LEFT JOIN Faculty f ON p.faculty_id = f.faculty_id
ORDER BY s.register_number;

-- ----------------------------------------------------------------------------
-- QUERY 2: Multi-Table Join with String Aggregation
-- List each project with all mapped technologies as a combined comma-separated list
-- ----------------------------------------------------------------------------
SELECT 
    p.project_id,
    p.project_title,
    s.student_name,
    GROUP_CONCAT(pt.technology_name, ', ') AS technologies_used
FROM Project p
JOIN Student s ON p.student_id = s.student_id
JOIN ProjectTechnology pt ON p.project_id = pt.project_id
GROUP BY p.project_id, p.project_title, s.student_name
ORDER BY p.project_id;

-- ----------------------------------------------------------------------------
-- QUERY 3: Milestone Progress Calculation (Aggregations, COUNT, CASE WHEN)
-- Compute the percentage of completed milestones for each active project
-- ----------------------------------------------------------------------------
SELECT 
    p.project_id,
    p.project_title,
    s.student_name,
    COUNT(m.milestone_id) AS total_milestones,
    SUM(CASE WHEN m.completion_status = 'Completed' THEN 1 ELSE 0 END) AS completed_milestones,
    SUM(CASE WHEN m.completion_status = 'Delayed' THEN 1 ELSE 0 END) AS delayed_milestones,
    SUM(CASE WHEN m.completion_status = 'Pending' THEN 1 ELSE 0 END) AS pending_milestones,
    ROUND((SUM(CASE WHEN m.completion_status = 'Completed' THEN 1.0 ELSE 0.0 END) / COUNT(m.milestone_id)) * 100.0, 1) AS progress_percentage
FROM Project p
JOIN Student s ON p.student_id = s.student_id
LEFT JOIN Milestone m ON p.project_id = m.project_id
GROUP BY p.project_id, p.project_title, s.student_name
ORDER BY progress_percentage DESC;

-- ----------------------------------------------------------------------------
-- QUERY 4: Technology Popularity Ranking (GROUP BY, ORDER BY, HAVING)
-- Identify the most popular programming languages and frameworks used in mini-projects
-- ----------------------------------------------------------------------------
SELECT 
    pt.technology_name,
    COUNT(pt.project_id) AS usage_count,
    ROUND((COUNT(pt.project_id) * 100.0 / (SELECT COUNT(DISTINCT project_id) FROM Project)), 1) AS adoption_percentage
FROM ProjectTechnology pt
GROUP BY pt.technology_name
ORDER BY usage_count DESC, pt.technology_name ASC;

-- ----------------------------------------------------------------------------
-- QUERY 5: Student Skill Matrix & Proficiency Distribution
-- Analyze the distribution of skills across student proficiency levels
-- ----------------------------------------------------------------------------
SELECT 
    sk.skill_category,
    sk.skill_name,
    SUM(CASE WHEN ss.proficiency_level = 'Advanced' THEN 1 ELSE 0 END) AS advanced_count,
    SUM(CASE WHEN ss.proficiency_level = 'Intermediate' THEN 1 ELSE 0 END) AS intermediate_count,
    SUM(CASE WHEN ss.proficiency_level = 'Beginner' THEN 1 ELSE 0 END) AS beginner_count,
    COUNT(ss.student_id) AS total_students_with_skill
FROM Skill sk
LEFT JOIN StudentSkill ss ON sk.skill_id = ss.skill_id
GROUP BY sk.skill_category, sk.skill_name
ORDER BY sk.skill_category, total_students_with_skill DESC;

-- ----------------------------------------------------------------------------
-- QUERY 6: Evaluation Marks Audit & Academic Grade Classification (CASE WHEN)
-- Detailed breakdown of evaluation rubrics with performance grades
-- ----------------------------------------------------------------------------
SELECT 
    s.register_number,
    s.student_name,
    p.project_title,
    e.requirement_analysis_marks AS req_20,
    e.design_marks AS design_20,
    e.implementation_marks AS impl_30,
    e.documentation_marks AS doc_15,
    e.presentation_marks AS pres_15,
    e.total_marks AS total_100,
    CASE 
        WHEN e.total_marks >= 90 THEN 'O (Outstanding)'
        WHEN e.total_marks >= 80 THEN 'A+ (Excellent)'
        WHEN e.total_marks >= 70 THEN 'A (Very Good)'
        WHEN e.total_marks >= 60 THEN 'B+ (Good)'
        WHEN e.total_marks >= 50 THEN 'B (Average)'
        ELSE 'RA (Re-Appear)'
    END AS academic_grade,
    f.faculty_name AS evaluated_by
FROM Evaluation e
JOIN Project p ON e.project_id = p.project_id
JOIN Student s ON p.student_id = s.student_id
JOIN Faculty f ON e.faculty_id = f.faculty_id
ORDER BY e.total_marks DESC;

-- ----------------------------------------------------------------------------
-- QUERY 7: Skill Gap Analysis (Correlated Subquery / NOT EXISTS)
-- Identify students who registered an AI/ML project but do not possess 'Machine Learning' skill
-- ----------------------------------------------------------------------------
SELECT 
    s.register_number,
    s.student_name,
    p.project_title,
    p.domain
FROM Student s
JOIN Project p ON s.student_id = p.student_id
WHERE p.domain LIKE '%AI%'
  AND NOT EXISTS (
      SELECT 1 
      FROM StudentSkill ss
      JOIN Skill sk ON ss.skill_id = sk.skill_id
      WHERE ss.student_id = s.student_id 
        AND sk.skill_name = 'Machine Learning'
  );

-- ----------------------------------------------------------------------------
-- QUERY 8: Delayed Milestone Audit (Alerting System)
-- Identify milestones marked as Delayed along with planned dates and mentor remarks
-- ----------------------------------------------------------------------------
SELECT 
    s.register_number,
    s.student_name,
    p.project_title,
    m.milestone_name,
    m.submission_date,
    m.completion_status,
    m.remarks
FROM Milestone m
JOIN Project p ON m.project_id = p.project_id
JOIN Student s ON p.student_id = s.student_id
WHERE m.completion_status = 'Delayed'
ORDER BY m.submission_date;

-- ----------------------------------------------------------------------------
-- QUERY 9: Database View Creation
-- Create a comprehensive reporting view summarizing student project performance
-- ----------------------------------------------------------------------------
DROP VIEW IF EXISTS View_StudentProjectSummary;
CREATE VIEW View_StudentProjectSummary AS
SELECT 
    s.student_id,
    s.register_number,
    s.student_name,
    s.department,
    p.project_id,
    p.project_title,
    p.domain,
    p.status AS project_status,
    f.faculty_name AS mentor,
    COALESCE(e.total_marks, 0.0) AS marks_obtained,
    COUNT(DISTINCT m.milestone_id) AS total_milestones,
    SUM(CASE WHEN m.completion_status = 'Completed' THEN 1 ELSE 0 END) AS completed_milestones
FROM Student s
JOIN Project p ON s.student_id = p.student_id
LEFT JOIN Faculty f ON p.faculty_id = f.faculty_id
LEFT JOIN Evaluation e ON p.project_id = e.project_id
LEFT JOIN Milestone m ON p.project_id = m.project_id
GROUP BY s.student_id, p.project_id;

-- ----------------------------------------------------------------------------
-- QUERY 10: Query the Created View
-- ----------------------------------------------------------------------------
SELECT register_number, student_name, project_title, domain, project_status, marks_obtained 
FROM View_StudentProjectSummary
ORDER BY marks_obtained DESC;
