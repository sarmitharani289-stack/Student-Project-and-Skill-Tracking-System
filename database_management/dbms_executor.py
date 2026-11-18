"""
DBMS Script & SQL Execution Engine - Interactive & Automated
Student Project and Skill Tracking System (Scenario 2)
Course: 25ITT31 - Database Management Systems | Regulation: R2025
Target Register Numbers: 2, 32, 62, 92, L2
"""

import os
import sys
import sqlite3
import datetime

DB_FILE = os.path.join(os.path.dirname(__file__), "student_project_tracking.db")
SCHEMA_FILE = os.path.join(os.path.dirname(__file__), "schema.sql")
INSERT_FILE = os.path.join(os.path.dirname(__file__), "insert_data.sql")
QUERIES_FILE = os.path.join(os.path.dirname(__file__), "queries.sql")
OUTPUT_FILE = os.path.join(os.path.dirname(__file__), "dbms_output.txt")

class Logger(object):
    def __init__(self, filename):
        self.terminal = sys.stdout
        self.log = open(filename, "w", encoding="utf-8")

    def write(self, message):
        self.terminal.write(message)
        self.log.write(message)

    def flush(self):
        self.terminal.flush()
        self.log.flush()

def format_table(headers, rows):
    if not rows:
        return "(No rows returned)\n"
    col_widths = [len(h) for h in headers]
    str_rows = []
    for row in rows:
        str_row = [str(item) if item is not None else "NULL" for item in row]
        str_rows.append(str_row)
        for i, val in enumerate(str_row):
            if len(val) > col_widths[i]:
                col_widths[i] = len(val)
    
    sep_line = "+" + "+".join(["-" * (w + 2) for w in col_widths]) + "+"
    header_line = "|" + "|".join([f" {h:<{col_widths[i]}} " for i, h in enumerate(headers)]) + "|"
    
    result = [sep_line, header_line, sep_line]
    for row in str_rows:
        row_line = "|" + "|".join([f" {row[i]:<{col_widths[i]}} " for i in range(len(headers))]) + "|"
        result.append(row_line)
    result.append(sep_line)
    return "\n".join(result)

def initialize_database():
    if not os.path.exists(DB_FILE):
        run_full_benchmark(quiet=True)
    conn = sqlite3.connect(DB_FILE)
    conn.execute("PRAGMA foreign_keys = ON;")
    return conn

