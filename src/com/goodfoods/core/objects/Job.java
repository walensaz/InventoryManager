package com.goodfoods.core.objects;

import com.sun.istack.internal.Nullable;

public class Job {

    private JobType jobType;
    private int hours;
    private int expectedHours;
    private boolean completed;

    @Nullable private String employee;

    public Job(JobType jobType, int hours, String employee) {
        this.jobType = jobType;
        this.expectedHours = hours;
        this.hours = hours;
        this.employee = employee;
        this.completed = false;
    }

    public Job(JobType jobType, int hours) {
        this.jobType = jobType;
        this.expectedHours = hours;
        this.hours = hours;
        this.employee = "N/A";
        this.completed = false;
    }

    public JobType getJobType() {
        return jobType;
    }

    public void setJobType(JobType jobType) {
        this.jobType = jobType;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public int getExpectedHours() {
        return expectedHours;
    }

    public void setExpectedHours(int expectedHours) {
        this.expectedHours = expectedHours;
    }
}
