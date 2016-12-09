package ninja.paranoidandroid.ttm2.model;

/**
 * Created by anton on 08.12.16.
 */

public class Task {

    private String name;
    private String description;
    private String startDate;
    private String endDate;
    private double budget;
    private String note;
    private String report;
    private boolean status;
    private int priority; //low = 0, medium = 1, high = 2

    public Task() {
    }

    public Task(String name, String description, String startDate, String endDate, double budget, String note, String report, boolean status, int priority) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.budget = budget;
        this.note = note;
        this.report = report;
        this.status = status;
        this.priority = priority;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public double getBudget() {
        return budget;
    }

    public String getNote() {
        return note;
    }

    public String getReport() {
        return report;
    }

    public boolean isStatus() {
        return status;
    }

    public int getPriority() {
        return priority;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
