package Model;

import java.time.LocalDate;

public class ModelAchievement {
    private int id;
    private int cadetId;
    private String title;
    private String description;
    private LocalDate date;

    public ModelAchievement() {}

    public ModelAchievement(int id, int cadetId, String title, String description, LocalDate date) {
        this.id = id;
        this.cadetId = cadetId;
        this.title = title;
        this.description = description;
        this.date = date;
    }

    // Getters
    public int getId() {
        return id;
    }

    public int getCadetId() {
        return cadetId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDate() {
        return date;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setCadetId(int cadetId) {
        this.cadetId = cadetId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    // toString
    @Override
    public String toString() {
        return "Achievement{" +
                "id=" + id +
                ", cadetId=" + cadetId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                '}';
    }
}