def run_full_benchmark(quiet=False):
    if not quiet:
        sys.stdout = Logger(OUTPUT_FILE)
    
    print("=" * 80)
    print(" VELALAR COLLEGE OF ENGINEERING AND TECHNOLOGY (AUTONOMOUS), ERODE")
    print(" DEPARTMENT OF INFORMATION TECHNOLOGY - REGULATION R2025")
    print(" COURSE: 25ITT31 - DATABASE MANAGEMENT SYSTEMS")
    print(" SCENARIO 2: STUDENT PROJECT AND SKILL TRACKING SYSTEM")
    print(f" EXECUTION TIMESTAMP: {datetime.datetime.now().strftime('%Y-%m-%d %H:%M:%S')}")
    print(" REGISTER NUMBERS: 2, 32, 62, 92, L2")
    print("=" * 80)
    print()

    # Step 1: Initialize Database
    if os.path.exists(DB_FILE):
        try:
            os.remove(DB_FILE)
            print(f"[*] Reset previous database instance: {os.path.basename(DB_FILE)}")
        except Exception:
            pass
            
    conn = sqlite3.connect(DB_FILE)
    conn.execute("PRAGMA foreign_keys = ON;")
    cursor = conn.cursor()
    print(f"[+] Initialized SQLite Relational Database Engine at: {DB_FILE}")

    # Step 2: Execute Schema (DDL)
    print("\n" + "-" * 80)
    print(" STEP 1: EXECUTING DATABASE SCHEMA (DDL) CREATION")
    print("-" * 80)
    with open(SCHEMA_FILE, "r", encoding="utf-8") as f:
        schema_sql = f.read()
    cursor.executescript(schema_sql)
    conn.commit()

    # Verify Tables Created
    cursor.execute("SELECT name FROM sqlite_master WHERE type='table' AND name NOT LIKE 'sqlite_%' ORDER BY name;")
    tables = cursor.fetchall()
    print("[SUCCESS] All relational tables created successfully:")
    for idx, tbl in enumerate(tables, 1):
        cursor.execute(f"PRAGMA table_info({tbl[0]});")
        col_count = len(cursor.fetchall())
        print(f"   {idx}. Table: {tbl[0]:<20} | Columns: {col_count}")

    # Step 3: Execute Sample Data Insertions (DML)
    print("\n" + "-" * 80)
    print(" STEP 2: INSERTING POPULATION DATA (DML) - TARGET REGISTER NUMBERS INCLUDED")
    print("-" * 80)
    with open(INSERT_FILE, "r", encoding="utf-8") as f:
        insert_sql = f.read()
    cursor.executescript(insert_sql)
    conn.commit()

    for tbl in tables:
        cursor.execute(f"SELECT COUNT(*) FROM {tbl[0]};")
        cnt = cursor.fetchone()[0]
        print(f"   - Table [{tbl[0]:<18}]: {cnt:>2} records inserted.")
    print("[SUCCESS] Comprehensive test dataset populated across all relational entities.")

    # Create Database View
    cursor.execute("DROP VIEW IF EXISTS View_StudentProjectSummary;")
    cursor.execute("""
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
    """)
    conn.commit()
    print("[SUCCESS] Relational View 'View_StudentProjectSummary' created successfully.")

    # Step 4: Execute Analytical SQL Queries
    print("\n" + "=" * 80)
    print(" STEP 3: EXECUTING ADVANCED SQL QUERIES & ANALYTICAL WORKLOADS")
    print("=" * 80)

    queries = [
        ("QUERY 1: Student Project & Faculty Advisor Mapping (INNER & LEFT JOIN)",
         """SELECT s.register_number, s.student_name, p.project_title, p.domain, p.status AS project_status,
                   f.faculty_name AS faculty_guide, f.email AS faculty_email
            FROM Student s
            INNER JOIN Project p ON s.student_id = p.student_id
            LEFT JOIN Faculty f ON p.faculty_id = f.faculty_id
            ORDER BY s.register_number;"""),

        ("QUERY 2: Multi-Table Join & Technology Stacks (GROUP_CONCAT)",
         """SELECT p.project_id, p.project_title, s.student_name,
                   GROUP_CONCAT(pt.technology_name, ', ') AS technologies_used
            FROM Project p
            JOIN Student s ON p.student_id = s.student_id
            JOIN ProjectTechnology pt ON p.project_id = pt.project_id
            GROUP BY p.project_id, p.project_title, s.student_name
            ORDER BY p.project_id;"""),

        ("QUERY 3: Milestone Progress & Completion Rate (COUNT, CASE WHEN, ROUND)",
         """SELECT p.project_id, p.project_title, s.student_name,
                   COUNT(m.milestone_id) AS total_milestones,
                   SUM(CASE WHEN m.completion_status = 'Completed' THEN 1 ELSE 0 END) AS completed,
                   SUM(CASE WHEN m.completion_status = 'Delayed' THEN 1 ELSE 0 END) AS delayed,
                   SUM(CASE WHEN m.completion_status = 'Pending' THEN 1 ELSE 0 END) AS pending,
                   ROUND((SUM(CASE WHEN m.completion_status = 'Completed' THEN 1.0 ELSE 0.0 END) / COUNT(m.milestone_id)) * 100.0, 1) AS progress_pct
            FROM Project p
            JOIN Student s ON p.student_id = s.student_id
            LEFT JOIN Milestone m ON p.project_id = m.project_id
            GROUP BY p.project_id, p.project_title, s.student_name
            ORDER BY progress_pct DESC;"""),

        ("QUERY 4: Popularity Ranking of Project Technologies (GROUP BY & Adoption %)",
         """SELECT pt.technology_name, COUNT(pt.project_id) AS usage_count,
                   ROUND((COUNT(pt.project_id) * 100.0 / (SELECT COUNT(DISTINCT project_id) FROM Project)), 1) AS adoption_pct
            FROM ProjectTechnology pt
            GROUP BY pt.technology_name
            ORDER BY usage_count DESC, pt.technology_name ASC;"""),

        ("QUERY 5: Student Skill Matrix by Proficiency Level (Beginner / Inter / Adv)",
         """SELECT sk.skill_category, sk.skill_name,
                   SUM(CASE WHEN ss.proficiency_level = 'Advanced' THEN 1 ELSE 0 END) AS advanced,
                   SUM(CASE WHEN ss.proficiency_level = 'Intermediate' THEN 1 ELSE 0 END) AS intermediate,
                   SUM(CASE WHEN ss.proficiency_level = 'Beginner' THEN 1 ELSE 0 END) AS beginner,
                   COUNT(ss.student_id) AS total_students
            FROM Skill sk
            LEFT JOIN StudentSkill ss ON sk.skill_id = ss.skill_id
            GROUP BY sk.skill_category, sk.skill_name
            ORDER BY sk.skill_category, total_students DESC;"""),

        ("QUERY 6: Evaluation Marks Rubric & Academic Grade Classification (CASE)",
         """SELECT s.register_number, s.student_name, p.project_title,
                   e.requirement_analysis_marks AS req_20, e.design_marks AS design_20,
                   e.implementation_marks AS impl_30, e.documentation_marks AS doc_15,
                   e.presentation_marks AS pres_15, e.total_marks AS total_100,
                   CASE 
                       WHEN e.total_marks >= 90 THEN 'O (Outstanding)'
                       WHEN e.total_marks >= 80 THEN 'A+ (Excellent)'
                       WHEN e.total_marks >= 70 THEN 'A (Very Good)'
                       WHEN e.total_marks >= 60 THEN 'B+ (Good)'
                       WHEN e.total_marks >= 50 THEN 'B (Average)'
                       ELSE 'RA (Re-Appear)'
                   END AS grade,
                   f.faculty_name AS mentor
            FROM Evaluation e
            JOIN Project p ON e.project_id = p.project_id
            JOIN Student s ON p.student_id = s.student_id
            JOIN Faculty f ON e.faculty_id = f.faculty_id
            ORDER BY e.total_marks DESC;"""),

        ("QUERY 7: Skill Gap Analysis - AI Projects lacking Machine Learning Skill",
         """SELECT s.register_number, s.student_name, p.project_title, p.domain
            FROM Student s
            JOIN Project p ON s.student_id = p.student_id
            WHERE p.domain LIKE '%AI%'
              AND NOT EXISTS (
                  SELECT 1 FROM StudentSkill ss
                  JOIN Skill sk ON ss.skill_id = sk.skill_id
                  WHERE ss.student_id = s.student_id AND sk.skill_name = 'Machine Learning'
              );"""),

        ("QUERY 8: Delayed Milestone Audit and Alerting System",
         """SELECT s.register_number, s.student_name, p.project_title,
                   m.milestone_name, m.submission_date, m.completion_status, m.remarks
            FROM Milestone m
            JOIN Project p ON m.project_id = p.project_id
            JOIN Student s ON p.student_id = s.student_id
            WHERE m.completion_status = 'Delayed'
            ORDER BY m.submission_date;"""),

        ("QUERY 9: Querying the Database View (View_StudentProjectSummary)",
         """SELECT register_number, student_name, project_title, domain, project_status, marks_obtained 
            FROM View_StudentProjectSummary
            ORDER BY marks_obtained DESC;""")
    ]

    for title, sql in queries:
        print(f"\n>>> {title}")
        print("-" * 80)
        cursor.execute(sql)
        headers = [desc[0] for desc in cursor.description]
        rows = cursor.fetchall()
        print(format_table(headers, rows))

    conn.close()
    print("\n" + "=" * 80)
    print(" [COMPLETED] DBMS Execution finished successfully with 0 errors.")
    print(f" All query execution logs written to: {OUTPUT_FILE}")
    print("=" * 80)

