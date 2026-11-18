-- ============================================================================
-- SAMPLE DATA INSERTION: Student Project and Skill Tracking System
-- Scenario 2 | Target Register Numbers: 2, 32, 62, 92, L2
-- ============================================================================

-- 1. Insert Faculty Guides
INSERT INTO Faculty (faculty_id, faculty_name, email, department) VALUES
(1, 'Dr. K. R. Valluvan', 'valluvan.kr@vcet.ac.in', 'Information Technology'),
(2, 'Dr. S. Senthil Kumar', 'senthilkumar.s@vcet.ac.in', 'Information Technology'),
(3, 'Prof. M. Thilagaraj', 'thilagaraj.m@vcet.ac.in', 'Information Technology'),
(4, 'Dr. P. Mythili', 'mythili.p@vcet.ac.in', 'Information Technology');

-- 2. Insert Students (Including Register Numbers 2, 32, 62, 92, L2)
INSERT INTO Student (student_id, register_number, student_name, department, year_of_study, email, phone) VALUES
(1, '25IT002', 'Sarmida S', 'Information Technology', 3, 'sarmida.25it002@vcet.ac.in', '9842100002'),
(2, '25IT032', 'Kavin R', 'Information Technology', 3, 'kavin.25it032@vcet.ac.in', '9842100032'),
(3, '25IT062', 'Pradeep M', 'Information Technology', 3, 'pradeep.25it062@vcet.ac.in', '9842100062'),
(4, '25IT092', 'Shreya V', 'Information Technology', 3, 'shreya.25it092@vcet.ac.in', '9842100092'),
(5, '25ITL02', 'Dinesh Kumar K', 'Information Technology', 3, 'dinesh.25itl02@vcet.ac.in', '9842100102'),
(6, '25IT015', 'Ananya B', 'Information Technology', 3, 'ananya.25it015@vcet.ac.in', '9842100015'),
(7, '25IT045', 'Gokulnath P', 'Information Technology', 3, 'gokul.25it045@vcet.ac.in', '9842100045'),
(8, '25IT078', 'Pavithra T', 'Information Technology', 3, 'pavithra.25it078@vcet.ac.in', '9842100078');

-- 3. Insert Skills Catalog
INSERT INTO Skill (skill_id, skill_name, skill_category) VALUES
(1, 'Java', 'Programming'),
(2, 'Python', 'Programming'),
(3, 'C++', 'Programming'),
(4, 'HTML/CSS/JS', 'Web Development'),
(5, 'React.js', 'Web Development'),
(6, 'Spring Boot', 'Web Development'),
(7, 'Node.js', 'Web Development'),
(8, 'MySQL', 'Database'),
(9, 'MongoDB', 'Database'),
(10, 'SQLite', 'Database'),
(11, 'Machine Learning', 'AI/ML'),
(12, 'Deep Learning', 'AI/ML'),
(13, 'Natural Language Processing', 'AI/ML'),
(14, 'Docker & Kubernetes', 'Cloud/DevOps'),
(15, 'AWS Cloud', 'Cloud/DevOps'),
(16, 'Flutter', 'Mobile App'),
(17, 'Network Security', 'Cybersecurity');

-- 4. Insert StudentSkill Mappings (Proficiency Levels: Beginner, Intermediate, Advanced)
INSERT INTO StudentSkill (student_id, skill_id, proficiency_level, acquired_date) VALUES
-- Sarmida S (Reg: 2)
(1, 1, 'Advanced', '2024-03-15'),
(1, 2, 'Advanced', '2024-06-20'),
(1, 6, 'Intermediate', '2024-09-10'),
(1, 8, 'Advanced', '2024-04-12'),
(1, 11, 'Intermediate', '2024-11-05'),

-- Kavin R (Reg: 32)
(2, 2, 'Advanced', '2024-02-18'),
(2, 11, 'Advanced', '2024-07-22'),
(2, 12, 'Intermediate', '2024-10-15'),
(2, 8, 'Intermediate', '2024-05-10'),
(2, 4, 'Beginner', '2024-08-01'),

-- Pradeep M (Reg: 62)
(3, 4, 'Advanced', '2024-01-10'),
(3, 5, 'Advanced', '2024-05-15'),
(3, 7, 'Intermediate', '2024-08-20'),
(3, 9, 'Intermediate', '2024-09-01'),
(3, 1, 'Beginner', '2024-11-12'),

-- Shreya V (Reg: 92)
(4, 1, 'Advanced', '2024-04-05'),
(4, 8, 'Advanced', '2024-06-18'),
(4, 15, 'Intermediate', '2024-09-25'),
(4, 14, 'Beginner', '2024-10-30'),
(4, 2, 'Intermediate', '2024-12-01'),

