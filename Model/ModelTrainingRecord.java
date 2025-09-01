package Model;

import java.time.LocalDate;

public class ModelTrainingRecord {
    private int id;
    private int cadetId;
    private String campName;
    private String location;
    private LocalDate date;

    public ModelTrainingRecord(int id, int cadetId, String campName, String location, LocalDate date) {
        this.id = id;
        this.cadetId = cadetId;
        this.campName = campName;
        this.location = location;
        this.date = date;
    }

    public int getId() { return id; }
    public int getCadetId() { return cadetId; }
    public String getCampName() { return campName; }
    public String getLocation() { return location; }
    public LocalDate getDate() { return date; }

    public void setId(int id) { this.id = id; }
    public void setCadetId(int cadetId) { this.cadetId = cadetId; }
    public void setCampName(String campName) { this.campName = campName; }
    public void setLocation(String location) { this.location = location; }
    public void setDate(LocalDate date) { this.date = date; }

    @Override
    public String toString() {
        return "ModelTrainingRecord [id=" + id + ", cadetId=" + cadetId + ", campName=" + campName
                + ", location=" + location + ", date=" + date + "]";
    }
}
