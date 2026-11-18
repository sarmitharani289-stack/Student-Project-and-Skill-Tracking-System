package com.tracking.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class TerminalScreenshotUtil {
    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Usage: java TerminalScreenshotUtil <inputTxtPath> <outputPngPath> <windowTitle>");
            return;
        }
        String inputPath = args[0];
        String outputPath = args[1];
        String title = args[2];

        try {
            List<String> allLines = Files.readAllLines(Paths.get(inputPath));
            int maxLines = Math.min(allLines.size(), 46);
            int width = 1250;
            int lineHeight = 21;
            int topPadding = 65;
            int bottomPadding = 25;
            int height = topPadding + (maxLines * lineHeight) + bottomPadding;

            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = image.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

            // Dark terminal background
            g2.setColor(new Color(15, 23, 42)); // #0F172A
            g2.fillRect(0, 0, width, height);

            // Window Header Bar
            g2.setColor(new Color(30, 41, 59)); // #1E293B
            g2.fillRect(0, 0, width, 45);

            // Window controls
            g2.setColor(new Color(239, 68, 68)); g2.fillOval(18, 16, 13, 13);
            g2.setColor(new Color(245, 158, 11)); g2.fillOval(38, 16, 13, 13);
            g2.setColor(new Color(34, 197, 94));  g2.fillOval(58, 16, 13, 13);

            // Title
            g2.setFont(new Font("Segoe UI", Font.BOLD, 13));
            g2.setColor(new Color(203, 213, 225));
            g2.drawString(title, 90, 27);

            // Terminal font
            g2.setFont(new Font("Consolas", Font.PLAIN, 13));

            int y = topPadding + 14;
            for (int i = 0; i < maxLines; i++) {
                String line = allLines.get(i);
                if (line.contains("VELALAR") || line.contains("SCENARIO 2") || line.contains("COMPLETED") || line.contains("SUCCESS")) {
                    g2.setColor(new Color(56, 189, 248)); // Cyan
                } else if (line.contains("===") || line.contains("---") || line.contains("+++")) {
                    g2.setColor(new Color(71, 85, 105)); // Divider
                } else if (line.startsWith(">>>") || line.startsWith(" STEP")) {
                    g2.setColor(new Color(250, 204, 21)); // Gold/Yellow
                } else if (line.contains("Completed") || line.contains("Advanced") || line.contains("O (Outstanding)")) {
                    g2.setColor(new Color(74, 222, 128)); // Green
                } else if (line.contains("Delayed") || line.contains("In-Progress")) {
                    g2.setColor(new Color(251, 146, 60)); // Orange
                } else {
                    g2.setColor(new Color(241, 245, 249)); // Text
                }
                g2.drawString(line, 20, y);
                y += lineHeight;
            }

            g2.dispose();
            ImageIO.write(image, "PNG", new File(outputPath));
            System.out.println("Generated screenshot: " + outputPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
