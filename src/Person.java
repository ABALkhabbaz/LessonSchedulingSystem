public class Person {
    private String name;
    private int age;

    public Person(){
        name = "Default Name";
        age = 0;
    }
    public Person(String name, int age){
        this.name = name;
        this.age = age;
    }

    public void display(){
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
    }

    public void setName(String name){
        this.name = name;
    }

    public void setAge(int age){
        this.age = age;
    }

    public String getName(){
        return name;
    }

    public int getAge(){
        return age;
    }
}