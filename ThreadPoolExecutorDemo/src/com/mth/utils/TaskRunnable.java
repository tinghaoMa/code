package com.mth.utils;

import java.io.Serializable;

public class TaskRunnable implements Runnable, Serializable, Comparable<TaskRunnable> {
    public String name;
    public int priority;
    public Runnable run;

    public TaskRunnable(Runnable run, String name, int priority) {
        this.name = name;
        this.priority = priority;
        this.run = run;
    }

    public int compareTo(TaskRunnable other) {
        if (other == this || other.priority == this.priority) { // FIFO
            return this.name.compareTo(other.name);
        } else if (other.priority > this.priority) {
            return 1;
        } else {
            return -1;
        }
    }

    public void run() {
        run.run();
    }
}