def run_interactive_mode():
    conn = initialize_database()
    cursor = conn.cursor()

    while True:
        print("\n================================================================================")
        print("  DATABASE MANAGEMENT SYSTEMS (DBMS) - INTERACTIVE CONSOLE")
        print("  Course: 25ITT31 | VCET B.Tech IT - Regulation R2025")
        print("  Connected Database: student_project_tracking.db")
        print("================================================================================")
        print("  [1] View Table Records (Student, Project, Skill, Milestone, Evaluation)")
        print("  [2] Manually Insert a New Student Record")
        print("  [3] Manually Insert a New Project Record")
        print("  [4] Manually Add / Update Project Milestone")
        print("  [5] Manually Record Project Evaluation Marks")
        print("  [6] Execute Predefined Analytical Query (Queries 1 to 9)")
        print("  [7] Execute Custom SQL Query (SELECT, INSERT, UPDATE, DELETE)")
        print("  [8] Run Complete Automated Benchmark & Regenerate dbms_output.txt")
        print("  [0] Exit DBMS Console")
        print("================================================================================")
        choice = input("Enter choice [0-8]: ").strip()

        if choice == "0":
            print("\nExiting DBMS Interactive Console. Goodbye!")
            break
        elif choice == "1":
            handle_view_table(cursor)
        elif choice == "2":
            handle_insert_student(cursor, conn)
        elif choice == "3":
            handle_insert_project(cursor, conn)
        elif choice == "4":
            handle_insert_milestone(cursor, conn)
        elif choice == "5":
            handle_insert_evaluation(cursor, conn)
        elif choice == "6":
            handle_predefined_query(cursor)
        elif choice == "7":
            handle_custom_query(cursor, conn)
        elif choice == "8":
            run_full_benchmark()
            print("\n[SUCCESS] Benchmark complete! dbms_output.txt regenerated.")
        else:
            print("[!] Invalid option. Please enter a number between 0 and 8.")

    conn.close()

