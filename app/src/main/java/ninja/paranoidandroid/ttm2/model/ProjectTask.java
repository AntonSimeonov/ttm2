package ninja.paranoidandroid.ttm2.model;

/**
 * Created by anton on 08.12.16.
 */

public class ProjectTask {

    private String name;
    private String startDate;
    private String endDate;
    private String description;

    public ProjectTask() {
    }

    public ProjectTask(String name, String startDate, String endDate, String description) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
