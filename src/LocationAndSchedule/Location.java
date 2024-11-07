package LocationAndSchedule;

public class Location {
    private String name;
    private String city;
    private String province;
    private String address;

    public Location(String name, String city, String province, String address) {
        this.name = name;
        this.city = city;
        this.province = province;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getProvince() {
        return province;
    }

    public String getAddress() {
        return address;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setCity(String city){
        this.city = city;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return name + " in " + city + ", " + province + " at " + address;
    }
}