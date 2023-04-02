package bn.nadamattar.libraryprojectnada.model;

public class UserData {
    private String country ;
    private int age ;

    public UserData() {
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "UserData{" +
                "country='" + country + '\'' +
                ", age=" + age +
                '}';
    }
}
