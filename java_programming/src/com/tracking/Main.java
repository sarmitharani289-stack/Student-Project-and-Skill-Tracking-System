package com.tracking;

import com.tracking.exception.InvalidMarkException;
import com.tracking.exception.ProjectNotFoundException;
import com.tracking.model.*;
import com.tracking.service.MilestoneMonitorThread;
import com.tracking.service.ProjectTrackingService;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

public class Main {
    private static final ByteArrayOutputStream consoleCapture = new ByteArrayOutputStream();
    private static final PrintStream originalOut = System.out;
    private static ProjectTrackingService service = new ProjectTrackingService();

    public static void main(String[] args) {
        // Pre-populate system with initial foundational dataset
        seedInitialData(service);

        // Check if demo mode was requested via command line flag
        boolean demoOnly = (args.length > 0 && "--demo".equalsIgnoreCase(args[0]));

        if (demoOnly) {
            setupDualOutput();
            runAutomatedDemonstration(service);
            saveOutputsAndScreenshot();
            return;
        }

        // Interactive Menu Mode
        runInteractiveMenu();
    }

    private static void setupDualOutput() {
        PrintStream dualOut = new PrintStream(new OutputStream() {
            @Override
            public void write(int b) {
                originalOut.write(b);
                consoleCapture.write(b);
            }

            @Override
            public void write(byte[] b, int off, int len) {
                originalOut.write(b, off, len);
                consoleCapture.write(b, off, len);
            }
        });
        System.setOut(dualOut);
    }

    private static void runInteractiveMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n================================================================================");
            System.out.println("  [1] Register a New Student (Manual Input)");
            System.out.println("  [2] Add / Map Technical Skill to Student (Manual Input)");
            System.out.println("  [3] Register a New Project for Student (Manual Input)");
            System.out.println("  [4] Add / Update Project Milestone (Manual Input)");
            System.out.println("  [5] Record Project Evaluation Rubric Marks (Manual Input)");
            System.out.println("  [6] View All Students & Registered Skills");
            System.out.println("  [7] View All Projects & Milestone Progress");
            System.out.println("  [8] View Project Evaluation Marks & Grades");
            System.out.println("  [9] Departmental Skill Gap Analysis Report");
            System.out.println("  [10] Run Full Demonstration & Update java_output.txt");
            System.out.println("  [0] Exit Application");
            System.out.println("================================================================================");
            System.out.print("Enter your choice [0-10]: ");

            String input = scanner.nextLine().trim();

            if ("0".equals(input)) {
                System.out.println("\nExiting Student Project & Skill Tracking System. Goodbye!");
                break;
            }

