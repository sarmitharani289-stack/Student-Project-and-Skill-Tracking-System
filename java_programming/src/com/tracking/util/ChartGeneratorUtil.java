package com.tracking.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Map;
import java.util.LinkedHashMap;

public class ChartGeneratorUtil {

    public static void main(String[] args) {
        String outputDir = "../foundations_of_data_science/visualizations";
        if (args.length > 0) outputDir = args[0];
        new File(outputDir).mkdirs();

        generateChart1(outputDir + "/viz1_project_status_completion_rate.png");
        generateChart2(outputDir + "/viz2_popular_technology_stacks.png");
        generateChart3(outputDir + "/viz3_skill_proficiency_distribution.png");
        generateChart4(outputDir + "/viz4_milestone_progression_status.png");
        generateChart5(outputDir + "/viz5_evaluation_marks_breakdown.png");
        System.out.println("[SUCCESS] All 5 data visualization charts generated in " + outputDir);
    }

    // Chart 1: Donut Chart - Project Completion Status
    private static void generateChart1(String path) {
        int w = 900, h = 600;
        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics();
        setupGraphics(g);

        // Background
        g.setColor(new Color(248, 250, 252));
        g.fillRect(0, 0, w, h);

        // Header Title
        drawHeader(g, "Project Completion Status & Distribution", 
                "VCET B.Tech IT - Mini-Project Progress Tracking (R2025)", w);

        // Donut Chart data: Completed (3, 37.5%), In-Progress (3, 37.5%), Proposed (2, 25.0%)
        String[] labels = {"Completed (3 Projects - 37.5%)", "In-Progress (3 Projects - 37.5%)", "Proposed (2 Projects - 25.0%)"};
        double[] values = {37.5, 37.5, 25.0};
        Color[] colors = {new Color(16, 185, 129), new Color(59, 130, 246), new Color(245, 158, 11)};

        int cx = 340, cy = 340, outerR = 190, innerR = 100;
        double startAngle = 90;

        for (int i = 0; i < values.length; i++) {
            double arc = (values[i] / 100.0) * 360.0;
            g.setColor(colors[i]);
            g.fill(new Arc2D.Double(cx - outerR, cy - outerR, outerR * 2, outerR * 2, startAngle, arc, Arc2D.PIE));
            startAngle += arc;
        }

        // Inner cutout for donut
        g.setColor(new Color(248, 250, 252));
        g.fill(new Ellipse2D.Double(cx - innerR, cy - innerR, innerR * 2, innerR * 2));

        // Center text
        g.setColor(new Color(15, 23, 42));
        g.setFont(new Font("Segoe UI", Font.BOLD, 28));
        drawCenteredString(g, "8", cx, cy - 8);
        g.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        g.setColor(new Color(100, 116, 139));
        drawCenteredString(g, "Total Projects", cx, cy + 18);

        // Legend
        int lx = 590, ly = 260;
        for (int i = 0; i < labels.length; i++) {
            g.setColor(colors[i]);
            g.fillRoundRect(lx, ly + (i * 55), 22, 22, 6, 6);
            g.setColor(new Color(15, 23, 42));
            g.setFont(new Font("Segoe UI", Font.BOLD, 15));
            g.drawString(labels[i], lx + 34, ly + (i * 55) + 16);
        }

        g.dispose();
        saveImage(img, path);
    }

    // Chart 2: Horizontal Bar Chart - Popular Technology Choices
    private static void generateChart2(String path) {
        int w = 1000, h = 650;
        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics();
        setupGraphics(g);

        g.setColor(new Color(248, 250, 252));
        g.fillRect(0, 0, w, h);

        drawHeader(g, "Most Popular Technology & Framework Adoption", 
                "Frequency of Programming Languages and Frameworks across Registered Projects", w);

        String[] techs = {"Python", "MySQL", "Java", "Docker", "React.js", "Node.js", "Flutter", "Spring Boot", "TensorFlow", "FastAPI"};
        int[] counts = {5, 4, 3, 3, 2, 2, 2, 1, 1, 1};
        int maxVal = 6;

        int startX = 200, startY = 140, barHeight = 32, gap = 16;
        int maxBarWidth = 650;

        for (int i = 0; i < techs.length; i++) {
            int y = startY + (i * (barHeight + gap));
            // Label
            g.setFont(new Font("Segoe UI", Font.BOLD, 14));
            g.setColor(new Color(51, 65, 85));
            g.drawString(techs[i], startX - g.getFontMetrics().stringWidth(techs[i]) - 15, y + 21);

            // Bar background track
            g.setColor(new Color(226, 232, 240));
            g.fillRoundRect(startX, y, maxBarWidth, barHeight, 8, 8);

            // Bar fill
            int fillW = (int) ((counts[i] / (double) maxVal) * maxBarWidth);
            GradientPaint gp = new GradientPaint(startX, y, new Color(59, 130, 246), startX + fillW, y, new Color(99, 102, 241));
            g.setPaint(gp);
            g.fillRoundRect(startX, y, fillW, barHeight, 8, 8);

            // Value text
            g.setColor(Color.WHITE);
            g.setFont(new Font("Segoe UI", Font.BOLD, 13));
            g.drawString(counts[i] + " (" + (int)(counts[i] * 12.5) + "%)", startX + fillW - 65, y + 21);
        }

        g.dispose();
        saveImage(img, path);
    }