-- Dinesh Kumar K (Reg: L2)
(5, 2, 'Advanced', '2024-03-10'),
(5, 16, 'Advanced', '2024-07-14'),
(5, 10, 'Intermediate', '2024-08-19'),
(5, 7, 'Beginner', '2024-11-20'),

-- Ananya B (Reg: 15)
(6, 1, 'Intermediate', '2024-05-12'),
(6, 4, 'Advanced', '2024-07-10'),
(6, 8, 'Intermediate', '2024-09-15'),

-- Gokulnath P (Reg: 45)
(7, 2, 'Intermediate', '2024-06-01'),
(7, 13, 'Intermediate', '2024-09-10'),
(7, 3, 'Beginner', '2024-11-01'),

-- Pavithra T (Reg: 78)
(8, 1, 'Advanced', '2024-03-20'),
(8, 6, 'Advanced', '2024-08-15'),
(8, 15, 'Intermediate', '2024-10-10');

-- 5. Insert Student Projects
INSERT INTO Project (project_id, student_id, faculty_id, project_title, domain, description, start_date, end_date, status) VALUES
(1, 1, 1, 'Student Project and Skill Tracking System', 'Web & Analytics', 'A full-stack tracking system to monitor student mini-projects, skill matrices, milestone completions, and generate Python visual insights.', '2025-01-05', '2025-04-15', 'Completed'),
(2, 2, 2, 'Automated Disease Prediction from Medical Scans', 'AI/ML', 'Deep learning CNN model for diagnosing chest X-rays with real-time patient report generation.', '2025-01-10', '2025-04-20', 'In-Progress'),
(3, 3, 3, 'E-Commerce Platform with Microservices', 'Cloud & Web', 'High-throughput online shopping portal built with React, Node.js microservices, and Docker containers.', '2025-01-08', '2025-04-18', 'Completed'),
(4, 4, 1, 'Cloud-Native Distributed File Storage', 'Cloud/DevOps', 'Secure peer-to-peer cloud file sharing service featuring end-to-end AES-256 encryption.', '2025-01-12', '2025-04-25', 'In-Progress'),
(5, 5, 4, 'Smart Campus Navigation & Attendance App', 'Mobile App', 'Cross-platform Flutter application utilizing BLE beacons for indoor campus navigation and geo-fenced attendance.', '2025-01-15', '2025-04-22', 'Completed'),
(6, 6, 2, 'Online Examination and Proctoring Portal', 'Web Development', 'Automated web portal with computer vision-based student gaze tracking during online examinations.', '2025-01-18', '2025-04-30', 'Proposed'),
(7, 7, 3, 'Voice-Driven Conversational AI Chatbot', 'AI/ML', 'Fine-tuned LLM and Speech-to-Text assistant tailored for university campus inquiry handling.', '2025-01-15', '2025-04-28', 'In-Progress'),
(8, 8, 4, 'IoT-Based Agriculture Soil Health Monitor', 'IoT & Embedded', 'Sensors connected to Raspberry Pi collecting nitrogen, phosphorus, and moisture readings sent to cloud dashboard.', '2025-01-20', '2025-05-05', 'Proposed');

-- 6. Insert Project Technologies
INSERT INTO ProjectTechnology (project_id, technology_name) VALUES
(1, 'Java'), (1, 'Spring Boot'), (1, 'MySQL'), (1, 'Python'), (1, 'Matplotlib'),
(2, 'Python'), (2, 'TensorFlow'), (2, 'OpenCV'), (2, 'Flask'),
(3, 'React.js'), (3, 'Node.js'), (3, 'MongoDB'), (3, 'Docker'),
(4, 'Java'), (4, 'AWS S3'), (4, 'Docker'), (4, 'MySQL'),
(5, 'Flutter'), (5, 'Dart'), (5, 'Firebase'), (5, 'SQLite'),
(6, 'React.js'), (6, 'Node.js'), (6, 'MySQL'), (6, 'Python'),
(7, 'Python'), (7, 'PyTorch'), (7, 'HuggingFace'), (7, 'FastAPI'),
(8, 'Python'), (8, 'Raspberry Pi'), (8, 'MQTT'), (8, 'MySQL');

-- 7. Insert Project Milestones
INSERT INTO Milestone (project_id, milestone_name, submission_date, completion_status, remarks) VALUES
-- Project 1 (Sarmida S - Completed)
(1, 'SRS and Requirements Engineering', '2025-01-25', 'Completed', 'Comprehensive SRS document approved by guide'),
(1, 'System Architecture & ER Design', '2025-02-15', 'Completed', '3NF Relational schema and UML diagrams verified'),
(1, 'Core Java Business Logic Implementation', '2025-03-10', 'Completed', 'OOP modules, multithreading, and DAO layer completed'),
(1, 'Python Analytics & Visualization', '2025-03-28', 'Completed', '5 Publication-quality charts generated from SQL records'),
(1, 'Final Testing and Project Viva', '2025-04-10', 'Completed', 'All test cases passed with 100% test coverage'),

