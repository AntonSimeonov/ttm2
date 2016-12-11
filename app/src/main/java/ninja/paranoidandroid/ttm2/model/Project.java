package ninja.paranoidandroid.ttm2.model;

import java.io.Serializable;

/**
 * Created by anton on 08.12.16.
 */

public class Project implements Serializable{

    private String name;
    private String description;
    private String startDate;
    private String endDate;
    private double budget;
    private String goal;
    private String result;
    private String users = "init";

    public Project() {
    }

    public Project(String name, String description, String endDate, String startDate, double budget, String result, String goal) {
        this.name = name;
        this.description = description;
        this.endDate = endDate;
        this.startDate = startDate;
        this.budget = budget;
        this.result = result;
        this.goal = goal;
    }

    public String getUsers() {
        return users;
    }

    public void setUsers(String users) {
        this.users = users;
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

    public String getGoal() {
        return goal;
    }

    public String getResult() {
        return result;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStartDate(String start_date) {
        this.startDate = start_date;
    }

    public void setEndDate(String end_date) {
        this.endDate = end_date;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
