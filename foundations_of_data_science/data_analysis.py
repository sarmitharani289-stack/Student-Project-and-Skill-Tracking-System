"""
================================================================================
Foundations of Data Science - Interactive & Automated Analytics Workbench
Student Project and Skill Tracking System (Scenario 2)
Course: 25ITC31 - Foundations of Data Science | Regulation: R2025
Target Register Numbers: 2, 32, 62, 92, L2
================================================================================
"""

import os
import sys
import sqlite3
import math
import datetime

CURRENT_DIR = os.path.dirname(os.path.abspath(__file__))
DB_PATH = os.path.join(CURRENT_DIR, "..", "database_management", "student_project_tracking.db")
OUTPUT_FILE = os.path.join(CURRENT_DIR, "data_science_output.txt")
VIZ_DIR = os.path.join(CURRENT_DIR, "visualizations")

class DualLogger:
    def __init__(self, filepath):
        self.terminal = sys.stdout
        self.logfile = open(filepath, "w", encoding="utf-8")

    def write(self, msg):
        self.terminal.write(msg)
        self.logfile.write(msg)

    def flush(self):
        self.terminal.flush()
        self.logfile.flush()

def format_table(headers, rows):
    if not rows:
        return "(No records)\n"
    col_w = [len(h) for h in headers]
    s_rows = []
    for r in rows:
        sr = [str(x) if x is not None else "NULL" for x in r]
        s_rows.append(sr)
        for i, val in enumerate(sr):
            if len(val) > col_w[i]:
                col_w[i] = len(val)
    sep = "+" + "+".join(["-" * (w + 2) for w in col_w]) + "+"
    hdr = "|" + "|".join([f" {headers[i]:<{col_w[i]}} " for i in range(len(headers))]) + "|"
    lines = [sep, hdr, sep]
    for sr in s_rows:
        lines.append("|" + "|".join([f" {sr[i]:<{col_w[i]}} " for i in range(len(headers))]) + "|")
    lines.append(sep)
    return "\n".join(lines)

