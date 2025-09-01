package Model;

public class ModelCadet {
    private int id;
    private String name;
    private int age;
    private String rank;
    private String unit;
    private int eventsAttended;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getEventsAttended() {
        return eventsAttended;
    }

    public void setEventsAttended(int eventsAttended) {
        this.eventsAttended = eventsAttended;
    }

    public ModelCadet(int id, String name, int age, String rank, String unit, int eventsAttended) {
        super();
        this.id = id;
        this.name = name;
        this.age = age;
        this.rank = rank;
        this.unit = unit;
        this.eventsAttended = eventsAttended;
    }

    @Override
    public String toString() {
        return "ModelCadet [id=" + id + ", name=" + name + ", age=" + age + ", rank=" + rank + ", unit=" + unit
                + ", eventsAttended=" + eventsAttended + "]";
    }
}
