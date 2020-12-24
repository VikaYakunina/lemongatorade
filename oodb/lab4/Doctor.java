public class Doctor extends Human {
    private long id;
    private String position;

    public Doctor( long id,  String position, String FullName, String PhoneNumber, String MedicalPolicy,  String Age, String Address) {
        super(FullName, PhoneNumber, MedicalPolicy, Age, Address);
        this.id = id;
        this.position = position;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPosition() {
        return position;
    }
    public void setPosition(String position) {
        this.position = position;
    }
}