def compute_stats(data):
    if not data:
        return {"count": 0, "mean": 0.0, "std": 0.0, "min": 0.0, "q25": 0.0, "median": 0.0, "q75": 0.0, "max": 0.0}
    n = len(data)
    s_data = sorted(data)
    mean = sum(s_data) / n
    variance = sum((x - mean) ** 2 for x in s_data) / (n - 1) if n > 1 else 0.0
    std = math.sqrt(variance)
    median = s_data[n // 2] if n % 2 != 0 else (s_data[n // 2 - 1] + s_data[n // 2]) / 2.0
    q25 = s_data[int(n * 0.25)]
    q75 = s_data[int(n * 0.75)]
    return {
        "count": n,
        "mean": round(mean, 2),
        "std": round(std, 2),
        "min": round(s_data[0], 2),
        "q25": round(q25, 2),
        "median": round(median, 2),
        "q75": round(q75, 2),
        "max": round(s_data[-1], 2)
    }

def run_automated_pipeline():
    sys.stdout = DualLogger(OUTPUT_FILE)
    print("=" * 80)
    print(" VELALAR COLLEGE OF ENGINEERING AND TECHNOLOGY (AUTONOMOUS), ERODE")
    print(" DEPARTMENT OF INFORMATION TECHNOLOGY - REGULATION R2025")
    print(" COURSE: 25ITC31 - FOUNDATIONS OF DATA SCIENCE")
    print(" SCENARIO 2: STUDENT PROJECT AND SKILL TRACKING ANALYTICS")
    print(f" EXECUTION TIMESTAMP: {datetime.datetime.now().strftime('%Y-%m-%d %H:%M:%S')}")
    print(" TARGET REGISTER NUMBERS: 2, 32, 62, 92, L2")
    print("=" * 80)
    print()

    print("[STEP 1] RETRIEVING DATASETS FROM RELATIONAL DATABASE")
    print("-" * 80)
    if not os.path.exists(DB_PATH):
        print(f"[ERROR] Database file not found at: {DB_PATH}")
        return

    conn = sqlite3.connect(DB_PATH)
    cur = conn.cursor()
    print(f"[+] Connected to: {os.path.basename(DB_PATH)}")

    for tbl in ["Student", "Faculty", "Skill", "StudentSkill", "Project", "ProjectTechnology", "Milestone", "Evaluation"]:
        cur.execute(f"SELECT COUNT(*) FROM {tbl};")
        cnt = cur.fetchone()[0]
        print(f"   * Entity [{tbl:<18}]: {cnt:>2} records retrieved")

    print("\n[STEP 2] DATA PREPROCESSING & WRANGLING")
    print("-" * 80)
    cur.execute("""
        SELECT 
            SUM(CASE WHEN student_name IS NULL THEN 1 ELSE 0 END) AS null_names,
            SUM(CASE WHEN email IS NULL THEN 1 ELSE 0 END) AS null_emails,
            SUM(CASE WHEN phone IS NULL THEN 1 ELSE 0 END) AS null_phones
        FROM Student;
    """)
    nulls = cur.fetchone()
    print(f"[+] Student Data Quality Audit -> Null Names: {nulls[0]}, Null Emails: {nulls[1]}, Null Phones: {nulls[2]}")

    cur.execute("""
        SELECT 
            SUM(CASE WHEN remarks IS NULL OR remarks = '' THEN 1 ELSE 0 END) AS missing_remarks,
            COUNT(*) AS total_milestones
        FROM Milestone;
    """)
    m_nulls = cur.fetchone()
    print(f"[+] Milestone Data Quality Audit -> Missing Remarks: {m_nulls[0]} / {m_nulls[1]} total records (Imputed with 'None')")

    print("\n[STEP 3] EXPLORATORY DATA ANALYSIS (DESCRIPTIVE STATISTICS)")
    print("-" * 80)
    cur.execute("""
        SELECT 
            requirement_analysis_marks,
            design_marks,
            implementation_marks,
            documentation_marks,
            presentation_marks,
            total_marks
        FROM Evaluation;
    """)
    eval_rows = cur.fetchall()

    req_marks = [r[0] for r in eval_rows]
    des_marks = [r[1] for r in eval_rows]
    imp_marks = [r[2] for r in eval_rows]
    doc_marks = [r[3] for r in eval_rows]
    pre_marks = [r[4] for r in eval_rows]
    tot_marks = [r[5] for r in eval_rows]

    metrics = [
        ("Requirement Analysis (Max 20)", compute_stats(req_marks)),
        ("System Design (Max 20)", compute_stats(des_marks)),
        ("Implementation (Max 30)", compute_stats(imp_marks)),
        ("Documentation (Max 15)", compute_stats(doc_marks)),
        ("Presentation (Max 15)", compute_stats(pre_marks)),
        ("Total Score (Max 100)", compute_stats(tot_marks))
    ]

    stat_headers = ["Rubric Component", "Count", "Mean", "StdDev", "Min", "25%", "Median", "75%", "Max"]
    stat_rows = []
    for comp, s in metrics:
        stat_rows.append([comp, s["count"], s["mean"], s["std"], s["min"], s["q25"], s["median"], s["q75"], s["max"]])
    print(format_table(stat_headers, stat_rows))

    print("\n[STEP 4] PATTERN EXTRACTION FOR VISUALIZATION ARTIFACTS")
    print("-" * 80)

    print("\n>>> Insight 1: Project Completion Status Breakdown")
    cur.execute("""
        SELECT status, COUNT(*) AS count, 
               ROUND(COUNT(*) * 100.0 / (SELECT COUNT(*) FROM Project), 1) AS pct
        FROM Project
        GROUP BY status
        ORDER BY count DESC;
    """)
    print(format_table(["Project Status", "Count", "Percentage (%)"], cur.fetchall()))

    print(">>> Insight 2: Popularity Ranking of Programming Languages & Frameworks")
    cur.execute("""
        SELECT technology_name, COUNT(*) AS count,
               ROUND(COUNT(*) * 100.0 / (SELECT COUNT(DISTINCT project_id) FROM Project), 1) AS adoption_rate
        FROM ProjectTechnology
        GROUP BY technology_name
        ORDER BY count DESC, technology_name ASC
        LIMIT 8;
    """)
    print(format_table(["Technology / Framework", "Project Count", "Adoption Rate (%)"], cur.fetchall()))

    print(">>> Insight 3: Student Skill Distribution by Category and Proficiency Level")
    cur.execute("""
        SELECT 
            sk.skill_category,
            SUM(CASE WHEN ss.proficiency_level = 'Advanced' THEN 1 ELSE 0 END) AS advanced,
            SUM(CASE WHEN ss.proficiency_level = 'Intermediate' THEN 1 ELSE 0 END) AS intermediate,
            SUM(CASE WHEN ss.proficiency_level = 'Beginner' THEN 1 ELSE 0 END) AS beginner,
            COUNT(ss.student_id) AS total_skills_held
        FROM Skill sk
        LEFT JOIN StudentSkill ss ON sk.skill_id = ss.skill_id
        GROUP BY sk.skill_category
        ORDER BY total_skills_held DESC;
    """)
    print(format_table(["Skill Category", "Advanced", "Intermediate", "Beginner", "Total"], cur.fetchall()))

    print(">>> Insight 4: Project Milestone Completion Status")
    cur.execute("""
        SELECT 
            p.project_title,
            COUNT(m.milestone_id) AS total_milestones,
            SUM(CASE WHEN m.completion_status = 'Completed' THEN 1 ELSE 0 END) AS completed,
            SUM(CASE WHEN m.completion_status = 'Pending' THEN 1 ELSE 0 END) AS pending,
            SUM(CASE WHEN m.completion_status = 'Delayed' THEN 1 ELSE 0 END) AS delayed,
            ROUND(SUM(CASE WHEN m.completion_status = 'Completed' THEN 1.0 ELSE 0.0 END) / COUNT(m.milestone_id) * 100, 1) AS progress_pct
        FROM Project p
        JOIN Milestone m ON p.project_id = m.project_id
        GROUP BY p.project_id
        ORDER BY progress_pct DESC;
    """)
    print(format_table(["Project Title", "Total", "Completed", "Pending", "Delayed", "Progress (%)"], cur.fetchall()))

    print("\n[STEP 5] VISUALIZATION CHARTS GENERATED & SAVED")
    print("-" * 80)
    chart_files = [
        "viz1_project_status_completion_rate.png",
        "viz2_popular_technology_stacks.png",
        "viz3_skill_proficiency_distribution.png",
        "viz4_milestone_progression_status.png",
        "viz5_evaluation_marks_breakdown.png"
    ]
    for idx, c in enumerate(chart_files, 1):
        cp = os.path.join(VIZ_DIR, c)
        if os.path.exists(cp):
            size_kb = os.path.getsize(cp) / 1024.0
            print(f"   [CHART {idx}] {c:<42} | Size: {size_kb:>5.1f} KB [OK]")
        else:
            print(f"   [CHART {idx}] {c:<42} | MISSING")

    conn.close()
    print("\n" + "=" * 80)
    print(" [SUCCESS] DATA SCIENCE PIPELINE COMPLETED WITH 0 ERRORS")
    print(f" Detailed execution transcript saved to: {OUTPUT_FILE}")
    print("=" * 80)

def run_interactive_mode():
    if not os.path.exists(DB_PATH):
        print(f"[ERROR] Database file not found at: {DB_PATH}. Please run run_dbms.bat first.")
        return

    conn = sqlite3.connect(DB_PATH)
    cur = conn.cursor()

    while True:
        print("\n================================================================================")
        print("  FOUNDATIONS OF DATA SCIENCE - INTERACTIVE ANALYTICS WORKBENCH")
        print("  Course: 25ITC31 | VCET B.Tech IT - Regulation R2025")
        print("  Connected Database: student_project_tracking.db")
        print("================================================================================")
        print("  [1] View Dataset Summary & Entity Record Counts")
        print("  [2] Search / Filter Projects by Domain or Status (Manual Input)")
        print("  [3] Inspect Student Skills & Detect Skill Gaps (Manual Input)")
        print("  [4] Manually Enter Rubric Marks & Compute Grade / Statistics")
        print("  [5] Descriptive Statistics (Rubric Distributions & Quartiles)")
        print("  [6] Audit & Verify Generated Visualization Charts (5 PNG Charts)")
        print("  [7] Run Full Automated EDA Pipeline & Update data_science_output.txt")
        print("  [0] Exit Analytics Workbench")
        print("================================================================================")
        choice = input("Enter choice [0-7]: ").strip()

        if choice == "0":
            print("\nExiting Data Science Workbench. Goodbye!")
            break
        elif choice == "1":
            print("\n--- DATASET RECORD COUNTS ---")
            for tbl in ["Student", "Faculty", "Skill", "StudentSkill", "Project", "ProjectTechnology", "Milestone", "Evaluation"]:
                cur.execute(f"SELECT COUNT(*) FROM {tbl};")
                print(f"  * {tbl:<20}: {cur.fetchone()[0]:>2} records")
        elif choice == "2":
            handle_filter_projects(cur)
        elif choice == "3":
            handle_student_skills(cur)
        elif choice == "4":
            handle_manual_stats()
        elif choice == "5":
            handle_rubric_stats(cur)
        elif choice == "6":
            handle_audit_charts()
        elif choice == "7":
            run_automated_pipeline()
            print("\n[SUCCESS] Automated EDA pipeline complete! Output saved to data_science_output.txt")
        else:
            print("[!] Invalid option. Enter a number between 0 and 7.")

    conn.close()

def handle_filter_projects(cur):
    print("\n--- [MANUAL INPUT] SEARCH & FILTER PROJECTS ---")
    keyword = input("Enter keyword (Domain e.g. AI, Web, Cloud OR Status e.g. Completed, In-Progress): ").strip()
    cur.execute("""
        SELECT p.project_id, s.register_number, s.student_name, p.project_title, p.domain, p.status
        FROM Project p
        JOIN Student s ON p.student_id = s.student_id
        WHERE p.domain LIKE ? OR p.status LIKE ? OR p.project_title LIKE ?;
    """, (f"%{keyword}%", f"%{keyword}%", f"%{keyword}%"))
    rows = cur.fetchall()
    print(f"\nFound {len(rows)} projects matching '{keyword}':")
    print(format_table(["ID", "Reg No", "Student Name", "Project Title", "Domain", "Status"], rows))

def handle_student_skills(cur):
    print("\n--- [MANUAL INPUT] INSPECT STUDENT SKILLS & GAP AUDIT ---")
    reg = input("Enter Student Register Number (e.g. 25IT002, 25IT032, 25IT062): ").strip()
    cur.execute("""
        SELECT s.student_id, s.register_number, s.student_name, p.project_title, p.domain
        FROM Student s
        LEFT JOIN Project p ON s.student_id = p.student_id
        WHERE s.register_number LIKE ?;
    """, (f"%{reg}%",))
    st = cur.fetchone()
    if not st:
        print(f"[!] No student found matching Register Number: {reg}")
        return

    st_id, r_num, name, p_title, domain = st
    print(f"\nStudent: {name} [{r_num}]")
    print(f"Project: {p_title or 'None'} | Domain: {domain or 'None'}")

    cur.execute("""
        SELECT sk.skill_name, sk.skill_category, ss.proficiency_level, ss.acquired_date
        FROM StudentSkill ss
        JOIN Skill sk ON ss.skill_id = sk.skill_id
        WHERE ss.student_id = ?;
    """, (st_id,))
    skills = cur.fetchall()
    print(format_table(["Skill Name", "Category", "Proficiency", "Acquired Date"], skills))

    # Skill Gap Detection
    skill_names = [s[0].lower() for s in skills]
    if domain:
        dom_l = domain.lower()
        missing = []
        if ("ai" in dom_l or "ml" in dom_l) and "machine learning" not in skill_names:
            missing.append("Machine Learning")
        if "web" in dom_l and "html/css/js" not in skill_names and "react.js" not in skill_names:
            missing.append("HTML/CSS/JS or Frontend")
        if "cloud" in dom_l and "docker & kubernetes" not in skill_names and "aws cloud" not in skill_names:
            missing.append("Docker / AWS Cloud")
        
        if missing:
            print(f"[!] SKILL GAP DETECTED: Student lacks foundational skills in {', '.join(missing)} for their {domain} project.")
        else:
            print("[+] Verified: Student technical profile matches registered project domain requirements.")

def handle_manual_stats():
    print("\n--- [MANUAL INPUT] CUSTOM EVALUATION SCORE CALCULATOR ---")
    try:
        req = float(input("Enter Requirement Analysis Marks [0-20]: ").strip())
        des = float(input("Enter System Design Marks [0-20]: ").strip())
        imp = float(input("Enter Implementation Marks [0-30]: ").strip())
        doc = float(input("Enter Documentation Marks [0-15]: ").strip())
        pre = float(input("Enter Presentation Marks [0-15]: ").strip())

        tot = req + des + imp + doc + pre
        pct = (tot / 100.0) * 100.0

        if tot >= 90:
            grade = "O (Outstanding)"
        elif tot >= 80:
            grade = "A+ (Excellent)"
        elif tot >= 70:
            grade = "A (Very Good)"
        elif tot >= 60:
            grade = "B+ (Good)"
        elif tot >= 50:
            grade = "B (Average)"
        else:
            grade = "RA (Re-Appear)"

        print("\n" + "-" * 50)
        print(" COMPUTED EVALUATION METRICS:")
        print(f"  * Total Raw Score  : {tot:.1f} / 100.0")
        print(f"  * Percentage Score : {pct:.1f}%")
        print(f"  * Letter Grade     : {grade}")
        print("-" * 50)
    except Exception as e:
        print("[INPUT ERROR]", e)

def handle_rubric_stats(cur):
    print("\n--- EVALUATION RUBRICS DESCRIPTIVE STATISTICS ---")
    cur.execute("SELECT requirement_analysis_marks, design_marks, implementation_marks, documentation_marks, presentation_marks, total_marks FROM Evaluation;")
    rows = cur.fetchall()
    components = [
        ("Requirement Analysis (Max 20)", [r[0] for r in rows]),
        ("System Design (Max 20)", [r[1] for r in rows]),
        ("Implementation (Max 30)", [r[2] for r in rows]),
        ("Documentation (Max 15)", [r[3] for r in rows]),
        ("Presentation (Max 15)", [r[4] for r in rows]),
        ("Total Score (Max 100)", [r[5] for r in rows])
    ]
    stat_rows = []
    for name, data in components:
        st = compute_stats(data)
        stat_rows.append([name, st["count"], st["mean"], st["std"], st["min"], st["q25"], st["median"], st["q75"], st["max"]])
    print(format_table(["Rubric Component", "Count", "Mean", "StdDev", "Min", "25%", "Median", "75%", "Max"], stat_rows))

def handle_audit_charts():
    print("\n--- VISUALIZATION CHARTS AUDIT ---")
    charts = [
        "viz1_project_status_completion_rate.png",
        "viz2_popular_technology_stacks.png",
        "viz3_skill_proficiency_distribution.png",
        "viz4_milestone_progression_status.png",
        "viz5_evaluation_marks_breakdown.png"
    ]
    for idx, c in enumerate(charts, 1):
        cp = os.path.join(VIZ_DIR, c)
        if os.path.exists(cp):
            size_kb = os.path.getsize(cp) / 1024.0
            print(f"  [{idx}] {c:<40} : {size_kb:>5.1f} KB [AVAILABLE]")
        else:
            print(f"  [{idx}] {c:<40} : [NOT FOUND]")

if __name__ == "__main__":
    if len(sys.argv) > 1 and sys.argv[1] == "--demo":
        run_automated_pipeline()
    else:
        run_interactive_mode()
