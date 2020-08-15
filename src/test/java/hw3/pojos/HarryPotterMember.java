package hw3.pojos;

import com.google.gson.annotations.SerializedName;

public class HarryPotterMember {

    /**
     *  {
     *             "_id": "5a0fa648ae5bc100213c2332",
     *             "name": "Katie Bell"
     *         }
     */
    @SerializedName("_id")
    private String id;
    private String name;

    public HarryPotterMember(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "HarryPotterMember{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
