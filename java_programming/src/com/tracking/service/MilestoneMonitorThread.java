package com.tracking.service;

import com.tracking.model.Milestone;
import com.tracking.model.Project;
import java.util.List;

public class MilestoneMonitorThread extends Thread {
    private final List<Project> projects;
    private volatile boolean running = true;

    public MilestoneMonitorThread(List<Project> projects) {
        super("Milestone-Audit-Daemon");
        this.projects = projects;
        setDaemon(true); // Daemon thread so JVM exits cleanly
    }

    @Override
    public void run() {
        try {
            // Perform asynchronous milestone audit check
            Thread.sleep(100);
            int delayedCount = 0;
            for (Project p : projects) {
                for (Milestone m : p.getMilestones()) {
                    if ("Delayed".equalsIgnoreCase(m.getCompletionStatus())) {
                        delayedCount++;
                    }
                }
            }
            // Audit complete
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void stopMonitoring() {
        this.running = false;
    }
}