def handle_view_table(cursor):
    tables = ["Student", "Faculty", "Skill", "StudentSkill", "Project", "ProjectTechnology", "Milestone", "Evaluation", "View_StudentProjectSummary"]
    print("\nAvailable Tables / Views:")
    for i, t in enumerate(tables, 1):
        print(f"  [{i}] {t}")
    choice = input("Select table number to view [1-9]: ").strip()
    try:
        idx = int(choice) - 1
        if 0 <= idx < len(tables):
            tbl = tables[idx]
            cursor.execute(f"SELECT * FROM {tbl} LIMIT 25;")
            headers = [d[0] for d in cursor.description]
            rows = cursor.fetchall()
            print(f"\n--- Records from {tbl} (Total Rows: {len(rows)}) ---")
            print(format_table(headers, rows))
        else:
            print("[!] Invalid table selection.")
    except Exception as e:
        print("[ERROR]", e)

def handle_insert_student(cursor, conn):
    print("\n--- [MANUAL INPUT] INSERT NEW STUDENT RECORD ---")
    reg = input("Enter Register Number (e.g. 25IT002): ").strip()
    name = input("Enter Student Full Name: ").strip()
    dept = input("Enter Department [Information Technology]: ").strip() or "Information Technology"
    year = input("Enter Year of Study (1-4): ").strip()
    email = input("Enter Student Email: ").strip()
    phone = input("Enter Phone Number: ").strip()

    try:
        cursor.execute("""
            INSERT INTO Student (register_number, student_name, department, year_of_study, email, phone)
            VALUES (?, ?, ?, ?, ?, ?);
        """, (reg, name, dept, int(year), email, phone))
        conn.commit()
        print(f"[SUCCESS] Student '{name}' ({reg}) inserted with Student ID #{cursor.lastrowid}!")
    except Exception as e:
        print("[DATABASE ERROR] Insertion failed:", e)