    // Chart 3: Grouped Bar Chart - Skill Distribution by Category & Proficiency
    private static void generateChart3(String path) {
        int w = 1000, h = 650;
        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics();
        setupGraphics(g);

        g.setColor(new Color(248, 250, 252));
        g.fillRect(0, 0, w, h);

        drawHeader(g, "Student Technical Skill Distribution by Category & Proficiency", 
                "Proficiency Matrix Breakdown: Advanced vs Intermediate vs Beginner", w);

        String[] categories = {"Programming", "Web Dev", "Database", "AI/ML", "Cloud/DevOps", "Mobile App"};
        int[] advanced =     {6, 3, 3, 2, 0, 1};
        int[] intermediate = {2, 2, 3, 3, 2, 1};
        int[] beginner =     {1, 1, 0, 0, 1, 0};

        int groupX = 140, baseY = 520, groupW = 120, barW = 26;
        int maxVal = 8;
        int chartH = 340;

        // Grid lines
        g.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        for (int v = 0; v <= maxVal; v += 2) {
            int y = baseY - (int)((v / (double)maxVal) * chartH);
            g.setColor(new Color(226, 232, 240));
            g.drawLine(100, y, 920, y);
            g.setColor(new Color(148, 163, 184));
            g.drawString(String.valueOf(v), 75, y + 4);
        }

        // Draw groups
        for (int i = 0; i < categories.length; i++) {
            int gx = groupX + (i * 130);

            // Category label
            g.setFont(new Font("Segoe UI", Font.BOLD, 13));
            g.setColor(new Color(30, 41, 59));
            drawCenteredString(g, categories[i], gx + 40, baseY + 25);

            // Advanced Bar (Emerald)
            int hAdv = (int)((advanced[i] / (double)maxVal) * chartH);
            g.setColor(new Color(16, 185, 129));
            g.fillRoundRect(gx, baseY - hAdv, barW, hAdv, 6, 6);

            // Intermediate Bar (Blue)
            int hInt = (int)((intermediate[i] / (double)maxVal) * chartH);
            g.setColor(new Color(59, 130, 246));
            g.fillRoundRect(gx + barW + 4, baseY - hInt, barW, hInt, 6, 6);

            // Beginner Bar (Amber)
            int hBeg = (int)((beginner[i] / (double)maxVal) * chartH);
            g.setColor(new Color(245, 158, 11));
            g.fillRoundRect(gx + (barW * 2) + 8, baseY - hBeg, barW, hBeg, 6, 6);
        }

        // Legend at top right
        int lx = 620, ly = 90;
        g.setColor(new Color(16, 185, 129)); g.fillRoundRect(lx, ly, 16, 16, 4, 4);
        g.setColor(new Color(15, 23, 42)); g.drawString("Advanced", lx + 24, ly + 13);

        g.setColor(new Color(59, 130, 246)); g.fillRoundRect(lx + 105, ly, 16, 16, 4, 4);
        g.setColor(new Color(15, 23, 42)); g.drawString("Intermediate", lx + 129, ly + 13);

        g.setColor(new Color(245, 158, 11)); g.fillRoundRect(lx + 235, ly, 16, 16, 4, 4);
        g.setColor(new Color(15, 23, 42)); g.drawString("Beginner", lx + 259, ly + 13);

        g.dispose();
        saveImage(img, path);
    }

