public class Person{
    private String firstName;
    private String lastName;
    private String dob;
    private String address;
    private String phone;


    public Person(){
        firstName = "";
        lastName = "";
        dob = "";
        address = "";
        phone = "";        
    }

    public Person(String firstName, String lastName, String dob, String address, String phone){
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.address = address;
        this.phone = phone;
    }

    public void setFirstName(String name){
        this.firstName = name;
    }

    public void setLastName(String name){
        this.lastName = name;
    }

    public void setDOB(String dob){
        this.dob = dob;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public void setPhoneNumber(String phone){
        this.phone = phone;
    }

    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public String getDOB(){
        return dob;
    }

    public String getAddress(){
        return address;
    }

    public String getPhoneNumber(){
        return phone;
    }

    public String getName(){
        return getFirstName() + " " + getLastName();
    }
}