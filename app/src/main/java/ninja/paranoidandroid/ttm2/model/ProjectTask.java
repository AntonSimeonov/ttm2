package ninja.paranoidandroid.ttm2.model;

/**
 * Created by anton on 08.12.16.
 */

public class ProjectTask {

    private String name;
    private String startDate;
    private String endDate;

    public ProjectTask() {
    }

    public ProjectTask(String name, String startDate, String endDate) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
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