def handle_insert_project(cursor, conn):
    print("\n--- [MANUAL INPUT] INSERT NEW PROJECT RECORD ---")
    reg = input("Enter Student Register Number: ").strip()
    cursor.execute("SELECT student_id FROM Student WHERE register_number = ?;", (reg,))
    row = cursor.fetchone()
    if not row:
        print(f"[!] Student with Register Number '{reg}' not found.")
        return
    st_id = row[0]
    title = input("Enter Project Title: ").strip()
    domain = input("Enter Domain (e.g. AI/ML, Web, Cloud): ").strip()
    desc = input("Enter Brief Description: ").strip()
    start_date = input("Enter Start Date (YYYY-MM-DD) [2025-01-10]: ").strip() or "2025-01-10"
    end_date = input("Enter End Date (YYYY-MM-DD) [2025-04-20]: ").strip() or "2025-04-20"
    
    try:
        cursor.execute("""
            INSERT INTO Project (student_id, faculty_id, project_title, domain, description, start_date, end_date, status)
            VALUES (?, 1, ?, ?, ?, ?, ?, 'In-Progress');
        """, (st_id, title, domain, desc, start_date, end_date))
        p_id = cursor.lastrowid
        conn.commit()
        print(f"[SUCCESS] Project '{title}' registered with Project ID #{p_id}!")
    except Exception as e:
        print("[DATABASE ERROR] Failed to insert project:", e)

def handle_insert_milestone(cursor, conn):
    print("\n--- [MANUAL INPUT] ADD PROJECT MILESTONE ---")
    p_id = input("Enter Project ID: ").strip()
    m_name = input("Enter Milestone Name (e.g. SRS, Design, Coding, Testing): ").strip()
    sub_date = input("Enter Submission Date (YYYY-MM-DD): ").strip() or "2025-03-15"
    status = input("Enter Status (Pending / In-Progress / Completed / Delayed): ").strip() or "In-Progress"
    remarks = input("Enter Faculty Remarks: ").strip() or "Updated via interactive console"

    try:
        cursor.execute("""
            INSERT INTO Milestone (project_id, milestone_name, submission_date, completion_status, remarks)
            VALUES (?, ?, ?, ?, ?);
        """, (int(p_id), m_name, sub_date, status, remarks))
        conn.commit()
        print(f"[SUCCESS] Milestone '{m_name}' added for Project #{p_id}!")
    except Exception as e:
        print("[DATABASE ERROR]", e)

def handle_insert_evaluation(cursor, conn):
    print("\n--- [MANUAL INPUT] RECORD PROJECT EVALUATION MARKS ---")
    p_id = input("Enter Project ID to Evaluate: ").strip()
    try:
        req = float(input("Requirement Analysis Marks (Max 20): ").strip())
        des = float(input("System Design Marks (Max 20): ").strip())
        imp = float(input("Implementation Marks (Max 30): ").strip())
        doc = float(input("Documentation Marks (Max 15): ").strip())
        pre = float(input("Presentation Marks (Max 15): ").strip())
        tot = req + des + imp + doc + pre
        today = datetime.date.today().isoformat()

        cursor.execute("""
            INSERT OR REPLACE INTO Evaluation 
            (project_id, faculty_id, requirement_analysis_marks, design_marks, implementation_marks, documentation_marks, presentation_marks, total_marks, average_marks, evaluation_date)
            VALUES (?, 1, ?, ?, ?, ?, ?, ?, ?, ?);
        """, (int(p_id), req, des, imp, doc, pre, tot, tot, today))
        conn.commit()
        print(f"[SUCCESS] Evaluation recorded! Total Marks: {tot}/100")
    except Exception as e:
        print("[DATABASE ERROR]", e)

