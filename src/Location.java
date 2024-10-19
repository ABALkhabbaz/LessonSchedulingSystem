class Location {
    private String city;
    private String province;
    private String address;

    public Location(String city, String province, String address) {
        this.city = city;
        this.province = province;
        this.address = address;
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
}