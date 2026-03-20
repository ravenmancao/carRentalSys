package CusRecMan;

public class CustomerPage {  
    private int id;
    private String name;
    private String phone;
    private String license;
    private String address;
    private String car;
    private String rentalDate;
    private String returnDate;

    public CustomerPage(int id, String name, String phone, String license, String address, String car, String rentalDate, String returnDate) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.license = license;
        this.address = address;
        this.car = car;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getLicense() { return license; }
    public String getAddress() { return address; }
    public String getCar() { return car;}
    public String getRentalDate() {return rentalDate;}
    public String getReturnDate() {return returnDate;}

    public void setName(String name) { this.name = name; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setLicense(String license) { this.license = license; }
    public void setAddress(String address) { this.address = address; }
    public void setCar(String car) {this.car = car;}
    public void setRentalDate(String rentalDate) {this.rentalDate = rentalDate;}
    public void setReturnDate(String returnDate) {this.returnDate = returnDate;}
}