def handle_predefined_query(cursor):
    print("\nSelect Predefined Analytical Query:")
    print("  [1] Student Projects & Assigned Faculty Guides")
    print("  [2] Project Technology Stacks (GROUP_CONCAT)")
    print("  [3] Milestone Progress Calculation & Completion %")
    print("  [4] Popularity Ranking of Project Technologies")
    print("  [5] Student Skill Matrix across Proficiency Levels")
    print("  [6] Evaluation Marks Rubric & Academic Grades")
    print("  [7] Skill Gap Analysis (AI Projects missing ML skill)")
    print("  [8] Delayed Milestone Alerting Report")
    print("  [9] Summary View (View_StudentProjectSummary)")
    q_choice = input("Enter query number [1-9]: ").strip()

    sql_dict = {
        "1": "SELECT s.register_number, s.student_name, p.project_title, p.domain, p.status, f.faculty_name FROM Student s JOIN Project p ON s.student_id = p.student_id LEFT JOIN Faculty f ON p.faculty_id = f.faculty_id;",
        "2": "SELECT p.project_id, p.project_title, GROUP_CONCAT(pt.technology_name, ', ') AS tech_stack FROM Project p JOIN ProjectTechnology pt ON p.project_id = pt.project_id GROUP BY p.project_id;",
        "3": "SELECT p.project_id, p.project_title, COUNT(m.milestone_id) AS total, SUM(CASE WHEN m.completion_status = 'Completed' THEN 1 ELSE 0 END) AS completed, ROUND((SUM(CASE WHEN m.completion_status = 'Completed' THEN 1.0 ELSE 0.0 END) / COUNT(m.milestone_id)) * 100, 1) AS progress_pct FROM Project p JOIN Milestone m ON p.project_id = m.project_id GROUP BY p.project_id ORDER BY progress_pct DESC;",
        "4": "SELECT technology_name, COUNT(project_id) AS usage_count FROM ProjectTechnology GROUP BY technology_name ORDER BY usage_count DESC;",
        "5": "SELECT sk.skill_category, sk.skill_name, COUNT(ss.student_id) AS student_count FROM Skill sk LEFT JOIN StudentSkill ss ON sk.skill_id = ss.skill_id GROUP BY sk.skill_category, sk.skill_name ORDER BY student_count DESC;",
        "6": "SELECT s.register_number, s.student_name, p.project_title, e.total_marks, CASE WHEN e.total_marks >= 90 THEN 'O' WHEN e.total_marks >= 80 THEN 'A+' ELSE 'A' END AS grade FROM Evaluation e JOIN Project p ON e.project_id = p.project_id JOIN Student s ON p.student_id = s.student_id;",
        "7": "SELECT s.register_number, s.student_name, p.project_title FROM Student s JOIN Project p ON s.student_id = p.student_id WHERE p.domain LIKE '%AI%' AND NOT EXISTS (SELECT 1 FROM StudentSkill ss JOIN Skill sk ON ss.skill_id = sk.skill_id WHERE ss.student_id = s.student_id AND sk.skill_name = 'Machine Learning');",
        "8": "SELECT s.register_number, s.student_name, p.project_title, m.milestone_name, m.submission_date, m.remarks FROM Milestone m JOIN Project p ON m.project_id = p.project_id JOIN Student s ON p.student_id = s.student_id WHERE m.completion_status = 'Delayed';",
        "9": "SELECT * FROM View_StudentProjectSummary ORDER BY marks_obtained DESC;"
    }

    if q_choice in sql_dict:
        try:
            cursor.execute(sql_dict[q_choice])
            headers = [d[0] for d in cursor.description]
            rows = cursor.fetchall()
            print(f"\n--- Result for Query {q_choice} ---")
            print(format_table(headers, rows))
        except Exception as e:
            print("[ERROR]", e)
    else:
        print("[!] Invalid query number.")

def handle_custom_query(cursor, conn):
    print("\n--- [MANUAL INPUT] EXECUTE AD-HOC SQL QUERY ---")
    print("Enter custom SQL statement (e.g. SELECT * FROM Student WHERE year_of_study = 3;):")
    sql = input("SQL> ").strip()
    if not sql:
        return
    try:
        cursor.execute(sql)
        if sql.strip().upper().startswith("SELECT") or sql.strip().upper().startswith("PRAGMA"):
            headers = [d[0] for d in cursor.description] if cursor.description else ["Result"]
            rows = cursor.fetchall()
            print(f"\n--- Query Results ({len(rows)} rows) ---")
            print(format_table(headers, rows))
        else:
            conn.commit()
            print(f"[SUCCESS] Query executed successfully. Rows affected: {cursor.rowcount}")
    except Exception as e:
        print("[SQL EXECUTION ERROR]", e)

if __name__ == "__main__":
    if len(sys.argv) > 1 and sys.argv[1] == "--demo":
        run_full_benchmark()
    else:
        run_interactive_mode()