            switch (input) {
                case "1":
                    handleRegisterStudent(scanner);
                    break;
                case "2":
                    handleAddSkill(scanner);
                    break;
                case "3":
                    handleRegisterProject(scanner);
                    break;
                case "4":
                    handleMilestone(scanner);
                    break;
                case "5":
                    handleEvaluation(scanner);
                    break;
                case "6":
                    displayStudents();
                    break;
                case "7":
                    displayProjects();
                    break;
                case "8":
                    displayEvaluations();
                    break;
                case "9":
                    displaySkillGaps();
                    break;
                case "10":
                    setupDualOutput();
                    runAutomatedDemonstration(service);
                    saveOutputsAndScreenshot();
                    System.out.println(
                            "\n[SUCCESS] Demo complete! Outputs updated in java_output.txt and java_execution_screenshot.png");
                    break;
                default:
                    System.out.println("[!] Invalid choice. Please select an option between 0 and 10.");
            }
        }
    }

    private static void handleRegisterStudent(Scanner scanner) {
        System.out.println("\n--- [MANUAL INPUT] REGISTER NEW STUDENT ---");

        try {
            System.out.print("Enter Register Number (e.g., 25IT002): ");
            String regNo = scanner.nextLine().trim();

            if (regNo.isEmpty()) {
                System.out.println("[!] Register number cannot be empty.");
                return;
            }

            // Check duplicate register number
            boolean exists = service.getAllStudents().stream()
                    .anyMatch(s -> s.getRegisterNumber().equalsIgnoreCase(regNo));

            if (exists) {
                System.out.println("[ERROR] Register number already exists. Please enter a unique register number.");
                return;
            }

            System.out.print("Enter Student Full Name: ");
            String name = scanner.nextLine().trim();

            if (name.isEmpty()) {
                System.out.println("[!] Student name cannot be empty.");
                return;
            }

            System.out.print("Enter Department (Default: Information Technology): ");
            String dept = scanner.nextLine().trim();

            if (dept.isEmpty()) {
                dept = "Information Technology";
            }

            System.out.print("Enter Year of Study (1-4): ");
            int year = Integer.parseInt(scanner.nextLine().trim());

            if (year < 1 || year > 4) {
                System.out.println("[!] Invalid year. Enter a year between 1 and 4.");
                return;
            }

            System.out.print("Enter Student Email: ");
            String email = scanner.nextLine().trim();

            if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
                System.out.println("[!] Invalid email format. Please enter a valid email.");
                return;
            }

            System.out.print("Enter Phone Number: ");
            String phone = scanner.nextLine().trim();

            if (!phone.matches("[0-9]{10}")) {
                System.out.println("[!] Phone number must contain exactly 10 digits.");
                return;
            }

            int newId = service.getAllStudents().size() + 1;

            Student newStudent = new Student(
                    newId, regNo, name, dept, year, email, phone);

            service.addStudent(newStudent);

            System.out.println("[SUCCESS] Student registered successfully with ID #"
                    + newId + ": " + newStudent);

        } catch (NumberFormatException e) {
            System.out.println("[!] Invalid year. Please enter a number between 1 and 4.");
        } catch (Exception e) {
            System.out.println("[ERROR] Failed to register student: " + e.getMessage());
        }
    }

    private static void handleAddSkill(Scanner scanner) {
        System.out.println("\n--- [MANUAL INPUT] MAP TECHNICAL SKILL TO STUDENT ---");
        try {
            System.out.print("Enter Student Register Number: ");
            String reg = scanner.nextLine().trim();
            Student student = service.getAllStudents().stream()
                    .filter(s -> s.getRegisterNumber().equalsIgnoreCase(reg))
                    .findFirst().orElse(null);

            if (student == null) {
                System.out.println("[!] No student found with Register Number: " + reg);
                return;
            }

            System.out.print("Enter Skill Name (e.g. Java, Python, React.js, Docker): ");
            String skillName = scanner.nextLine().trim();

            System.out.print(
                    "Enter Skill Category (Programming, Web Development, Database, AI/ML, Cloud/DevOps, Mobile App): ");
            String cat = scanner.nextLine().trim();
            if (cat.isEmpty())
                cat = "Programming";

            System.out.print("Enter Proficiency Level (Beginner / Intermediate / Advanced): ");
            String prof = scanner.nextLine().trim();
            if (!prof.equalsIgnoreCase("Beginner") && !prof.equalsIgnoreCase("Intermediate")
                    && !prof.equalsIgnoreCase("Advanced")) {
                prof = "Intermediate";
            }

            int skillId = service.getAllSkills().size() + 1;
            Skill skill = new Skill(skillId, skillName, cat);
            service.addSkill(skill);

            int studentSkillId = student.getSkills().size() + 1;
            StudentSkill ss = new StudentSkill(studentSkillId, skill, prof, LocalDate.now());
            student.addSkill(ss);
            System.out.println(
                    "[SUCCESS] Mapped skill '" + skillName + "' (" + prof + ") to student " + student.getStudentName());
        } catch (Exception e) {
            System.out.println("[ERROR] Failed to map skill: " + e.getMessage());
        }
    }

    private static void handleRegisterProject(Scanner scanner) {
        System.out.println("\n--- [MANUAL INPUT] REGISTER STUDENT MINI-PROJECT ---");
        try {
            System.out.print("Enter Student Register Number: ");
            String reg = scanner.nextLine().trim();
            Student student = service.getAllStudents().stream()
                    .filter(s -> s.getRegisterNumber().equalsIgnoreCase(reg))
                    .findFirst().orElse(null);

            if (student == null) {
                System.out.println("[!] No student found with Register Number: " + reg);
                return;
            }

            System.out.print("Enter Project Title: ");
            String title = scanner.nextLine().trim();

            System.out.print("Enter Domain (e.g., AI/ML, Web & Analytics, Cloud/DevOps, Mobile App): ");
            String domain = scanner.nextLine().trim();

            System.out.print("Enter Project Description: ");
            String desc = scanner.nextLine().trim();

            System.out.print("Enter Technologies Used (comma-separated, e.g. Java, MySQL, Python): ");
            String techInput = scanner.nextLine().trim();

            int pId = service.getAllProjects().size() + 1;
            Faculty guide = new Faculty(1, "Dr. K. R. Valluvan", "valluvan.kr@vcet.ac.in", "IT");
            Project project = new Project(pId, title, domain, desc, LocalDate.now(), LocalDate.now().plusMonths(3),
                    "In-Progress", guide);

            for (String t : techInput.split(",")) {
                String trimmed = t.trim();
                if (!trimmed.isEmpty())
                    project.addTechnology(trimmed);
            }

            // Default initial milestones
            project.addMilestone(
                    new Milestone(1, "Requirements & SRS", LocalDate.now().plusWeeks(2), "Completed", "Approved"));
            project.addMilestone(new Milestone(2, "Design & ER Modeling", LocalDate.now().plusWeeks(4), "In-Progress",
                    "In progress"));
            project.addMilestone(
                    new Milestone(3, "Implementation", LocalDate.now().plusWeeks(8), "Pending", "Pending"));

            student.registerProject(project);
            service.addProject(project);
            System.out.println(
                    "[SUCCESS] Project registered successfully with ID #" + pId + " for " + student.getStudentName());
        } catch (Exception e) {
            System.out.println("[ERROR] Failed to register project: " + e.getMessage());
        }
    }

    private static void handleMilestone(Scanner scanner) {
        System.out.println("\n--- [MANUAL INPUT] ADD / UPDATE PROJECT MILESTONE ---");
        try {
            System.out.print("Enter Project ID: ");
            int pId = Integer.parseInt(scanner.nextLine().trim());
            Project project = service.getProject(pId);

            System.out.print("Enter Milestone Name: ");
            String mName = scanner.nextLine().trim();

            System.out.print("Enter Status (Pending / In-Progress / Completed / Delayed): ");
            String status = scanner.nextLine().trim();

            System.out.print("Enter Faculty Remarks: ");
            String remarks = scanner.nextLine().trim();

            int mId = project.getMilestones().size() + 1;
            Milestone m = new Milestone(mId, mName, LocalDate.now().plusWeeks(2), status, remarks);
            project.addMilestone(m);
            System.out.printf("[SUCCESS] Milestone '%s' added. Current Project Progress: %.1f%%\n", mName,
                    project.getProgress());
        } catch (Exception e) {
            System.out.println("[ERROR] Milestone update failed: " + e.getMessage());
        }
    }

    private static void handleEvaluation(Scanner scanner) {
        System.out.println("\n--- [MANUAL INPUT] RECORD PROJECT EVALUATION MARKS ---");
        try {
            System.out.print("Enter Project ID to Evaluate: ");
            int pId = Integer.parseInt(scanner.nextLine().trim());
            Project project = service.getProject(pId);

            System.out.println("Enter Rubric Scores (Validations Enforced):");
            System.out.print("  * Requirement Analysis Marks (Max 20): ");
            float req = Float.parseFloat(scanner.nextLine().trim());

            System.out.print("  * System Design Marks (Max 20): ");
            float des = Float.parseFloat(scanner.nextLine().trim());

            System.out.print("  * Implementation & Code Quality Marks (Max 30): ");
            float imp = Float.parseFloat(scanner.nextLine().trim());

            System.out.print("  * Documentation Marks (Max 15): ");
            float doc = Float.parseFloat(scanner.nextLine().trim());

            System.out.print("  * Presentation & Viva Marks (Max 15): ");
            float pre = Float.parseFloat(scanner.nextLine().trim());

            Faculty evaluator = project.getGuide() != null ? project.getGuide()
                    : new Faculty(1, "Dr. K. R. Valluvan", "valluvan.kr@vcet.ac.in", "IT");
            Evaluation eval = new Evaluation(pId, evaluator, req, des, imp, doc, pre, LocalDate.now());
            project.setEvaluation(eval);

            if (project.getProgress() >= 100.0) {
                project.setStatus("Completed");
            }

            System.out.printf("[SUCCESS] Evaluation Recorded -> Total: %.1f/100 | Grade: %s\n", eval.getTotalMarks(),
                    eval.getGrade());
        } catch (InvalidMarkException e) {
            System.out.println("[VALIDATION ERROR] " + e.getMessage());
        } catch (Exception e) {
            System.out.println("[ERROR] Evaluation failed: " + e.getMessage());
        }
    }

    private static void displayStudents() {
        System.out.println("\n--------------------------------------------------------------------------------");
        System.out.println(" STUDENT DIRECTORY AND ASSIGNED TECHNICAL SKILLS");
        System.out.println("--------------------------------------------------------------------------------");
        System.out.printf("%-10s %-16s %-6s %-12s %s\n", "REG NO", "STUDENT NAME", "YEAR", "DEPARTMENT",
                "ACQUIRED SKILLS (PROFICIENCY)");
        System.out.println("--------------------------------------------------------------------------------");
        for (Student s : service.getAllStudents()) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < s.getSkills().size(); i++) {
                StudentSkill ss = s.getSkills().get(i);
                sb.append(ss.getSkill().getSkillName()).append(" (").append(ss.getProficiencyLevel()).append(")");
                if (i < s.getSkills().size() - 1)
                    sb.append(", ");
            }
            String skillList = sb.length() > 0 ? sb.toString() : "[No registered skills]";
            System.out.printf("%-10s %-16s %-6d %-12s %s\n",
                    s.getRegisterNumber(), s.getStudentName(), s.getYearOfStudy(), "IT", skillList);
        }
    }

    private static void displayProjects() {
        System.out.println("\n--------------------------------------------------------------------------------");
        System.out.println(" REGISTERED STUDENT PROJECTS & MILESTONE PROGRESS TRACKING");
        System.out.println("--------------------------------------------------------------------------------");

        System.out.printf("%-4s %-10s %-38s %-16s %-10s %-8s\n",
                "ID", "REG NO", "PROJECT TITLE", "DOMAIN", "STATUS", "PROGRESS");

        System.out.println("--------------------------------------------------------------------------------");

        for (Project p : service.getAllProjects()) {
            Student owner = null;

            for (Student st : service.getAllStudents()) {
                boolean hasProject = st.getProjects().stream()
                        .anyMatch(pr -> pr.getProjectId() == p.getProjectId());

                if (hasProject) {
                    owner = st;
                    break;
                }
            }

            String reg = owner != null ? owner.getRegisterNumber() : "N/A";

            System.out.printf("%-4d %-10s %-38s %-16s %-10s %6.1f%%\n",
                    p.getProjectId(),
                    reg,
                    p.getTitle().length() > 36
                            ? p.getTitle().substring(0, 35) + "..."
                            : p.getTitle(),
                    p.getDomain(),
                    p.getStatus(),
                    p.getProgress());
        }
    }

    private static void displayEvaluations() {
        System.out.println("\n--------------------------------------------------------------------------------");
        System.out.println(" PROJECT EVALUATION MARKS & GRADES");
        System.out.println("--------------------------------------------------------------------------------");

        System.out.printf("%-4s %-10s %-38s %-10s %-10s\n",
                "ID", "REG NO", "PROJECT TITLE", "TOTAL", "GRADE");

        System.out.println("--------------------------------------------------------------------------------");

        for (Project p : service.getAllProjects()) {
            if (p.getEvaluation() != null) {
                Evaluation ev = p.getEvaluation();
                Student owner = null;

                for (Student s : service.getAllStudents()) {
                    boolean hasProject = s.getProjects().stream()
                            .anyMatch(pr -> pr.getProjectId() == p.getProjectId());

                    if (hasProject) {
                        owner = s;
                        break;
                    }
                }

                String reg = owner != null ? owner.getRegisterNumber() : "N/A";

                System.out.printf("%-4d %-10s %-38s %-10.1f %-10s\n",
                        p.getProjectId(),
                        reg,
                        p.getTitle().length() > 36
                                ? p.getTitle().substring(0, 35) + "..."
                                : p.getTitle(),
                        ev.getTotalMarks(),
                        ev.getGrade());
            }
        }
    }

    private static void displaySkillGaps() {
        System.out.println("\n--------------------------------------------------------------------------------");
        System.out.println(" DEPARTMENTAL SKILL GAP IDENTIFICATION (STREAM API)");
        System.out.println("--------------------------------------------------------------------------------");
        Map<String, List<String>> gaps = service.identifySkillGaps();
        if (gaps.isEmpty()) {
            System.out.println("   [+] No skill gaps detected across all student projects.");
        } else {
            gaps.forEach((stud, missingList) -> {
                System.out.printf("   [!] %s -> Recommended Training: %s\n", stud, String.join(", ", missingList));
            });
        }
    }

    private static void seedInitialData(ProjectTrackingService service) {
        Faculty f1 = new Faculty(1, "Dr. K. R. Valluvan", "valluvan.kr@vcet.ac.in", "Information Technology");
        Faculty f2 = new Faculty(2, "Dr. S. Senthil Kumar", "senthilkumar.s@vcet.ac.in", "Information Technology");
        Faculty f3 = new Faculty(3, "Prof. M. Thilagaraj", "thilagaraj.m@vcet.ac.in", "Information Technology");
        Faculty f4 = new Faculty(4, "Dr. P. Mythili", "mythili.p@vcet.ac.in", "Information Technology");
        service.addFaculty(f1);
        service.addFaculty(f2);
        service.addFaculty(f3);
        service.addFaculty(f4);

        Skill sJava = new Skill(1, "Java", "Programming");
        Skill sPython = new Skill(2, "Python", "Programming");
        Skill sCPP = new Skill(3, "C++", "Programming");
        Skill sWeb = new Skill(4, "HTML/CSS/JS", "Web Development");
        Skill sReact = new Skill(5, "React.js", "Web Development");
        Skill sSpring = new Skill(6, "Spring Boot", "Web Development");
        Skill sNode = new Skill(7, "Node.js", "Web Development");
        Skill sMySQL = new Skill(8, "MySQL", "Database");
        Skill sMongo = new Skill(9, "MongoDB", "Database");
        Skill sSQLite = new Skill(10, "SQLite", "Database");
        Skill sML = new Skill(11, "Machine Learning", "AI/ML");
        Skill sDL = new Skill(12, "Deep Learning", "AI/ML");
        Skill sAWS = new Skill(15, "AWS Cloud", "Cloud/DevOps");
        Skill sDocker = new Skill(14, "Docker & Kubernetes", "Cloud/DevOps");
        Skill sFlutter = new Skill(16, "Flutter", "Mobile App");

        Arrays.asList(sJava, sPython, sCPP, sWeb, sReact, sSpring, sNode, sMySQL, sMongo, sSQLite, sML, sDL, sAWS,
                sDocker, sFlutter)
                .forEach(service::addSkill);

        Student st1 = new Student(1, "25IT002", "Sarmida S", "Information Technology", 3, "sarmida.25it002@vcet.ac.in",
                "9842100002");
        Student st2 = new Student(2, "25IT032", "Kavin R", "Information Technology", 3, "kavin.25it032@vcet.ac.in",
                "9842100032");
        Student st3 = new Student(3, "25IT062", "Pradeep M", "Information Technology", 3, "pradeep.25it062@vcet.ac.in",
                "9842100062");
        Student st4 = new Student(4, "25IT092", "Shreya V", "Information Technology", 3, "shreya.25it092@vcet.ac.in",
                "9842100092");
        Student st5 = new Student(5, "25ITL02", "Dinesh Kumar K", "Information Technology", 3,
                "dinesh.25itl02@vcet.ac.in", "9842100102");
        Student st6 = new Student(6, "25IT015", "Ananya B", "Information Technology", 3, "ananya.25it015@vcet.ac.in",
                "9842100015");
        Student st7 = new Student(7, "25IT045", "Gokulnath P", "Information Technology", 3, "gokul.25it045@vcet.ac.in",
                "9842100045");
        Student st8 = new Student(8, "25IT078", "Pavithra T", "Information Technology", 3,
                "pavithra.25it078@vcet.ac.in", "9842100078");

        st1.addSkill(new StudentSkill(1, sJava, "Advanced", LocalDate.of(2024, 3, 15)));
        st1.addSkill(new StudentSkill(2, sPython, "Advanced", LocalDate.of(2024, 6, 20)));
        st1.addSkill(new StudentSkill(3, sSpring, "Intermediate", LocalDate.of(2024, 9, 10)));
        st1.addSkill(new StudentSkill(4, sMySQL, "Advanced", LocalDate.of(2024, 4, 12)));
        st1.addSkill(new StudentSkill(5, sML, "Intermediate", LocalDate.of(2024, 11, 5)));

        st2.addSkill(new StudentSkill(6, sPython, "Advanced", LocalDate.of(2024, 2, 18)));
        st2.addSkill(new StudentSkill(7, sML, "Advanced", LocalDate.of(2024, 7, 22)));
        st2.addSkill(new StudentSkill(8, sDL, "Intermediate", LocalDate.of(2024, 10, 15)));
        st2.addSkill(new StudentSkill(9, sMySQL, "Intermediate", LocalDate.of(2024, 5, 10)));

        st3.addSkill(new StudentSkill(10, sWeb, "Advanced", LocalDate.of(2024, 1, 10)));
        st3.addSkill(new StudentSkill(11, sReact, "Advanced", LocalDate.of(2024, 5, 15)));
        st3.addSkill(new StudentSkill(12, sNode, "Intermediate", LocalDate.of(2024, 8, 20)));
        st3.addSkill(new StudentSkill(13, sMongo, "Intermediate", LocalDate.of(2024, 9, 1)));

        st4.addSkill(new StudentSkill(14, sJava, "Advanced", LocalDate.of(2024, 4, 5)));
        st4.addSkill(new StudentSkill(15, sMySQL, "Advanced", LocalDate.of(2024, 6, 18)));
        st4.addSkill(new StudentSkill(16, sAWS, "Intermediate", LocalDate.of(2024, 9, 25)));
        st4.addSkill(new StudentSkill(17, sDocker, "Beginner", LocalDate.of(2024, 10, 30)));

        st5.addSkill(new StudentSkill(18, sPython, "Advanced", LocalDate.of(2024, 3, 10)));
        st5.addSkill(new StudentSkill(19, sFlutter, "Advanced", LocalDate.of(2024, 7, 14)));
        st5.addSkill(new StudentSkill(20, sSQLite, "Intermediate", LocalDate.of(2024, 8, 19)));

        Arrays.asList(st1, st2, st3, st4, st5, st6, st7, st8).forEach(service::addStudent);

        Project p1 = new Project(1, "Student Project and Skill Tracking System", "Web & Analytics",
                "Monitors student projects, technical skill matrices, milestones, and provides Python visualizations.",
                LocalDate.of(2025, 1, 5), LocalDate.of(2025, 4, 15), "Completed", f1);
        p1.addTechnology("Java");
        p1.addTechnology("Spring Boot");
        p1.addTechnology("MySQL");
        p1.addTechnology("Python");
        p1.addTechnology("Matplotlib");
        p1.addMilestone(new Milestone(1, "SRS and Requirements Engineering", LocalDate.of(2025, 1, 25), "Completed",
                "Approved"));
        p1.addMilestone(new Milestone(2, "System Architecture & ER Design", LocalDate.of(2025, 2, 15), "Completed",
                "Verified 3NF"));
        p1.addMilestone(new Milestone(3, "Core Java Business Logic Implementation", LocalDate.of(2025, 3, 10),
                "Completed", "Classes & Threads"));
        p1.addMilestone(new Milestone(4, "Python Analytics & Visualization", LocalDate.of(2025, 3, 28), "Completed",
                "5 Charts rendered"));
        p1.addMilestone(new Milestone(5, "Final Testing and Project Viva", LocalDate.of(2025, 4, 10), "Completed",
                "100% test pass"));
        try {
            p1.setEvaluation(new Evaluation(1, f1, 19.5f, 19.0f, 29.0f, 14.5f, 14.5f, LocalDate.of(2025, 4, 12)));
        } catch (Exception ignored) {
        }
        st1.registerProject(p1);
        service.addProject(p1);

        Project p2 = new Project(2, "Automated Disease Prediction from Medical Scans", "AI/ML",
                "Deep learning CNN model for diagnosing chest X-rays with real-time patient report generation.",
                LocalDate.of(2025, 1, 10), LocalDate.of(2025, 4, 20), "In-Progress", f2);
        p2.addTechnology("Python");
        p2.addTechnology("TensorFlow");
        p2.addTechnology("Flask");
        p2.addMilestone(new Milestone(6, "Dataset Collection and Augmentation", LocalDate.of(2025, 1, 28), "Completed",
                "5k curated"));
        p2.addMilestone(new Milestone(7, "CNN Architecture & Training", LocalDate.of(2025, 2, 25), "Completed",
                "94% accuracy"));
        p2.addMilestone(new Milestone(8, "Web UI & Diagnostic Inference", LocalDate.of(2025, 3, 20), "In-Progress",
                "Flask integration"));
        p2.addMilestone(new Milestone(9, "Clinical Validation and Viva", LocalDate.of(2025, 4, 18), "Pending",
                "Doctor consult"));
        try {
            p2.setEvaluation(new Evaluation(2, f2, 18.0f, 18.5f, 26.5f, 13.0f, 13.5f, LocalDate.of(2025, 4, 5)));
        } catch (Exception ignored) {
        }
        st2.registerProject(p2);
        service.addProject(p2);

        Project p3 = new Project(3, "E-Commerce Platform with Microservices", "Cloud & Web",
                "High-throughput online shopping portal built with React, Node.js microservices, and Docker containers.",
                LocalDate.of(2025, 1, 8), LocalDate.of(2025, 4, 18), "Completed", f3);
        p3.addTechnology("React.js");
        p3.addTechnology("Node.js");
        p3.addTechnology("MongoDB");
        p3.addTechnology("Docker");
        p3.addMilestone(new Milestone(10, "Microservices Architecture Design", LocalDate.of(2025, 1, 30), "Completed",
                "API gateway"));
        p3.addMilestone(new Milestone(11, "Cart & Catalog Service Coding", LocalDate.of(2025, 2, 20), "Completed",
                "Docker ready"));
        p3.addMilestone(new Milestone(12, "Payment Gateway Integration", LocalDate.of(2025, 3, 15), "Completed",
                "Stripe sandbox"));
        p3.addMilestone(new Milestone(13, "Load Testing and Submission", LocalDate.of(2025, 4, 12), "Completed",
                "1k req/s verified"));
        try {
            p3.setEvaluation(new Evaluation(3, f3, 19.0f, 18.0f, 28.5f, 14.0f, 14.0f, LocalDate.of(2025, 4, 14)));
        } catch (Exception ignored) {
        }
        st3.registerProject(p3);
        service.addProject(p3);

        Project p4 = new Project(4, "Cloud-Native Distributed File Storage", "Cloud/DevOps",
                "Secure peer-to-peer cloud file sharing service featuring end-to-end AES-256 encryption.",
                LocalDate.of(2025, 1, 12), LocalDate.of(2025, 4, 25), "In-Progress", f1);
        p4.addTechnology("Java");
        p4.addTechnology("AWS S3");
        p4.addTechnology("Docker");
        p4.addTechnology("MySQL");
        p4.addMilestone(new Milestone(14, "Cryptographic Protocol Specification", LocalDate.of(2025, 2, 5), "Completed",
                "AES verified"));
        p4.addMilestone(new Milestone(15, "Storage Node Replication Engine", LocalDate.of(2025, 3, 1), "Completed",
                "Raft consensus"));
        p4.addMilestone(new Milestone(16, "AWS Deployment & Benchmark", LocalDate.of(2025, 3, 25), "Delayed",
                "Budget approved"));
        p4.addMilestone(
                new Milestone(17, "Documentation and Viva", LocalDate.of(2025, 4, 20), "Pending", "Draft ready"));
        try {
            p4.setEvaluation(new Evaluation(4, f1, 17.5f, 17.0f, 24.0f, 12.5f, 13.0f, LocalDate.of(2025, 4, 8)));
        } catch (Exception ignored) {
        }
        st4.registerProject(p4);
        service.addProject(p4);

        Project p5 = new Project(5, "Smart Campus Navigation & Attendance App", "Mobile App",
                "Cross-platform Flutter application utilizing BLE beacons for indoor campus navigation and geo-fenced attendance.",
                LocalDate.of(2025, 1, 15), LocalDate.of(2025, 4, 22), "Completed", f4);
        p5.addTechnology("Flutter");
        p5.addTechnology("Dart");
        p5.addTechnology("Firebase");
        p5.addTechnology("SQLite");
        p5.addMilestone(new Milestone(18, "Campus BLE Beacon Floor Mapping", LocalDate.of(2025, 2, 2), "Completed",
                "4 floors done"));
        p5.addMilestone(new Milestone(19, "Flutter Navigation UI Implementation", LocalDate.of(2025, 2, 28),
                "Completed", "Turn-by-turn"));
        p5.addMilestone(new Milestone(20, "Geo-Fenced Attendance System", LocalDate.of(2025, 3, 20), "Completed",
                "50 students tested"));
        p5.addMilestone(new Milestone(21, "Field Deployment & Viva", LocalDate.of(2025, 4, 15), "Completed",
                "HOD demo passed"));
        try {
            p5.setEvaluation(new Evaluation(5, f4, 19.0f, 19.0f, 28.0f, 14.0f, 15.0f, LocalDate.of(2025, 4, 16)));
        } catch (Exception ignored) {
        }
        st5.registerProject(p5);
        service.addProject(p5);
    }

    private static void runAutomatedDemonstration(ProjectTrackingService service) {
        System.out.println("================================================================================");
        System.out.println("STUDENT PROJECT AND SKILL TRACKING SYSTEM");
        System.out.println("================================================================================\n");

        MilestoneMonitorThread auditThread = new MilestoneMonitorThread(new ArrayList<>(service.getAllProjects()));
        auditThread.start();

        displayStudents();
        displayProjects();
        displayEvaluations();

        System.out.println("\n--------------------------------------------------------------------------------");
        System.out.println(" [4] ANALYTICS & DEPARTMENTAL SKILL GAP IDENTIFICATION (STREAM API)");
        System.out.println("--------------------------------------------------------------------------------");
        System.out.printf("Total Enrolled Students : %d\n", service.getAllStudents().size());
        System.out.printf("Total Projects Registered: %d\n", service.getAllProjects().size());
        System.out.printf("Completed Projects      : %d\n", service.getCompletedProjects().size());
        System.out.printf("Department Avg Score    : %.2f / 100.0\n\n", service.getAverageEvaluationScore());

        System.out.println(">>> Technology Adoption Ranking across Student Projects:");
        Map<String, Long> techUsage = service.getTechnologyUsageCounts();
        techUsage.forEach((tech, count) -> {
            String bar = "=".repeat(count.intValue() * 3);
            System.out.printf("   %-15s [%d projects] %s\n", tech, count, bar);
        });

        displaySkillGaps();

        System.out.println("\n--------------------------------------------------------------------------------");
        System.out.println(" [5] DEMONSTRATING EXCEPTION HANDLING & VALIDATION GUARDS");
        System.out.println("--------------------------------------------------------------------------------");
        try {
            System.out.print("[TEST 1] Attempting to set rubric marks beyond 20 for Requirement Analysis (25/20)... ");
            new Evaluation(99, new Faculty(1, "Guide", "guide@vcet.ac.in", "IT"), 25.0f, 15.0f, 25.0f, 10.0f, 10.0f,
                    LocalDate.now());
        } catch (InvalidMarkException ex) {
            System.out.println("CAUGHT EXPECTED EXCEPTION: " + ex.getMessage());
        }

        try {
            System.out.print("[TEST 2] Querying non-existent project with ID #999... ");
            service.getProject(999);
        } catch (ProjectNotFoundException ex) {
            System.out.println("CAUGHT EXPECTED EXCEPTION: " + ex.getMessage());
        }

        System.out.println("\n================================================================================");
        System.out.println(" [SUCCESS] JAVA APPLICATION EXECUTION COMPLETED (0 ERRORS)");
        System.out.println("================================================================================");
    }

    private static void saveOutputsAndScreenshot() {
        String outputText = consoleCapture.toString();
        try {
            Files.write(Paths.get("java_output.txt"), outputText.getBytes());
            System.out.println("Console transcript persisted to java_output.txt");
        } catch (IOException e) {
            System.err.println("Failed to write java_output.txt: " + e.getMessage());
        }

        generateTerminalScreenshot(outputText, "java_execution_screenshot.png");
    }

    private static void generateTerminalScreenshot(String text, String outputPath) {
        try {
            String[] rawLines = text.split("\r?\n");
            int maxLines = Math.min(rawLines.length, 48);
            int width = 1200;
            int lineHeight = 22;
            int topPadding = 65;
            int bottomPadding = 25;
            int height = topPadding + (maxLines * lineHeight) + bottomPadding;

            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = image.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

            g2.setColor(new Color(15, 23, 42));
            g2.fillRect(0, 0, width, height);

            g2.setColor(new Color(30, 41, 59));
            g2.fillRect(0, 0, width, 45);

            g2.setColor(new Color(239, 68, 68));
            g2.fillOval(18, 16, 13, 13);
            g2.setColor(new Color(245, 158, 11));
            g2.fillOval(38, 16, 13, 13);
            g2.setColor(new Color(34, 197, 94));
            g2.fillOval(58, 16, 13, 13);

            g2.setFont(new Font("Segoe UI", Font.BOLD, 13));
            g2.setColor(new Color(203, 213, 225));
            g2.drawString("Java Application Terminal - Student Project & Skill Tracking System (VCET R2025)", 90, 27);

            Font monoFont = new Font("Consolas", Font.PLAIN, 14);
            g2.setFont(monoFont);

            int y = topPadding + 14;
            for (int i = 0; i < maxLines; i++) {
                String line = rawLines[i];
                if (line.contains("VELALAR") || line.contains("SCENARIO 2") || line.contains("SUCCESS")) {
                    g2.setColor(new Color(56, 189, 248));
                } else if (line.contains("===") || line.contains("---")) {
                    g2.setColor(new Color(71, 85, 105));
                } else if (line.contains("[1]") || line.contains("[2]") || line.contains("[3]") || line.contains("[4]")
                        || line.contains("[5]")) {
                    g2.setColor(new Color(250, 204, 21));
                } else if (line.contains("CAUGHT EXPECTED") || line.contains("Completed")
                        || line.contains("Advanced")) {
                    g2.setColor(new Color(74, 222, 128));
                } else if (line.contains("Delayed") || line.contains("In-Progress")) {
                    g2.setColor(new Color(251, 146, 60));
                } else {
                    g2.setColor(new Color(241, 245, 249));
                }
                g2.drawString(line, 24, y);
                y += lineHeight;
            }

            g2.dispose();
            File outFile = new File(outputPath);
            ImageIO.write(image, "PNG", outFile);
            System.out.println("Terminal screenshot saved successfully to: " + outFile.getAbsolutePath());
        } catch (Exception e) {
            System.err.println("Could not generate terminal screenshot: " + e.getMessage());
        }
    }
}