-- Project 2 (Kavin R - In-Progress)
(2, 'Dataset Collection and Augmentation', '2025-01-28', 'Completed', '5,000 chest X-ray images curated and annotated'),
(2, 'CNN Architecture & Training', '2025-02-25', 'Completed', 'Model achieved 94.2% test accuracy'),
(2, 'Web UI & Diagnostic Inference Pipeline', '2025-03-20', 'In-Progress', 'Integrating Flask REST backend with React UI'),
(2, 'Clinical Validation and Viva', '2025-04-18', 'Pending', 'Awaiting doctor consultation feedback'),

-- Project 3 (Pradeep M - Completed)
(3, 'Microservices Architecture Design', '2025-01-30', 'Completed', 'API Gateway and authentication services scoped'),
(3, 'Cart and Catalog Service Coding', '2025-02-20', 'Completed', 'Docker containers deployed locally'),
(3, 'Payment Gateway Integration', '2025-03-15', 'Completed', 'Stripe sandbox payment flow verified'),
(3, 'Load Testing and Final Submission', '2025-04-12', 'Completed', 'Benchmark tested at 1,000 req/sec'),

-- Project 4 (Shreya V - In-Progress)
(4, 'Cryptographic Protocol Specification', '2025-02-05', 'Completed', 'AES-GCM encryption verified'),
(4, 'Storage Node Replication Engine', '2025-03-01', 'Completed', 'Raft consensus implemented'),
(4, 'AWS Deployment & Benchmark Testing', '2025-03-25', 'Delayed', 'Cloud budget approval delayed; in progress'),
(4, 'Documentation and Viva', '2025-04-20', 'Pending', 'Draft report submitted'),

-- Project 5 (Dinesh Kumar K - Completed)
(5, 'Campus BLE Beacon Floor Mapping', '2025-02-02', 'Completed', 'Mapped 4 floors of IT block'),
(5, 'Flutter Navigation UI Implementation', '2025-02-28', 'Completed', 'Turn-by-turn routing implemented'),
(5, 'Geo-Fenced Attendance System', '2025-03-20', 'Completed', 'BLE proximity detection tested with 50 students'),
(5, 'Field Deployment and Project Viva', '2025-04-15', 'Completed', 'Successfully demonstrated to department head'),

-- Project 6 (Ananya B - Proposed)
(6, 'Requirements and Gaze Detection Prototype', '2025-02-15', 'Pending', 'Initial literature survey conducted'),
(6, 'Exam Engine Development', '2025-03-20', 'Pending', 'Not started'),

-- Project 7 (Gokulnath P - In-Progress)
(7, 'Corpus Collection for University Q&A', '2025-02-10', 'Completed', 'Curated 1,200 departmental FAQs'),
(7, 'LLM LoRA Fine-Tuning', '2025-03-15', 'In-Progress', 'Training on Llama-3 8B parameter model'),
(7, 'Voice Interface & Evaluation', '2025-04-22', 'Pending', 'Whisper integration scheduled'),

-- Project 8 (Pavithra T - Proposed)
(8, 'Sensor Hardware Assembly', '2025-02-25', 'Pending', 'Sensors procured; assembly pending'),
(8, 'IoT Cloud Dashboard', '2025-03-30', 'Pending', 'Not started');

-- 8. Insert Evaluations (Rubric Marks out of 100)
-- Req: 20 | Design: 20 | Impl: 30 | Doc: 15 | Pres: 15
INSERT INTO Evaluation (project_id, faculty_id, requirement_analysis_marks, design_marks, implementation_marks, documentation_marks, presentation_marks, total_marks, average_marks, evaluation_date) VALUES
(1, 1, 19.5, 19.0, 29.0, 14.5, 14.5, 96.5, 96.5, '2025-04-12'),
(2, 2, 18.0, 18.5, 26.5, 13.0, 13.5, 89.5, 89.5, '2025-04-05'),
(3, 3, 19.0, 18.0, 28.5, 14.0, 14.0, 93.5, 93.5, '2025-04-14'),
(4, 1, 17.5, 17.0, 24.0, 12.5, 13.0, 84.0, 84.0, '2025-04-08'),
(5, 4, 19.0, 19.0, 28.0, 14.0, 15.0, 95.0, 95.0, '2025-04-16');
