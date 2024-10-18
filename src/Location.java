public class Location {
    private String city;
    private String province;
    private String country;
    private String address;


    // Getters
    public String getCity(){
        return city;
    }

    public String getProvince(){
        return province;
    }

    public String getCountry(){
        return country;
    }

    public String getAddress(){
        return address;
    }

    // Setters

    public void setCity(String city){
        this.city = city;
    }

    public void setProvince(String province){
        this.province = province;
    }
    
    public void setCountry(String country){
        this.country = country;
    }

    public void setAddress(String address){
        this.address = address;
    }

}