    // Chart 4: Milestone Progression Status per Project
    private static void generateChart4(String path) {
        int w = 1000, h = 650;
        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics();
        setupGraphics(g);

        g.setColor(new Color(248, 250, 252));
        g.fillRect(0, 0, w, h);

        drawHeader(g, "Project Milestone Progression & Status Breakdown", 
                "Tracking Completed, Pending, and Delayed Milestones per Enrolled Project", w);

        String[] projects = {"P1 (Sarmida)", "P2 (Kavin)", "P3 (Pradeep)", "P4 (Shreya)", "P5 (Dinesh)", "P6 (Ananya)", "P7 (Gokul)", "P8 (Pavithra)"};
        int[] completed = {5, 2, 4, 2, 4, 0, 1, 0};
        int[] pending =   {0, 1, 0, 1, 0, 2, 1, 2};
        int[] delayed =   {0, 0, 0, 1, 0, 0, 0, 0};

        int groupX = 130, baseY = 520, groupW = 100, barW = 20;
        int maxVal = 6, chartH = 340;

        for (int v = 0; v <= maxVal; v += 1) {
            int y = baseY - (int)((v / (double)maxVal) * chartH);
            g.setColor(new Color(226, 232, 240));
            g.drawLine(90, y, 940, y);
            g.setColor(new Color(148, 163, 184));
            g.drawString(String.valueOf(v), 70, y + 4);
        }

        for (int i = 0; i < projects.length; i++) {
            int gx = groupX + (i * 102);

            g.setFont(new Font("Segoe UI", Font.BOLD, 11));
            g.setColor(new Color(30, 41, 59));
            drawCenteredString(g, projects[i], gx + 30, baseY + 25);

            int hComp = (int)((completed[i] / (double)maxVal) * chartH);
            g.setColor(new Color(16, 185, 129));
            g.fillRoundRect(gx, baseY - hComp, barW, hComp, 5, 5);

            int hPend = (int)((pending[i] / (double)maxVal) * chartH);
            g.setColor(new Color(148, 163, 184));
            g.fillRoundRect(gx + barW + 3, baseY - hPend, barW, hPend, 5, 5);

            int hDel = (int)((delayed[i] / (double)maxVal) * chartH);
            g.setColor(new Color(239, 68, 68));
            g.fillRoundRect(gx + (barW * 2) + 6, baseY - hDel, barW, hDel, 5, 5);
        }

        int lx = 600, ly = 90;
        g.setColor(new Color(16, 185, 129)); g.fillRoundRect(lx, ly, 16, 16, 4, 4);
        g.setColor(new Color(15, 23, 42)); g.drawString("Completed", lx + 24, ly + 13);

        g.setColor(new Color(148, 163, 184)); g.fillRoundRect(lx + 115, ly, 16, 16, 4, 4);
        g.setColor(new Color(15, 23, 42)); g.drawString("Pending", lx + 139, ly + 13);

        g.setColor(new Color(239, 68, 68)); g.fillRoundRect(lx + 215, ly, 16, 16, 4, 4);
        g.setColor(new Color(15, 23, 42)); g.drawString("Delayed", lx + 239, ly + 13);

        g.dispose();
        saveImage(img, path);
    }

    // Chart 5: Evaluation Rubric Marks Analysis (Stacked/Grouped)
    private static void generateChart5(String path) {
        int w = 1000, h = 650;
        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics();
        setupGraphics(g);

        g.setColor(new Color(248, 250, 252));
        g.fillRect(0, 0, w, h);

        drawHeader(g, "Component-Wise Evaluation Marks & Total Scores", 
                "Academic Rubric Breakdown: Req (20), Design (20), Impl (30), Doc (15), Pres (15)", w);

        String[] students = {"Sarmida (25IT002)", "Dinesh (25ITL02)", "Pradeep (25IT062)", "Kavin (25IT032)", "Shreya (25IT092)"};
        double[] totals = {96.5, 95.0, 93.5, 89.5, 84.0};
        String[] grades = {"O (96.5%)", "O (95.0%)", "O (93.5%)", "A+ (89.5%)", "A+ (84.0%)"};

        int startX = 220, startY = 150, barH = 48, gap = 35;
        int maxW = 600;

        for (int i = 0; i < students.length; i++) {
            int y = startY + (i * (barH + gap));

            g.setFont(new Font("Segoe UI", Font.BOLD, 14));
            g.setColor(new Color(30, 41, 59));
            g.drawString(students[i], startX - g.getFontMetrics().stringWidth(students[i]) - 15, y + 28);

            // Track
            g.setColor(new Color(226, 232, 240));
            g.fillRoundRect(startX, y, maxW, barH, 10, 10);

            // Bar
            int fillW = (int)((totals[i] / 100.0) * maxW);
            GradientPaint gp = new GradientPaint(startX, y, new Color(16, 185, 129), startX + fillW, y, new Color(6, 182, 212));
            g.setPaint(gp);
            g.fillRoundRect(startX, y, fillW, barH, 10, 10);

            // Score & Grade text
            g.setColor(Color.WHITE);
            g.setFont(new Font("Segoe UI", Font.BOLD, 15));
            g.drawString(grades[i], startX + fillW - 100, y + 30);
        }

        g.dispose();
        saveImage(img, path);
    }

    private static void setupGraphics(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    }

    private static void drawHeader(Graphics2D g, String title, String subtitle, int width) {
        g.setColor(new Color(15, 23, 42));
        g.setFont(new Font("Segoe UI", Font.BOLD, 22));
        g.drawString(title, 40, 50);

        g.setColor(new Color(100, 116, 139));
        g.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        g.drawString(subtitle, 40, 75);

        g.setColor(new Color(226, 232, 240));
        g.drawLine(40, 90, width - 40, 90);
    }

    private static void drawCenteredString(Graphics2D g, String text, int cx, int cy) {
        FontMetrics fm = g.getFontMetrics();
        int x = cx - (fm.stringWidth(text) / 2);
        int y = cy + (fm.getAscent() / 2);
        g.drawString(text, x, y);
    }

    private static void saveImage(BufferedImage img, String path) {
        try {
            ImageIO.write(img, "PNG", new File(path));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
