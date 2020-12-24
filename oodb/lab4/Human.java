public class Human {
    private String FullName;
    private String Age;
    private String MedicalPolicy;
    private String PhoneNumber;
    private String Address;

    public Human(String FullName, String PhoneNumber, String MedicalPolicy,  String Age, String Address) {
        this.FullName = FullName;
        this.Age = Age;
        this.MedicalPolicy = MedicalPolicy;
        this.PhoneNumber = PhoneNumber;
        this.Address = Address;
    }


    @Override
    public String toString() {
        return "Human{" +
                "FullName='" + FullName + '\'' +
                ", Age='" + Age + '\'' +
                ", MedicalPolicy='" + MedicalPolicy + '\'' +
                ", PhoneNumber='" + PhoneNumber + '\'' +
                ", Address='" + Address + '\'' +
                '}';
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        this.Age = age;
    }

    public void setMedicalPolicy(String MedicalPolicy) {
        this.MedicalPolicy = MedicalPolicy;
    }

    public String getMedicalPolicy() {
        return MedicalPolicy;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String PhoneNumber) {
        this.PhoneNumber = PhoneNumber;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getAddress() {
        return Address;
    }
}