package org.example;

import java.util.Arrays;

public class SJFScheduler extends Scheduler {
    @Override
    public void schedule(Process[] processes) {
        int currentTime = 0;
        int completedProcesses = 0;
        int n = processes.length;
        boolean[] isCompleted = new boolean[n];

        Arrays.sort(processes, (p1, p2) -> p1.arrivalTime - p2.arrivalTime);

        executionOrder = new StringBuilder();

        while (completedProcesses < n) {
            Process currentProcess = null;
            int minBurstTime = Integer.MAX_VALUE;

            for (int i = 0; i < n; i++) {
                if (!isCompleted[i] && processes[i].arrivalTime <= currentTime) {
                    if (processes[i].burstTime < minBurstTime ||
                            (processes[i].burstTime == minBurstTime && processes[i].arrivalTime < (currentProcess != null ? currentProcess.arrivalTime : Integer.MAX_VALUE))) {
                        minBurstTime = processes[i].burstTime;
                        currentProcess = processes[i];
                    }
                }
            }

            if (currentProcess != null) {
                executionOrder.append(currentProcess.id).append(",");
                currentTime += currentProcess.burstTime;

                currentProcess.completionTime = currentTime;
                currentProcess.turnaroundTime = currentProcess.completionTime - currentProcess.arrivalTime;
                currentProcess.waitingTime = currentProcess.turnaroundTime - currentProcess.burstTime;

                isCompleted[currentProcess.id - 1] = true;
                completedProcesses++;
            } else {
                currentTime++;
            }
        }

        if (executionOrder.length() > 0) {
            executionOrder.setLength(executionOrder.length() - 1);
        }
    }
}


