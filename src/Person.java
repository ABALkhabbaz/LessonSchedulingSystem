public class Person {

    private String name;
    private String phone;
    private int age;
    

    public Person(){
        name = "Default Name";
        age = 0;
    }

    public Person(String name, String phone, int age){
        this.name = name;
        this.phone = phone;
        this.age = age;
    }

    public void display(){
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
    }

    public void setName(String name){
        this.name = name;
    }

    public void setPhone(String phone){
        this.phone = phone;
    }

    public void setAge(int age){
        this.age = age;
    }

    public String getName(){
        return name;
    }

    public String getPhone(){
        return phone;
    }

    public int getAge(){
        return age;
    }
}