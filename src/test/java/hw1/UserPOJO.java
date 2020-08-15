package hw1;

/**
 *  {
 *         "name": "森",
 *         "surname": "大輔",
 *         "gender": "male",
 *         "region": "Japan"
 *     }
 */
public class UserPOJO {

    private String name;
    private String surname;
    private String gender;
    private String region;

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getGender() {
        return gender;
    }

    public String getRegion() {
        return region;
    }

    @Override
    public String toString() {
        return "UserPOJO{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", gender='" + gender + '\'' +
                ", region='" + region + '\'' +
                '}';
    }


}
